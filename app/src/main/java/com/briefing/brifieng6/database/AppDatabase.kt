package com.briefing.brifieng6.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.briefing.brifieng6.database.`object`.UserData
import com.briefing.brifieng6.database.dao.DataDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "RegisterData"
                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
                this.instance = instance
                instance
            }
        }
        private val scope = CoroutineScope(Dispatchers.IO)
        /*private val populateDbTask = scope.launch {
            instance?.dataDao()?.insert(RegisterData())
        }*/
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDbTask.start()
            }
        }
    }
}
