package com.example.wordoftheday.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wordoftheday.model.Word


@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDAO

    //Singleton Design Pattern
    companion object {

        @Volatile
        private var INSTANCE: WordDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                WordDatabase::class.java,
                "user_database"
            ).build()
            INSTANCE = instance
            instance

        }
    }
}