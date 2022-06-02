package com.example.mvvmexample.db

class SubscriberRepository(private val dao:SubscriberDao) {

    val subscribers = dao.getAllSubscribersList()

    suspend fun insert(subscriber: Subscriber):Long{
      return  dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

}