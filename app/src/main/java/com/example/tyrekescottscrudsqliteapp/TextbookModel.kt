package com.example.tyrekescottscrudsqliteapp

import java.util.*

data class TextbookModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var author: String = "",
    var course: String = "",
    var isbn: String = "",
){

    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}