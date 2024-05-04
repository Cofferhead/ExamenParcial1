package data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.io.Serializable
@JsonClass(generateAdapter = true)
data class Plant(
    val description: String,
    val growZoneNumber: Int,
    val imageUrl: String,
    val name: String,
    val plantId: String,
    val wateringInterval: Int
):Serializable
