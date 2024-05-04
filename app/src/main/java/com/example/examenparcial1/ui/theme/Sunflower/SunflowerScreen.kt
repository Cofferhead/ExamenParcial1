package com.example.examenparcial1.ui.theme.Sunflower

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.TabRow
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import data.GardenPlant
import data.Plant
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (plants:List<Plant>,
                goDetailPlants: (plant: Plant) -> Unit,
                goToGardenDetail: (plant: GardenPlant) -> Unit,
                context:Context)
{

    var estado by remember { mutableStateOf(1) }
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar= {
            CenterAlignedTopAppBar(title = { Text(text = "Sunflower", color = Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ))
        }
    ){

        values ->
        Column(
            modifier = Modifier
                .background(Color(255, 255, 240))
                .fillMaxSize()
        ) {
        Spacer(modifier = Modifier
            .height(50.dp)
            .padding(values))
            TabRow(selectedTabIndex = estado,
                modifier = Modifier.background(Color(0,100,0)),
                contentColor = Color.Green,
                containerColor = Color.Green

                ) {
                Tab(selected = (0==estado), modifier = Modifier.background(Color(255, 255, 240)),
                    onClick = { estado = 0 },
                    text = { Text(text = "Garden", maxLines = 1, color = Color(0,100,0))},
                    icon = { Icon(
                        imageVector = Icons.Rounded.Forest,
                        contentDescription = null,
                        tint = Color(0,100,0)
                    ) }
                )
                Tab(selected = (1==estado), onClick = { estado = 1 }, modifier = Modifier.background(Color(255, 255, 240)),
                    text = {Text(text = "Plants", color = Color(0,100,0))},
                    icon = { Icon(
                        imageVector = Icons.Rounded.Forest,
                        contentDescription = null,
                        tint = Color(0,100,0)
                    ) }
                    )
            }
            //Spacer (modifier = Modifier.height(100.dp))
            if (estado == 1) {
                PlantsScreen(plants = plants, goDetailPlants = goDetailPlants)
            }
            else{
                if (isGardenEmpty(context))
                {
                    EmptyGarden({estado = 1})
                }
                else{
                    readGarden(context)?.let { GardenScreen(plants = it, goToGardenDetail) }
                }
                //EmptyGarden()
            }
        }
    }
}
//Estaba probando aqui.
@SuppressLint("SuspiciousIndentation")
fun addPlantToGarden (plant:Plant, contexto:Context) {
   //TODO repite plantas que ya pusiste, orden:Superficial
    //Creamos el archivo si no existe
    val file:File = File(contexto.filesDir, "garden.json")
    var jsonAuxObject:JSONObject
    var jsonList:MutableList<JSONObject> = mutableListOf<JSONObject>()
    val time = Calendar.getInstance().time
    val formater = SimpleDateFormat("yyyy-MM-dd")
    val current = formater.format(time)
    if (!file.exists())
    {
        file.createNewFile()
    }
    //Leemos todos los GardenPlants en garden.json
    //Si hay objetos dentro entonces los leemos
    if (!isGardenEmpty(contexto)) {
        //Leemos el archivo en string
        var content = file.readText()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val dataListType = Types.newParameterizedType(List::class.java, GardenPlant::class.java)
        val adapter: JsonAdapter<List<GardenPlant>> = moshi.adapter(dataListType)
        val gardenPlants:List<GardenPlant>? = adapter.fromJson(content)
        if (gardenPlants != null)
        {
            //los parseamos a json en un arreglo json
            for (actualPlant in gardenPlants)
            {
                jsonAuxObject = JSONObject()
                try {
                    jsonAuxObject.put("plantId", actualPlant.plantId)
                    jsonAuxObject.put("name", actualPlant.name)
                    jsonAuxObject.put("description", actualPlant.description)
                    jsonAuxObject.put("growZoneNumber", actualPlant.growZoneNumber)
                    jsonAuxObject.put("wateringInterval", actualPlant.wateringInterval)
                    jsonAuxObject.put("imageUrl", actualPlant.imageUrl)
                    jsonAuxObject.put("plantedDate", actualPlant.plantedDate)
                    jsonAuxObject.put("lastWater", actualPlant.lastWater)
                } catch (ex: Exception) {
                    print(ex.message)
                }
                jsonList.add(jsonAuxObject)
            }
        }
    }
    //Parseamos a  json a la plant
    jsonAuxObject = JSONObject()
    try {
        jsonAuxObject.put("plantId", plant.plantId)
        jsonAuxObject.put("name", plant.name)
        jsonAuxObject.put("description", plant.description)
        jsonAuxObject.put("growZoneNumber", plant.growZoneNumber)
        jsonAuxObject.put("wateringInterval", plant.wateringInterval)
        jsonAuxObject.put("imageUrl", plant.imageUrl)
        jsonAuxObject.put("plantedDate", current)
        jsonAuxObject.put("lastWater", current)
    } catch (ex: Exception) {
        print(ex.message)
    }
    //Lo añadimos al arregloJson
    jsonList.add(jsonAuxObject)
    //Lo escribimos en el archivo
    file.writeText(jsonList.toString())


}
public fun readGarden (contexto: Context):List<GardenPlant>?
{
    var archivo = File(contexto.filesDir, "garden.json")
    if (!archivo.exists())
    {
        archivo.createNewFile()
    }
    val gardenList : List<GardenPlant>?
    val jsonString = archivo.readText()
    if (jsonString != "") {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val dataListType =
            Types.newParameterizedType(List::class.java, GardenPlant::class.java)
        val adapter: JsonAdapter<List<GardenPlant>> = moshi.adapter(dataListType)
        gardenList = adapter.fromJson(jsonString)
        if (gardenList != null) {
            return gardenList
        }
    }
    return null
}


