package com.anphoenix.data.persistent.hbase;

import org.apache.hadoop.hbase.client.Result;

/**
 * Created by stefanie on 6/6/14.
 */
public interface ResultHandler {
    public void process(Result rs);
}
