package com.jaegerapps.malmali.common.models

import com.jaegerapps.malmali.MR
import dev.icerock.moko.resources.ImageResource


sealed class IconResource(
    val resource: ImageResource,
    val tag: String,
) {
    //This will be used to handle the images
    //The tags will be uploaded to the user's account online
    //When we get a tag, we return the IconResource containing the icon connected.
    //This system relies on the resources being offline, if online, will prolly have to change some things.
    data object Bear_One : IconResource(
        resource = MR.images.user_icon_bear_1,
        tag = "bear 1"
    )

    data object Bear_Two : IconResource(
        resource = MR.images.user_icon_bear_2,
        tag = "bear 2"
    )

    data object Bear_Three : IconResource(
        resource = MR.images.user_icon_bear_3,
        tag = "bear 3"
    )

    data object Bear_Four : IconResource(
        resource = MR.images.user_icon_bear_4,
        tag = "bear 4"
    )

    data object Bear_Five : IconResource(
        resource = MR.images.user_icon_bear_5,
        tag = "bear 4"
    )

    data object Bear_Six : IconResource(
        resource = MR.images.user_icon_bear_6,
        tag = "bear 6"
    )

    data object Bear_Seven : IconResource(
        resource = MR.images.user_icon_bear_7,
        tag = "bear 7"
    )

    data object Bear_Eight : IconResource(
        resource = MR.images.user_icon_bear_8,
        tag = "bear 8"
    )

    data object Bear_Nine : IconResource(
        resource = MR.images.user_icon_bear_9,
        tag = "bear 9"
    )

    data object Bear_Ten : IconResource(
        resource = MR.images.user_icon_bear_10,
        tag = "bear 10"
    )

    data object Bear_Eleven : IconResource(
        resource = MR.images.user_icon_bear_11,
        tag = "bear 11"
    )

    data object Bear_Twelve : IconResource(
        resource = MR.images.user_icon_bear_12,
        tag = "bear 12"
    )

    data object Bear_Thirteen : IconResource(
        resource = MR.images.user_icon_bear_13,
        tag = "bear 13"
    )

    data object Bear_Fourteen : IconResource(
        resource = MR.images.user_icon_bear_14,
        tag = "bear 14"
    )


    companion object {
        fun resourceFromTag(tag: String): IconResource {
            return when (tag) {
                "bear 1" -> Bear_One
                "bear 2" -> Bear_Two
                "bear 3" -> Bear_Three
                "bear 4" -> Bear_Four
                "bear 5" -> Bear_Five
                "bear 6" -> Bear_Six
                "bear 7" -> Bear_Seven
                "bear 8" -> Bear_Eight
                "bear 9" -> Bear_Nine
                "bear 10" -> Bear_Ten
                "bear 11" -> Bear_Eleven
                "bear 12" -> Bear_Twelve
                "bear 13" -> Bear_Thirteen
                "bear 14" -> Bear_Fourteen
                else -> Bear_One
            }
        }

        fun tagFromResource(resource: IconResource): String {
            return when (resource) {
                Bear_One -> "bear 1"
                Bear_Two -> "bear 2"
                Bear_Three -> "bear 3"
                Bear_Four -> "bear 4"
                Bear_Five -> "bear 5"
                Bear_Six -> "bear 6"
                Bear_Seven -> "bear 7"
                Bear_Eight -> "bear 8"
                Bear_Nine -> "bear 9"
                Bear_Ten -> "bear 10"
                Bear_Eleven -> "bear 11"
                Bear_Twelve -> "bear 12"
                Bear_Thirteen -> "bear 13"
                Bear_Fourteen -> "bear 14"
            }
        }

        val userIconList = listOf(
            Bear_One,
            Bear_Two,
            Bear_Three,
            Bear_Four,
            Bear_Five,
            Bear_Six,
            Bear_Seven,
            Bear_Eight,
            Bear_Nine,
            Bear_Ten,
            Bear_Eleven,
            Bear_Twelve,
            Bear_Thirteen,
            Bear_Fourteen,
        )
    }
}

