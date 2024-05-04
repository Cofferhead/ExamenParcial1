package data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class DataProvided (context:Context) {
    var plants : List<Plant> = emptyList()
    var plantedPlants:List<GardenPlant> = emptyList()
    init {
        read(context)
        //readGarden(context)
    }
    private fun read (contexto: Context)
    {
        val ruta : String = "plants.json"
        val contactosAux : List<Plant>?

        val jsonString = contexto.assets.open(ruta).bufferedReader().use {
            it.readText()
        }
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val dataListType = Types.newParameterizedType(List::class.java, Plant::class.java)
        val adapter: JsonAdapter<List<Plant>> = moshi.adapter(dataListType)
        contactosAux = adapter.fromJson(jsonString)
        if (contactosAux != null) {
            plants = contactosAux
        }
    }
    public fun isInGarden (plant:Plant, contexto: Context):Boolean
    {
        readGarden(contexto = contexto)
        for (garden in plantedPlants)
        {
            if (plant.name == garden.name)
            {
                return true
            }
        }
        return false
    }
    private fun readGarden (contexto: Context)
    {
        var archivo = File(contexto.filesDir, "garden.json")
        if (!archivo.exists())
        {
            archivo.createNewFile()
        }
        val ruta : String = archivo.path
        val contactosAux : List<GardenPlant>?
        /*val jsonString = contexto.assets.open(ruta).bufferedReader().use {
            it.readText()
        }*/
        val jsonString = archivo.readText()
        if (jsonString != "") {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val dataListType =
                Types.newParameterizedType(List::class.java, GardenPlant::class.java)
            val adapter: JsonAdapter<List<GardenPlant>> = moshi.adapter(dataListType)
            contactosAux = adapter.fromJson(jsonString)
            if (contactosAux != null) {
                plantedPlants = contactosAux
            }
        }
    }
}
