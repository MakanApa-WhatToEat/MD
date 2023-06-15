package com.example.makanapa.view.foodsearched

import NodeRecipeResponseItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makanapa.adapter.FoodResultListAdapter
import com.example.makanapa.api.NodeApiConfig
import com.example.makanapa.databinding.ActivityFoodSearchedBinding
import com.example.makanapa.view.foodlist.FoodDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class FoodSearchedActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFoodSearchedBinding
    private lateinit var adapter: FoodResultListAdapter

    companion object {
        const val EXTRA_KEY_SEARCHED = "extra_key_searched"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodSearchedBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Log.d("TAG", "Food searched enter")
        showLoading(true)

        supportActionBar?.title = "Searched Result"

        val searchedText = intent.getStringExtra(EXTRA_KEY_SEARCHED)

        if (searchedText != null) {

            getFood(searchedText )
            Log.d("TAG", searchedText)
        }


    }


    private fun getFood(searchResult: String) {
        NodeApiConfig.getApiService().getRecipe(
            searchResult
                .lowercase(Locale.getDefault())
        ).enqueue(object :
            Callback<List<NodeRecipeResponseItem>> {
            override fun onResponse(
                call: Call<List<NodeRecipeResponseItem>>,
                response: Response<List<NodeRecipeResponseItem>>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    Log.d("TAG", response.body().toString())
                    if (response.body()?.size!! > 0) {
                        binding.tvMakanApa.visibility = View.GONE
                        binding.ivMakanApa.visibility = View.GONE
                        setAdapter(ArrayList(response.body()))
                    } else {
                        binding.tvMakanApa.visibility = View.VISIBLE
                        binding.ivMakanApa.visibility = View.VISIBLE
                    }
                } else {
                    showLoading(false)
                    Log.d("TAG", response.message())
                }
            }

            override fun onFailure(
                call: Call<List<NodeRecipeResponseItem>>,
                t: Throwable
            ) {
                showLoading(false)
                Log.d("TAG", t.message.toString())
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbProgressBar.visibility = View.VISIBLE
        } else {
            binding.pbProgressBar.visibility = View.GONE
        }
    }

    private fun setAdapter(foodList: ArrayList<NodeRecipeResponseItem>) {
        adapter = FoodResultListAdapter(foodList)
        binding.rvFoodList.layoutManager = LinearLayoutManager(this@FoodSearchedActivity)
        binding.rvFoodList.adapter = adapter
        adapter.setOnClickCallBack(object : FoodResultListAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: NodeRecipeResponseItem) {
                val intent = Intent(this@FoodSearchedActivity, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.EXTRA_ID, data.id)
                intent.putExtra(FoodDetailActivity.EXTRA_MENU, data.menu)
                intent.putExtra(FoodDetailActivity.EXTRA_COOKING_TIME, data.cookingTime)
                intent.putExtra(FoodDetailActivity.EXTRA_KCAL, data.kcal.toString())
                intent.putExtra(FoodDetailActivity.EXTRA_RECIPE, data.recipe)
                intent.putStringArrayListExtra(
                    FoodDetailActivity.EXTRA_INGREDIENTS,
                    data.ingredients
                )
                startActivity(intent)
            }

        })
    }

}