package me.chaopeng.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.japi.Creator;
import akka.persistence.Persistence;
import com.typesafe.config.ConfigFactory;
import me.chaopeng.akka.actor.UserActor;
import me.chaopeng.akka.extractor.ClusterExtractor;
import me.chaopeng.akka.message.Add;
import me.chaopeng.akka.message.ClusterMessage;
import me.chaopeng.akka.message.Print;
import me.chaopeng.akka.message.Stop;
import scala.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * me.chaopeng.akka.Main
 *
 * @author chao
 * @version 1.0 - 2015-09-23
 */
public class Main {

    public static final String ActorSystemName = "ActorSystem";

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create(ActorSystemName, ConfigFactory.load(args[0]));
        Persistence.apply(system);
        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        ClusterExtractor extractor = new ClusterExtractor();

        if (args[0].equals("proxy")) {
            // new sharding proxy
            ClusterSharding.get(system).startProxy("U"
                    , Optional.empty()
                    , extractor);
        } else {
            // new sharding region
            ClusterSharding.get(system).start("U"
                    , Props.create(UserActor.class)
                    , settings
                    , extractor);
        }

        ActorRef actor = ClusterSharding.get(system).shardRegion("U");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                String s = in.readLine().trim();

                String[] ss = s.split(" ");

                if ("add".equals(ss[0])) {
                    int id = Integer.parseInt(ss[1]);
                    int num = Integer.parseInt(ss[2]);

                    actor.tell(new ClusterMessage(id, new Add(num)), ActorRef.noSender());
                }

                if ("print".equals(ss[0])) {
                    int id = Integer.parseInt(ss[1]);

                    actor.tell(new ClusterMessage(id, new Print()), ActorRef.noSender());
                }

                if ("printn".equals(ss[0])) {
                    int n = Integer.parseInt(ss[1]);

                    for (int i = 0; i < n; ++i) {
                        actor.tell(new ClusterMessage(i, new Print()), ActorRef.noSender());
                    }
                }

                if ("stop".equals(ss[0])) {
                    int id = Integer.parseInt(ss[1]);

                    actor.tell(new ClusterMessage(id, new Stop()), ActorRef.noSender());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
