package com.briefing.brifieng6.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.briefing.brifieng6.database.`object`.RegisterData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [RegisterData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "lessonData"
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
