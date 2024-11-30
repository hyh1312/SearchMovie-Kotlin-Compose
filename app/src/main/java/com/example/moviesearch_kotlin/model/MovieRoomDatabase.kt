package com.example.moviesearch_kotlin.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): MovieRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            MovieRoomDatabase::class.java, "movie_database"
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // If you want to keep data through app restarts,
                // comment out the following block
                databaseWriteExecutor.execute {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    val dao = INSTANCE!!.movieDao()
                    dao.deleteAll()
                }
            }
        }
    }
}