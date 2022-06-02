package com.example.mvvmexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {

    abstract val subscriberDao : SubscriberDao

   companion object{
       @Volatile
       private var INSTANCE : SubscriberDatabase? = null
       fun getInstance(context: Context) : SubscriberDatabase{
           synchronized(this){
               var instance : SubscriberDatabase? = null
               if(instance == null){
                   instance = Room.databaseBuilder(
                       context.applicationContext,
                       SubscriberDatabase::class.java,
                       "subscriber_data_database"
                   ).build()

               }
               return instance
           }

       }
   }
}