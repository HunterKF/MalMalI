package com.jaegerapps.malmali.vocabulary.data.models

data class SetEntity(
    val set_id: Long?,
    //Public id is going to be used for the linked_set
    val linked_set: Long,
    val set_title: String,
    val tags: String?,
    val date_created: String?,
    val is_author: Long,
    val is_public: Long,
    val set_icon: String
)
