package com.anphoenix.data.loader;

import com.anphoenix.data.model.ObjectType;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
@RunWith(JUnit4.class)
public class FileLoaderTest extends TestCase {

    @Test
    public void testLoadPerson() throws Exception {
        String personData = getClass().getResource("/data/userprofile_test.txt").getPath();
        //System.out.println(personData);
        FileLoader.load(personData, ObjectType.PERSON, 5);
    }

    @Test
    public void testLoadRelation() throws Exception {
        String personData = getClass().getResource("/data/relation_test.txt").getPath();
        //System.out.println(personData);
        FileLoader.load(personData, ObjectType.RELATION, 5);
    }

    @Test
    public void testLoadWeibo() throws Exception {
        String personData = getClass().getResource("/data/weibo_test.txt").getPath();
        //System.out.println(personData);
        FileLoader.load(personData, ObjectType.WEIBO, 5);
    }

    @Test
    public void testLoadMap(){
        String mapFile = getClass().getResource("/model/TestMap").getPath();
        List<String> properties = FileLoader.loadMap(mapFile);
        assertEquals(properties.size(), 3);
        assertTrue(properties.get(properties.size() - 1).startsWith("rowkey"));
    }

    @Test
    public void testParseGenerationRule(){
        String rule = "rowkey=a:t-a:s";
        List<String> keys = new ArrayList<String>();
        keys.add("a:t");
        keys.add("a:s");

        String[] items = FileLoader.parseGenerationRule(rule, keys);
        assertEquals(2, items.length);
        assertEquals("a:t", items[0]);
        assertEquals("a:s", items[1]);
    }

    @Test(expected=RuntimeException.class)
    public void testParseGenerationRuleWithUnExistedKey(){
        String rule = "rowkey=a:t-a:s-a:b";
        List<String> keys = new ArrayList<String>();
        keys.add("a:t");
        keys.add("a:s");
        FileLoader.parseGenerationRule(rule, keys);
    }
}