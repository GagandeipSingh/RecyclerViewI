package com.example.recyclerviewi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataEntity(
    @PrimaryKey (autoGenerate = true)
    var id : Long = 0,
    var name : String ? = null,
    var roll : String ? = null
)
