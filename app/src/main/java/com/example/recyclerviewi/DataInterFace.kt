package com.example.recyclerviewi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataInterFace {

//    @Insert
//    fun insertData(dataEntity: DataEntity)

    @Insert
    fun insertData(dataEntity: DataEntity): Long // Returns the ID of the inserted row

    @Query("SELECT * FROM DataEntity WHERE id = :tempId")
    fun getEntity(tempId: Long): DataEntity

    @Query("SELECT * FROM DataEntity")
    fun getList() : List<DataEntity>

    @Delete
    fun deleteData(dataEntity: DataEntity)

    @Update
    fun updateData(dataEntity: DataEntity)

}