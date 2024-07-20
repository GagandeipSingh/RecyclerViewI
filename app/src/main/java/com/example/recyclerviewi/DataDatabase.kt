package com.example.recyclerviewi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1, exportSchema = false)
abstract class DataDatabase : RoomDatabase() {
    abstract fun dataInterface() : DataInterFace

    // static members and functions inside companion object
    companion object{
        private var dataDatabase : DataDatabase?=null
        fun getInstance(context : Context): DataDatabase{
            if(dataDatabase == null){
                dataDatabase = Room.databaseBuilder(context,DataDatabase::class.java,"DataDataBase")
                    .allowMainThreadQueries()
                    .build()
            }
            return dataDatabase!!
        }
    }
}