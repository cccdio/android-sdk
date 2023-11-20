package com.example.cccd_io_kotlin_android.presentations.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.icons.LogoIcon
import com.example.cccd_io_kotlin_android.components.images.DecorationImage
import com.example.cccd_io_kotlin_android.navigations.Screen


@Composable
fun HomeScreen(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier

                .padding(top = 32.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(
                8.735279083251953.dp,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.Top,
        ) {
            LogoIcon()
            Text(
                text = "CCCD.IO",
                style = TextStyle(
                    fontSize = 26.79.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF4B0082),
                )
            )
        }
        Row(
            modifier = Modifier
                .weight(1F),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DecorationImage()
        }
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Button(modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF7B41B3),
                        shape = RoundedCornerShape(size = 100.dp)
                    ), onClick = { /*TODO*/ }) {
                    Text(
                        text = "Connect my CCCD account",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.1.sp,
                        )
                    )
                }
                Button(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFF7C757E),
                            shape = RoundedCornerShape(size = 100.dp)
                        )
                        .fillMaxWidth(),

                    onClick = { navController.navigate(Screen.VerificationScreen.route) },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Continue as a guest",

                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF7B41B3),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.1.sp,
                        )
                    )
                }
            }
        }
    }
}