package com.example.examenparcial1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.examenparcial1.ui.theme.ExamenParcial1Theme
import com.example.examenparcial1.ui.theme.Sunflower.DetailPlant
import com.example.examenparcial1.ui.theme.Sunflower.addPlantToGarden
import data.DataProvided
import data.Plant

class PlantDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val plant = intent.getSerializableExtra("PLANT") as Plant
        setContent {
            ExamenParcial1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )  {

                    val goToMain = {
                        println("DEVOLVER, DEVOLVER, DEVOLVER")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    val shareButton = {plant:Plant ->
                        val salida = "${plant.name} \n ${plant.description}"
                        val sendIntent = Intent()
                            .apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, salida)
                                type = "text/plain"
                            }
                        val shareIntent = Intent.createChooser(
                            sendIntent, null
                        )
                        startActivity(shareIntent)
                    }
                    val addPlant = { plant:Plant ->
                        addPlantToGarden(plant, applicationContext)
                    }
                    DetailPlant(plant = plant, goToMain, shareButton,
                        addPlant, DataProvided(applicationContext).isInGarden(plant, applicationContext))
                }
            }
        }
    }
}