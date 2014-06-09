package com.anphoenix.data.util;

import com.anphoenix.data.model.Person;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by stefanie on 14-6-9.
 */
public class TypeConvertorTest extends TestCase {
    @Test
    public void testGetIntFromStr() throws Exception {
        int one = TypeConvertor.getInt("1");
        assertEquals(1, one);

        int zero = TypeConvertor.getInt("unNumber");
        assertEquals(0, zero);
    }

    @Test
    public void testGetBoolFromStr() throws Exception {
        boolean yes = TypeConvertor.getBool("true");
        assertEquals(true, yes);

        boolean no = TypeConvertor.getBool("false");
        assertEquals(false, no);

        boolean other = TypeConvertor.getBool("other");
        assertEquals(false, other);

    }

    @Test
    public void testGetBoolFromInt() throws Exception {
        boolean yes = TypeConvertor.getBool(1);
        assertEquals(true, yes);

        boolean no = TypeConvertor.getBool(0);
        assertEquals(false, no);

        boolean other = TypeConvertor.getBool(3);
        assertEquals(true, other);
    }

    @Test
    public void testGetGenderFromStr() throws Exception {
        Person.Gender female = TypeConvertor.getGender("F");
        assertEquals(Person.Gender.FEMALE, female);

        Person.Gender male = TypeConvertor.getGender("M");
        assertEquals(Person.Gender.MALE, male);

        Person.Gender other = TypeConvertor.getGender("L");
        assertEquals(Person.Gender.MALE, other);
    }
}
