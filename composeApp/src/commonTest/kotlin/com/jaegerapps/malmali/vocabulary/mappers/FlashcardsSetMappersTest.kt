package com.jaegerapps.malmali.vocabulary.mappers

import com.jaegerapps.malmali.vocabulary.domain.mapper.createCards
import kotlin.test.Test
import kotlin.test.assertEquals

class FlashcardsSetMappersTest {
    @Test
    fun testMatchingWordsAndDefinitions() {
        val words = arrayOf("apple", "banana")
        val definitions = arrayOf("A fruit", "Another fruit")
        val result = createCards(words, definitions)
        assertEquals(2, result.size)
        assertEquals("apple", result[0].word)
        assertEquals("A fruit", result[0].definition)
        assertEquals("banana", result[1].word)
        assertEquals("Another fruit", result[1].definition)
    }

    @Test
    fun testWordsArrayLongerThanDefinitions() {
        val words = arrayOf("apple", "banana", "cherry")
        val definitions = arrayOf("A fruit", "Another fruit")
        val result = createCards(words, definitions)
        assertEquals(2, result.size)
    }

    @Test
    fun testDefinitionsArrayLongerThanWords() {
        val words = arrayOf("apple", "banana")
        val definitions = arrayOf("A fruit", "Another fruit", "Yet another fruit")
        val result = createCards(words, definitions)
        assertEquals(2, result.size)
    }

    @Test
    fun testEmptyArrays() {
        val words = emptyArray<String>()
        val definitions = emptyArray<String>()
        val result = createCards(words, definitions)
        assertEquals(0, result.size)
    }

}