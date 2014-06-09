package com.anphoenix.data.loader;

import com.anphoenix.data.model.ObjectType;
import com.anphoenix.data.model.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by stefanie on 6/5/14.
 */
public class FileLoader {

    public void load(String filePath, ObjectType type){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] args = line.split("\t");
                switch (type){
                    case PERSON:
                        Person p = new Person(args);
                        //p.save();
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
