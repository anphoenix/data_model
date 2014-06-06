package com.anphoenix.data.persistent;

import com.anphoenix.data.model.Person;

import com.anphoenix.data.persistent.hbase.HBasePersistor;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by stefanie on 6/5/14.
 */
public abstract class Persistor {

    public static Persistor getInstance(String provider){
        if("HBASE".equalsIgnoreCase(provider)){
            return new HBasePersistor();
        } else {
            //TODO: LOG ERROR
            return null;
        }
    }

    public void save(Object obj){

    }

    public abstract void savePerson(Person person);
}
