package com.example.mvvmexample.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SubscriberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)


    @Query("DELETE FROM subscriber_table")
    suspend fun deleteAll()


    @Query("SELECT * FROM subscriber_table")
    fun getAllSubscribersList():LiveData<List<Subscriber>>
}