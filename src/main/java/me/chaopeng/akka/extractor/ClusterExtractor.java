package me.chaopeng.akka.extractor;

import akka.cluster.sharding.ShardRegion;
import me.chaopeng.akka.message.ClusterMessage;

/**
 * me.chaopeng.akka.extractor.ClusterExtractor
 *
 * @author chao
 * @version 1.0 - 2015-09-23
 */
public class ClusterExtractor implements ShardRegion.MessageExtractor {

    @Override
    public String entityId(Object message) {
        if (message instanceof ClusterMessage) {
            return String.valueOf(((ClusterMessage) message).id);
        }
        return null;
    }

    @Override
    public Object entityMessage(Object message) {
        if (message instanceof ClusterMessage) {
            return ((ClusterMessage) message).payload;
        }
        return null;
    }

    @Override
    public String shardId(Object message) {
        if (message instanceof ClusterMessage) {
            int id = ((ClusterMessage) message).id;
            return String.valueOf(id % 10);
        }
        return null;
    }
}
