package com.example.makanapa.view.foodlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.makanapa.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {


    lateinit var binding : ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val foodName = intent.getStringExtra(EXTRA_MENU)
        val foodKcal = intent.getStringExtra(EXTRA_KCAL)
        val foodCoockingTime = intent.getStringExtra(EXTRA_COOKING_TIME)
        val foodCategory = intent.getStringExtra(EXTRA_CATEGORY)
        val foodIngredients = intent.getStringExtra(EXTRA_INGREDIENTS)


        binding.tvDetailFoodName.text = foodName?.capitalize()
        binding.tvDetailFoodKcal.text = "$foodKcal KCal"
        binding.tvDetailFoodCategory.text = foodCategory
        binding.tvDetailFoodCookingTime.text = foodCoockingTime
        binding.tvDetailFoodStepByStep.text = foodIngredients



    }


    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_MENU = "extra_menu"
        const val EXTRA_COOKING_TIME = "extra_cooking_time"
        const val EXTRA_KCAL = "extra_kcal"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_INGREDIENTS = "extra_ingredients"
    }

}