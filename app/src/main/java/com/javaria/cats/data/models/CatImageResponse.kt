package com.javaria.cats.data.models

/**
 * data model for Cat image response
 */
data class CatImageResponse(
    val id: String,
    val url:String?,
    val breeds:List<String>?,
    val width:Int?,
    val height:Int?
)