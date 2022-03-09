package com.abdullrahman.weatherapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(roomDatabaseModel: RoomDataBaseModel)

    @Update
    suspend fun update(roomDatabaseModel: RoomDataBaseModel)

    @Query("SELECT * FROM place")
     fun  getAllPlaces():LiveData<List<RoomDataBaseModel>>

    @Query("Delete from place")
    suspend fun deleteAll()

    @Query("Delete from place WHERE id =:id")
    suspend fun delete(id:String)

}