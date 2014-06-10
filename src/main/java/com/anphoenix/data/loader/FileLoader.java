package com.anphoenix.data.loader;

import com.anphoenix.data.model.ObjectType;
import com.anphoenix.data.persistent.hbase.HBaseUtil;

import java.io.*;
import java.util.*;

/**
 * Created by stefanie on 6/5/14.
 */
public class FileLoader {

    public static void load(String filePath, ObjectType type, int batchNum){
        String mapPath = null;
        switch(type){
            case PERSON:
                mapPath = FileLoader.class.getResource("/model/Person").getPath();
                break;
            case RELATION:
                mapPath = FileLoader.class.getResource("/model/Relation").getPath();
                break;
            case WEIBO:
                mapPath = FileLoader.class.getResource("/model/Weibo").getPath();
                break;
        }
        if(mapPath != null){
            load(filePath, mapPath, "\t", batchNum);
        }
    }

    public static void load(String filePath, String mapPath, String splitor, int batchNum){
        List<String> propertiesMap = loadMap(mapPath);
        String tableName = new File(mapPath).getName();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            List<Map<String, String>> objects = new ArrayList<Map<String, String>>();
            String line;
            int recordNum = 0;
            while ((line = br.readLine()) != null) {
                String[] args = line.split(splitor);
                Map<String, String> obj = new HashMap<String, String>();
                //TODO: ADD FAULT DETECTION FOR INCOMPLETE DATA
                for(int i = 0; i < args.length; i++){
                    if(!"IGNORE".equalsIgnoreCase(propertiesMap.get(i))){
                        //System.out.println(propertiesMap.get(i));
                        //System.out.println(args[i]);
                        obj.put(propertiesMap.get(i), args[i]);
                    }
                }
                //ignore all the lines without rowkey
                // also fix empty line split problem (split will return "" when given a empty line)
                if(obj.get("rowkey") != null && !"".equals(obj.get("rowkey")))
                    objects.add(obj);
                if(objects.size() > batchNum){
                    recordNum += insertData(tableName, objects);
                }
            }
            if(objects.size() > 0) {
//                for(Map<String, String> obj : objects){
//                    for(Map.Entry<String, String> item : obj.entrySet()){
//                        System.out.println("Map: " + item.getKey() + " " + item.getValue());
//                    }
//                }
                recordNum += insertData(tableName, objects);
            }

            br.close();
            System.out.println("Total insert record: " + recordNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int insertData(String tableName, List<Map<String, String>> objects) throws Exception{
        if(! HBaseUtil.isTableExist(tableName)){
            String[] cfs = {"a", "b"};
            HBaseUtil.creatTable(tableName, cfs);
        }
        int insertNum = HBaseUtil.addData(tableName, objects);
        objects.clear();
        return insertNum;
    }

    private static List<String> loadMap(String filePath){
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.split("\\s");
                //handle composite rowkey
                String key = args[0];
                if(key.startsWith("rowkey=")){
                    //parse rowkey generator
                    //handle rowkey generator
                } else {
                    list.add(args[0]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
