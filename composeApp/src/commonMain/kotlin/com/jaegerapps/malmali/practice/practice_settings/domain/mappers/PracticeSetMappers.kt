package com.jaegerapps.malmali.practice.practice_settings.domain.mappers

import com.jaegerapps.malmali.practice.practice_settings.domain.models.PracticeSetModel
import com.jaegerapps.malmali.vocabulary.data.models.SetEntity
import com.jaegerapps.malmali.vocabulary.domain.mapper.toVocabSet

fun SetEntity.toPracticeSetModel(): PracticeSetModel {
    return PracticeSetModel(
        set = this.toVocabSet(),
        isSelected = false
    )
}