package com.example.makanapa.adapter

import NodeRecipeResponseItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.RecyclerView
import com.example.makanapa.databinding.FoodlistItemBinding

class FoodResultListAdapter(private val foodResultList : ArrayList<NodeRecipeResponseItem>) : RecyclerView.Adapter<FoodResultListAdapter.ViewHolder>() {



    private  var onItemClickCallBack : OnItemClickCallBack? = null

    fun setOnClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data : NodeRecipeResponseItem)
    }

    inner class ViewHolder(private val  binding : FoodlistItemBinding) : RecyclerView.ViewHolder(binding.root){
             fun bind(foodList : NodeRecipeResponseItem){
                binding.apply {
                        tvFoodName.text = foodList.menu
                        tvFoodKcal.text = "${foodList.kcal.toString()} KCal"
                }
             }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = foodResultList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodResultList[position])
        holder.itemView.setOnClickListener{
            onItemClickCallBack?.onItemClicked(foodResultList[position])
        }

    }
}