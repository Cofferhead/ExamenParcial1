package data

import java.io.Serializable

class GardenPlant(
    val description: String,
    val growZoneNumber: Int,
    val imageUrl: String,
    val name: String,
    val plantId: String,
    val wateringInterval: Int,
    val plantedDate: String,
    val lastWater:String
):Serializable