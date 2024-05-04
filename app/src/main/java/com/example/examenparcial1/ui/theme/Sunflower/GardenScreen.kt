package com.example.examenparcial1.ui.theme.Sunflower

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import data.GardenPlant
import data.Plant
import java.io.File

//TODO Reeplace  all the plants in garden with plantedPlants
@Composable
fun GardenCard (plant: GardenPlant, goToGardenDetail:(plant: GardenPlant) -> Unit)
{
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(325.dp)
            .padding(15.dp)
            .background(Color(185, 200, 170), shape = RoundedCornerShape(15.dp))
            .clickable { goToGardenDetail(plant) },
        shape = RoundedCornerShape(15.dp)
    ) {
        AsyncImage(model = plant.imageUrl, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(185, 200, 170)))
        Box (modifier = Modifier
            .fillMaxSize()
            .background(Color(185, 200, 170)),
            contentAlignment = Alignment.TopCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(185, 200, 170))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Hacer las fechas TODO
                Text(text = plant.name, fontSize = 15.sp, color = Color.Black)
                Text(text = "Planted", color = Color.Black)
                Text(text = plant.plantedDate, color = Color.Black)//Provisional TODO
                Text(text = "Last Watered", color = Color.Black)
                Text(text = plant.lastWater, color = Color.Black)
            }
        }

    }
}
@Composable
fun GardenScreen (plants: List<GardenPlant>, goToGardenDetail: (plant: GardenPlant) -> Unit)
{
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .padding(10.dp)
        .fillMaxSize() )
    {
        items(plants)
        {
                plant -> GardenCard(plant = plant, goToGardenDetail)
        }
    }
}
fun isGardenEmpty (contexto: Context):Boolean
{
    var archivo = File(contexto.filesDir, "garden.json")
    if (archivo.exists())
    {
        archivo.createNewFile()
    }
    val jsonString = archivo.readText()
    if (jsonString == "")
    {
        return true
    }
        return false
}
@Composable
fun EmptyGarden (passToPlants:() -> Unit)
{
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your garden is empty", color = Color.Black)
            //Add plant here
            Button(onClick = passToPlants, colors = ButtonDefaults.buttonColors(Color(50, 100, 50))) {
                Text(text = "Add plant", color = Color.Black)
            }
        }

    }
}
//TODO Modify the DetailPlants: Use a single activity for both of garden and plants detail  activities
@Composable
fun DetailGarden (plant: GardenPlant, goMain:()-> Unit, shareIntent:(plant: GardenPlant) -> Unit)
{
    Box (contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(255, 255, 240))
    ) {

        AsyncImage(
            model = plant.imageUrl,
            contentDescription = "The delasign logo",
            onLoading = { println("Cargando") },
            onError = { println("Error") },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        )
        {
            OutlinedButton(
                onClick = { goMain() },
                shape = CircleShape,
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.White, shape = CircleShape),
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(0.dp, Color.White)
            ) {
                Icon(
                    rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.White),
                    tint = Color.Black,

                    )
            }
        }


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp), contentAlignment = Alignment.TopEnd)
        {
            OutlinedButton(
                onClick = { shareIntent(plant) },
                shape = CircleShape,
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.White, shape = CircleShape),
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(0.dp, Color.White)
            ) {
                Icon(
                    rememberVectorPainter(image = Icons.Rounded.Share),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.White),
                    tint = Color.Black,

                    )
            }
        }

        Column (modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.size(250.dp))
            //Arreglar los tamaños y colores TODO
            Text(text = plant.name, fontSize = 20.sp, color = Color.Black)
            Text(text = "Watering needs:", color = Color.Black)
            Text(text = "Every ${plant.wateringInterval.toString()} days", color = Color.Black)
            Text(text = plant.description, color = Color.Black,
                modifier = Modifier
                    .height(100.dp)
                    .verticalScroll(rememberScrollState()))
        }
    }
}