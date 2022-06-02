package com.example.mvvmexample.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.Event
import com.example.mvvmexample.db.Subscriber
import com.example.mvvmexample.db.SubscriberRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.selectUnbiased

class SubscribersViewModel(private val repo:SubscriberRepository):ViewModel(),Observable {


    val subscriber = repo.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete:Subscriber
    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val deleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get() = statusMessage

    init{
        saveOrUpdateButtonText.value = "Save"
        deleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        }
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputName.value = ""
        inputEmail.value = ""


    }

    fun clearOrDelte(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }
        else
           clearAll()

    }
    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
           val newRowId =  repo.insert(subscriber)
            if(newRowId > -1){
                statusMessage.value = Event("Inserted")
            }
            else
                statusMessage.value = Event("error")
        }
    }
    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            repo.update(subscriber)
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
           saveOrUpdateButtonText.value = "Save"
            deleteButtonText.value = "Clear all"
        }
    }
    fun clearAll(){
        viewModelScope.launch {
            repo.deleteAll()
        }
    }
    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repo.delete(subscriber)
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            deleteButtonText.value = "Clear all"
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        deleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}