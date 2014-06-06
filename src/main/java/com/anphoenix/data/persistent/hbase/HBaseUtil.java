package com.anphoenix.data.persistent.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;

/**
 * Created by stefanie on 6/6/14.
 */
public class HBaseUtil {

    static Configuration conf = null;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
    }

    public static void creatTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    public static void addData(String rowKey, String tableName, Map<String, String> object)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        for(Map.Entry<String, String> attr : object.entrySet()){
            String[] keys = attr.getKey().split(":");
            if(keys.length < 2) {
                //TODO: LOG THE ERROR
                continue;
            }
            put.add(Bytes.toBytes(keys[0]),
                    Bytes.toBytes(keys[1]), Bytes.toBytes(attr.getValue()));
        }
        table.put(put);
        //TODO: LOG THE SUCCESS
        System.out.println("add data Success!");
    }

    public static Result getResult(String tableName, String rowKey, ResultHandler handler)
            throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
        Result result = table.get(get);
        handleResult(result, handler);
        return result;
    }

    public static ResultScanner getResultScan(String tableName, ResultHandler handler) throws IOException {
        return getResultScan(tableName, null, null, handler);
    }

    public static ResultScanner getResultScan(String tableName, String start_rowkey,
                                      String stop_rowkey, ResultHandler handler) throws IOException {
        Scan scan = new Scan();
        if(start_rowkey != null)
            scan.setStartRow(Bytes.toBytes(start_rowkey));
        if(stop_rowkey != null)
            scan.setStopRow(Bytes.toBytes(stop_rowkey));
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            if(handler != null){
                for (Result r : rs) {
                    handler.process(r);
                }
            }
            return rs;
        } finally {
            rs.close();
        }
    }

    public static Result getResultByColumn(String tableName, String rowKey,
                                         String familyName, String columnName, ResultHandler handler) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        Result result = table.get(get);
        handleResult(result, handler);
        return result;
    }

    public static void updateTable(String tableName, String rowKey,
                                   String familyName, String columnName, String value)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
                Bytes.toBytes(value));
        table.put(put);
        //TODO: CHANGE TO LOG
        System.out.println("update table Success!");
    }

    //TODO: HAVE SOME PROBLEM TO GET MULTIPLE VERSION
    public static Result getResultByVersion(String tableName, String rowKey,
                                          String familyName, String columnName, ResultHandler handler) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(5);
        Result result = table.get(get);
        handleResult(result, handler);
        return result;
    }

    public static void deleteColumn(String tableName, String rowKey,
                                    String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(familyName),
                Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        //TODO: CHANGE TO LOG
        System.out.println(familyName + ":" + columnName + " is deleted!");
    }

    public static void deleteAllColumn(String tableName, String rowKey)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        //TODO: CHANGE TO LOG
        System.out.println("all columns in table " + tableName + " are deleted!");
    }

    public static void deleteTable(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        //TODO: CHANGE TO LOG
        System.out.println(tableName + " is deleted!");
    }

    public static void handleResult(Result result, ResultHandler handler){
        if(handler != null)
            handler.process(result);
    }
}
