package com.example.examenparcial1.ui.theme.Sunflower

import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import data.Plant


@Composable
fun PlantCard (plant: Plant, goDetailPlants:(plant: Plant) -> Unit,)
{
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(Color.Green, shape = RoundedCornerShape(15.dp))
            .clickable { goDetailPlants(plant) },
        shape = RoundedCornerShape(15.dp)
    ) {

        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(185, 200, 170)) )
        {

            AsyncImage(
                model = plant.imageUrl,
                contentDescription = "The delasign logo",
                onLoading = { println("Cargando") },
                onError = { println("Error") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop

            )

            /*KamelImage(resource = asyncPainterResource(data = plant.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillBounds)*/


            Box(
                modifier = Modifier
                    .background(Color(185, 200, 170))
                    .padding(10.dp)
                    .height(40.dp),
                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = plant.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )

                )

            }
        }
    }
}

@Composable
fun DetailPlant (plant:Plant,
                 goMain:()-> Unit,
                 shareIntent:(plant:Plant) -> Unit,
                 addPlant:(plant:Plant) -> Unit,
                 isPlanted:Boolean
) {
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

        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            if (!isPlanted) {
                FloatingActionButton(
                    //Add plant
                    onClick = { addPlant(plant)
                              goMain},
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Icon(Icons.Rounded.Add, "Large floating action button")
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp), contentAlignment = Alignment.TopEnd)
        {
            OutlinedButton(
                onClick = {
                    shareIntent(plant)
                },
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
            Text(text = plant.name, fontSize = 20.sp, color = Color.Black)
            Text(text = "Watering needs:", color = Color.Black)
            Text(text = "Every ${plant.wateringInterval.toString()} days", color = Color.Black)
            transformHtlml(inHtml = plant.description)

        }
    }
}
@Composable
fun PlantsScreen (plants: List<Plant>, goDetailPlants:(plant:Plant)->Unit)
{
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
        .padding(10.dp)
        .fillMaxSize() )
    {
        items(plants)
        {
                plant -> PlantCard(plant = plant, goDetailPlants)
        }
    }
}
@Composable
fun transformHtlml (inHtml:String)
{
    var aux = Html.fromHtml(inHtml, 0)
    Text (aux.toString(), color = Color.Black,
        modifier = Modifier.verticalScroll(rememberScrollState()))
}