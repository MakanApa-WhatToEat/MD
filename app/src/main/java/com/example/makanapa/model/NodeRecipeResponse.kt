import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NodeRecipeResponse(
	@SerializedName("NodeRecipeResponse")
	val nodeRecipeResponse: List<NodeRecipeResponseItem>
)

@Parcelize
data class NodeRecipeResponseItem(
	@field:SerializedName("kcal")
	val kcal: Int,

	@field:SerializedName("recipe")
	val recipe: String,

	@field:SerializedName("cooking_time")
	val cookingTime: String,

	@field:SerializedName("ingredients")
	val ingredients: ArrayList<String?>,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("menu")
	val menu: String
) : Parcelable
