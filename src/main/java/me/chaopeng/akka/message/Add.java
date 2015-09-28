package me.chaopeng.akka.message;

import java.io.Serializable;

/**
 * me.chaopeng.akka.message.Add
 *
 * @author chao
 * @version 1.0 - 2015-09-23
 */
public class Add implements Serializable{

    public Add(int num) {
        this.num = num;
    }

    public final int num;

}
