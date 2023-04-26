package com.briefing.brifieng6.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.briefing.brifieng6.database.`object`.RegisterData

//TODO: main interface for work with Database, all class
// in this package all classes instantiate this class
// using annotations we working with database - insert, update, delete, getAll
@Dao
interface DataDao {
    @get:Query("SELECT * FROM RegisterData")
    val all: LiveData<List<RegisterData>?>?

    @Query("SELECT * FROM RegisterData WHERE login =:login")
    fun getByLogin(login: String): List<RegisterData?>?

    @get:Query("SELECT * FROM RegisterData")
    val allLiveData: LiveData<List<RegisterData?>?>?

    @Query("DELETE FROM RegisterData")
    fun deleteAllData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lessonData: RegisterData?)

    @Update
    fun update(lessonData: RegisterData?)

    @Delete
    fun delete(lessonData: RegisterData?)
}