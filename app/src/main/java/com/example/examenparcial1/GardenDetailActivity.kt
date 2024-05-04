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
import com.example.examenparcial1.ui.theme.Sunflower.DetailGarden
import com.example.examenparcial1.ui.theme.Sunflower.DetailPlant
import data.DataProvided
import data.GardenPlant
import data.Plant

class GardenDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val plant = intent.getSerializableExtra("GARDENED") as GardenPlant
        val data = DataProvided(applicationContext)
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
                    val shareButton = {plant:GardenPlant ->
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
                    DetailGarden(plant = plant, goToMain, shareButton)
                }
            }
        }
    }
}