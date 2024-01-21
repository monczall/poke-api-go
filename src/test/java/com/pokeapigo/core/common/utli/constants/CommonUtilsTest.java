package com.pokeapigo.core.common.utli.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonUtilsTest {

    @Test
    void apiConstantsPrivateConstructorTest() throws NoSuchMethodException {
        Constructor<ApiConstants> c = ApiConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(c.getModifiers()));

        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void constantsPrivateConstructorTest() throws NoSuchMethodException {
        Constructor<Constants> c = Constants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(c.getModifiers()));

        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void dataBaseConstantsPrivateConstructorTest() throws NoSuchMethodException {
        Constructor<DataBaseConstants> c = DataBaseConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(c.getModifiers()));

        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }
}