import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NodeRecipeResponse(
	@SerializedName("NodeRecipeResponse")
	val nodeRecipeResponse: List<NodeRecipeResponseItem>
)

@Parcelize
data class NodeRecipeResponseItem(
	@SerializedName("_id")
	val id: String,
	@SerializedName("menu")
	val menu: String,
	@SerializedName("cooking_time")
	val cookingTime: String,
	@SerializedName("kcal")
	val kcal: Int,
	@SerializedName("category")
	val category: String,
	@SerializedName("ingredients")
	val ingredients: String
) : Parcelable
