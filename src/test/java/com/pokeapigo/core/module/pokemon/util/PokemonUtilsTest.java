package com.pokeapigo.core.module.pokemon.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokemonUtilsTest {

    @Test
    void pokemonConstantsPrivateConstructorTest() throws NoSuchMethodException {
        Constructor<PokemonConstants> c = PokemonConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(c.getModifiers()));

        c.setAccessible(true);
        assertThrows(InvocationTargetException.class, c::newInstance);
    }

}