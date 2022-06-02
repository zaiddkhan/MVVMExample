package com.example.mvvmexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmexample.databinding.ItemListBinding
import com.example.mvvmexample.db.Subscriber

class SubscribersListAdapter(private val clickListener:(Subscriber)->Unit):RecyclerView.Adapter<SubscribersListAdapter.MyViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()


    class MyViewHolder(val binding:ItemListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(subscriber:Subscriber,clickListener: (Subscriber) -> Unit){
            binding.nameTv.text = subscriber.name
            binding.emailTv.text = subscriber.email
            binding.listItem.setOnClickListener {
                clickListener(subscriber)
            }
        }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemListBinding = androidx.databinding.DataBindingUtil.inflate(layoutInflater,R.layout.item_list,parent,false)
        return MyViewHolder(binding)

    }

    fun setList(subscribers : List<Subscriber>){
        subscribersList.addAll(subscribers)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }


}