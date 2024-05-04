package com.example.examenparcial1

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.JsonWriter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examenparcial1.ui.theme.ExamenParcial1Theme
import com.example.examenparcial1.ui.theme.Sunflower.MainScreen
import com.example.examenparcial1.ui.theme.Sunflower.addPlantToGarden
import com.example.examenparcial1.ui.theme.Sunflower.isGardenEmpty
import data.DataProvided
import data.GardenPlant
import data.Plant
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.Writer
import java.nio.charset.Charset
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalStdlibApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aux = Calendar.getInstance().time
        val formater = SimpleDateFormat("yyyy-MM-dd")
        val current = formater.format(aux)
        val dato = DataProvided(applicationContext)
        val rnd = (0..<dato.plants.size).random()
        val planta = dato.plants[rnd]
        setContent {
            ExamenParcial1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val goDetailPlants = {plant:Plant ->
                        val intent = Intent(this, PlantDetailActivity::class.java)
                        intent.putExtra("PLANT", plant)
                        startActivity(intent)
                    }
                    val goToGardenDetailActivity = {plant:GardenPlant ->
                        val intent = Intent(this, GardenDetailActivity::class.java)
                        intent.putExtra("GARDENED", plant)
                        startActivity(intent)
                    }


                    MainScreen(dato.plants, goDetailPlants, goToGardenDetailActivity, applicationContext)

                }

            }
        }

    }


}

