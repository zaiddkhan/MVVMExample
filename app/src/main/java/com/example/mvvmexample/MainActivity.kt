package com.example.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmexample.databinding.ActivityMainBinding
import com.example.mvvmexample.db.Subscriber
import com.example.mvvmexample.db.SubscriberDao
import com.example.mvvmexample.db.SubscriberDatabase
import com.example.mvvmexample.db.SubscriberRepository
import com.example.mvvmexample.viewmodels.SubscriberViewModelFactory
import com.example.mvvmexample.viewmodels.SubscribersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var subsViewModel : SubscribersViewModel
    private lateinit var adapter: SubscribersListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subsViewModel = ViewModelProvider(this,factory).get(SubscribersViewModel::class.java)
        binding.myViewModel = subsViewModel
        binding.lifecycleOwner = this
        setRecyclerView()

        subsViewModel.message.observe(this,Observer{
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun displaySubscribersList(){
        subsViewModel.subscriber.observe(this, Observer {
            adapter.setList(it)

        })
    }
    private fun setRecyclerView(){
        binding.subscriberRecView.layoutManager = LinearLayoutManager(this)
        adapter =  SubscribersListAdapter({selectedItem:Subscriber -> listItemCLicked(selectedItem)})

        displaySubscribersList()
    }

    private fun listItemCLicked(subscriber: Subscriber){
        subsViewModel.initUpdateAndDelete(subscriber)

    }
}