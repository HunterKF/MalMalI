package com.jaegerapps.malmali.vocabulary.mapper

import com.jaegerapps.malmali.MR
import com.jaegerapps.malmali.composeApp.database.FlashcardSets
import com.jaegerapps.malmali.composeApp.database.Flashcards
import com.jaegerapps.malmali.vocabulary.create_set.presentation.toSetMode
import com.jaegerapps.malmali.vocabulary.models.UiFlashcard
import com.jaegerapps.malmali.vocabulary.models.VocabSet

fun FlashcardSets.toVocabSet(): VocabSet {
    println("set_id")
    println(set_id)
    return VocabSet(
        setId = set_id,
        title = set_title,
        icon = MR.images.cat_icon,
        expanded = false,
        isPrivate = is_public.toSetMode(),
        dateCreated = date_created
    )
}

fun Flashcards.toUiFlashcard(): UiFlashcard {
    return UiFlashcard(
        uiId = id,
        cardId = id,
        word = word,
        def = meaning,
        level = memorization_level ?: 1,
        error = false
    )
}