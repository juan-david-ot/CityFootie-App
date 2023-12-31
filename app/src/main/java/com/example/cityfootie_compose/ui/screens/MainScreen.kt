package com.example.cityfootie_compose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    goLoginScreen: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar() {
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = "¡¡Bienvenido a CityFootie!!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
            )
        }
    }) {
        MainBodyContent(goLoginScreen)
    }
}

@Composable
fun MainBodyContent(goLoginScreen: () -> Unit) {
    AsyncImage(
        model = "https://w0.peakpx.com/wallpaper/941/855/HD-wallpaper-football-field-aerial-view-trees-playground-green.jpg",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(10000.dp)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row() {
            Button(
                onClick = {
                    goLoginScreen()
                }
            ) {
                Text(
                    text = "Entra Aquí",
                    fontSize = 30.sp
                )
            }
        }
    }
}