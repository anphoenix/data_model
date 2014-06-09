package com.anphoenix.data.persistent.hbase;

import com.anphoenix.data.model.Person;
import com.anphoenix.data.persistent.Persistor;

import java.lang.reflect.Field;

/**
 * Created by stefanie on 6/5/14.
 */
public class HBasePersistor extends Persistor {

    @Override
    public void save(Object obj) {

        Field[] fields = obj.getClass().getFields();
    }
}
