package me.chaopeng.akka.message;

import java.io.Serializable;

/**
 * me.chaopeng.akka.message.ClusterMessage
 *
 * @author chao
 * @version 1.0 - 2015-09-23
 */
public class ClusterMessage implements Serializable {
    final public int id;
    final public Object payload;

    public ClusterMessage(int id, Object payload) {
        this.id = id;
        this.payload = payload;
    }
}
