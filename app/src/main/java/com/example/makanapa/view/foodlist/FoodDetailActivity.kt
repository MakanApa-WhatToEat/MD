package com.example.makanapa.view.foodlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.makanapa.databinding.ActivityFoodDetailBinding
import java.util.Locale

class FoodDetailActivity : AppCompatActivity() {


    lateinit var binding : ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "FoodDetail"
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val foodName = intent.getStringExtra(EXTRA_MENU)
        val foodKcal = intent.getStringExtra(EXTRA_KCAL)
        val foodCoockingTime = intent.getStringExtra(EXTRA_COOKING_TIME)
        val foodRecipe = intent.getStringExtra(EXTRA_RECIPE)
        val foodIngredients = intent.getStringArrayListExtra(EXTRA_INGREDIENTS)


        binding.tvDetailFoodName.text = capitalizeFirstLetter(foodName.toString())
        binding.tvDetailFoodKcal.text = "$foodKcal KCal"
//        binding.tvDetailFoodCategory.text = foodCategory
        binding.tvRecipe.text = foodRecipe
        binding.tvDetailFoodCookingTime.text = foodCoockingTime

        var tempIngredients = ""

        foodIngredients?.forEach {
            tempIngredients = "$tempIngredients$it\n"
        }

        binding.tvDetailFoodStepByStep.text = tempIngredients




    }

    fun capitalizeFirstLetter(sentence: String): String {
        val words = sentence.split(" ")
        val capitalizedWords = words.map { it.replaceFirstChar { char -> char.uppercase() } }
        return capitalizedWords.joinToString(" ")
    }


    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_MENU = "extra_menu"
        const val EXTRA_COOKING_TIME = "extra_cooking_time"
        const val EXTRA_KCAL = "extra_kcal"
        const val EXTRA_RECIPE = "extra_recipe"
        const val EXTRA_INGREDIENTS = "extra_ingredients"
        const val EXTRA_CATEGORY = "extra_category"
    }

}