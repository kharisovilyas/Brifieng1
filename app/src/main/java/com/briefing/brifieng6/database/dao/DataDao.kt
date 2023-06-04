package com.briefing.brifieng6.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.briefing.brifieng6.database.`object`.UserData

//TODO: main interface for work with Database, all class
// in this package all classes instantiate this class
// using annotations we working with database - insert, update, delete, getAll
@Dao
interface DataDao {
    @get:Query("SELECT * FROM RegisterData")
    val all: LiveData<List<UserData>?>?

    @Query("SELECT * FROM RegisterData WHERE login =:login")
    fun getByLogin(login: String): List<UserData?>?

    @get:Query("SELECT * FROM RegisterData")
    val allLiveData: LiveData<List<UserData>>?

    @Query("DELETE FROM RegisterData")
    fun deleteAllData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lessonData: UserData?)

    @Update
    fun update(lessonData: UserData?)

    @Delete
    fun delete(lessonData: UserData?)
}