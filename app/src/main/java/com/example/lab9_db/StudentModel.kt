package com.example.lab9_db

import java.util.*

data class StudentModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var major: String = ""
)
{
    companion object {
        fun getAutoId(): Int {
            return Random().nextInt(100)
        }
    }
}