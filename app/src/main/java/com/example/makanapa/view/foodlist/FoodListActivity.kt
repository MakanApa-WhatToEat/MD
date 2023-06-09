package com.example.makanapa.view.foodlist

import NodeRecipeResponseItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makanapa.R
import com.example.makanapa.adapter.FoodResultListAdapter
import com.example.makanapa.databinding.ActivityFoodListBinding
import com.example.makanapa.view.camera.CameraMainActivity

class FoodListActivity : AppCompatActivity() {


    private lateinit var binding : ActivityFoodListBinding
    private lateinit var adapter: FoodResultListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = intent.getParcelableArrayListExtra<NodeRecipeResponseItem>("nodeFoodList")
        if(list != null){
                setAdapter(list)
                Log.d("TAG", "Food List ${list}")
        }else{
            Log.d("TAG", "Tidak ada data")
        }

    }

    private fun setAdapter(foodList : ArrayList<NodeRecipeResponseItem>){
        adapter = FoodResultListAdapter(foodList)
        binding.rvFoodList.layoutManager =LinearLayoutManager(this@FoodListActivity)
        binding.rvFoodList.adapter = adapter
        adapter.setOnClickCallBack(object : FoodResultListAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: NodeRecipeResponseItem) {
                val intent = Intent(this@FoodListActivity, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.EXTRA_ID, data.id)
                intent.putExtra(FoodDetailActivity.EXTRA_MENU, data.menu)
                intent.putExtra(FoodDetailActivity.EXTRA_COOKING_TIME, data.cookingTime)
                intent.putExtra(FoodDetailActivity.EXTRA_KCAL, data.kcal.toString())
                intent.putExtra(FoodDetailActivity.EXTRA_CATEGORY, data.category)
                intent.putExtra(FoodDetailActivity.EXTRA_INGREDIENTS, data.ingredients)
                startActivity(intent)
            }

        })
    }


    override fun onBackPressed() {
        val intent = Intent(this@FoodListActivity, CameraMainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }



}