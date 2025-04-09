package com.example.tasker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM Profile")
    fun getAll(): Flow<List<Profile>>
}