package com.anphoenix.data.persistent.hbase;

import junit.framework.TestCase;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HBaseUtilTest extends TestCase {

    @Test
    public void testMainFunction() throws Exception {

        // create table
        String tableName = "person";
        String[] family = { "basic", "atemp" };
        HBaseUtil.creatTable(tableName, family);

        // insert data to table
        String personID1 = "10457";
        Map<String, String> person1 = new HashMap<String, String>();
        person1.put("basic:name", "Hellena");
        person1.put("basic:city", "北京");
        person1.put("basic:gender", "f");
        person1.put("basic:isvip", "0");
        person1.put("atemp:interest", "偶像剧,小王子");

        String personID2 = "11387";
        Map<String, String> person2 = new HashMap<String, String>();
        person2.put("basic:name", "左琼");
        person2.put("basic:city", "广东");
        person2.put("basic:gender", "f");
        person2.put("atemp:interest", "网络");

        String personID3 = "40343";
        Map<String, String> person3 = new HashMap<String, String>();
        person3.put("basic:name", "周旭东");
        person3.put("basic:city", "北京");
        person3.put("basic:gender", "m");
        person3.put("atemp:interest", "文学");

        List<Map<String, String>> persons = new ArrayList<Map<String, String>>();
        person2.put(HBaseUtil.ROW_KEY, personID2);
        persons.add(person2);
        person3.put(HBaseUtil.ROW_KEY, personID3);
        persons.add(person3);

        HBaseUtil.addData(personID1, tableName, person1);

//        HBaseUtil.addData(personID2, tableName, person2);
//        HBaseUtil.addData(personID2, tableName, person3);
//        HBaseUtil.addData(persons);

        ResultHandler handler = new ResultHandler(){
            @Override
            public void process(Result rs) {
                if(rs.isEmpty()) {
                    System.out.println("No query result found");
                    return;
                }
                for (KeyValue kv : rs.list()) {
                    System.out.print("key:" + Bytes.toString(kv.getFamily()) + ":" + Bytes.toString(kv.getQualifier()));
                    System.out.print("  value:" + Bytes.toString(kv.getValue()));
                    System.out.println("  Timestamp:" + kv.getTimestamp());
                }
            }
        };
        System.out.println("Get the info of person 1");
        HBaseUtil.getResult(tableName, personID1, handler);
        System.out.println("-------------------------------------------");

        System.out.println("Scan the person record");
        HBaseUtil.getResultScan(tableName, handler);
        System.out.println("-------------------------------------------");

        System.out.println("Scan the record by specify rowkey");
        HBaseUtil.getResultScan(tableName, personID1, personID2, handler);
        System.out.println("-------------------------------------------");

        System.out.println("Get a specific column data");
        HBaseUtil.getResultByColumn(tableName, personID1, "basic", "name", handler);
        System.out.println("-------------------------------------------");

        System.out.println("update a specific column data");
        HBaseUtil.getResultByColumn(tableName, personID1, "atemp", "interest", handler);
        HBaseUtil.updateTable(tableName, personID1, "atemp", "interest", "电玩");
        HBaseUtil.getResultByColumn(tableName, personID1, "atemp", "interest", handler);
        System.out.println("-------------------------------------------");

        System.out.println("get multiple version of a column");
        HBaseUtil.getResultByVersion(tableName, personID1, "atemp", "interest", handler);
        System.out.println("-------------------------------------------");

        System.out.println("delete one column");
        HBaseUtil.deleteColumn(tableName, personID1, "atemp", "interest");
        HBaseUtil.getResultByColumn(tableName, personID1, "atemp", "interest", handler);
        System.out.println("-------------------------------------------");

        HBaseUtil.deleteAllColumn(tableName, personID1);

        HBaseUtil.deleteTable(tableName);

    }
}