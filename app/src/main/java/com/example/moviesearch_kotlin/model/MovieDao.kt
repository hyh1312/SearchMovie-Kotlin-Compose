package com.example.moviesearch_kotlin.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getAll(): Flow<List<Movie>>

    @Delete
    fun delete(Movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()
}