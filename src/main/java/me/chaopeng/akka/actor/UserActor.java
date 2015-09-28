package me.chaopeng.akka.actor;

import akka.actor.PoisonPill;
import akka.cluster.sharding.ShardRegion;
import akka.persistence.UntypedPersistentActor;
import me.chaopeng.akka.message.Add;
import me.chaopeng.akka.message.Print;
import me.chaopeng.akka.message.Stop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * me.chaopeng.akka.actor.UserActor
 *
 * @author chao
 * @version 1.0 - 2015-09-23
 */
public class UserActor extends UntypedPersistentActor {

    private final static Random r = ThreadLocalRandom.current();
    private final static Logger logger = LoggerFactory.getLogger(UserActor.class);

    public UserActor() {
        this.id = this.self().path().name();

        logger.info("UserActor-{} created. ", id);
    }

    private final String id;
    private int count = 0; // it just do +

    private void updateState(Object msg) {
        if (msg instanceof Add) {
            count += ((Add) msg).num;
        }
    }

    @Override
    public void onReceiveRecover(Object msg) throws Exception {
        logger.info("UserActor-{} receive recover message", id);

        if(r.nextInt(10) > 7) {
            logger.info("UserActor-{} will recover slow", id);
            Thread.sleep(10000);
        }
        updateState(msg);
        logger.info("UserActor-{} recover done", id);
    }

    @Override
    public void onReceiveCommand(Object msg) throws Exception {

        if (msg instanceof Add) {

            logger.info("UserActor-{} receive: add {}", id, ((Add) msg).num);

            persist(msg, add -> {
                updateState(msg);
            });
        }

        else if (msg instanceof Print){
            logger.info("UserActor-{}: count={}", id, count);
        }

        else if (msg instanceof Stop){
            logger.info("UserActor-{} will stop", id);
            getContext().parent().tell(
                    new ShardRegion.Passivate(PoisonPill.getInstance()), getSelf());
        }

    }

    @Override
    public String persistenceId() {
        return "UserActor-" + id;
    }

    @Override
    public void postStop() {
        logger.info("UserActor-{} stopped", id);
    }
}
