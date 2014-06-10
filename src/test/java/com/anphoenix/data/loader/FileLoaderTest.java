package com.anphoenix.data.loader;

import com.anphoenix.data.model.ObjectType;
import junit.framework.TestCase;
import org.junit.Test;

import java.net.URL;

public class FileLoaderTest extends TestCase {

    @Test
    public void testLoadDefault() throws Exception {
        String personData = getClass().getResource("/data/userprofile_test.txt").getPath();
        //System.out.println(personData);
        FileLoader.load(personData, ObjectType.PERSON, 5);
    }
}