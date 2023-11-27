package com.example.cccd_io_kotlin_android.presentations.guest

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cccd_io_kotlin_android.R
import com.example.cccd_io_kotlin_android.components.Variables
import com.example.cccd_io_kotlin_android.components.gnb.TopAppBar
import com.example.cccd_io_kotlin_android.components.images.GuestImage
import com.example.cccd_io_kotlin_android.navigations.Screen
import com.example.cccd_io_kotlin_android.ui.theme.AppTheme

@Composable
fun TryAsGuestScreen(navController: NavController) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://cccd.io/")) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(title = "Indentify verification", onGoBack = {
            navController.popBackStack()
        })
        Row(
            modifier = Modifier
                .weight(1F)
                .padding(start = Variables.CornerL, end = Variables.CornerL),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(59.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(contentAlignment = Alignment.BottomCenter){
                    Image(
                        painter = painterResource(id = R.drawable.ellipse1),
                        contentDescription = "image description",
                        contentScale = ContentScale.None
                    )
                    GuestImage()
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(Variables.CornerM, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(text = "Try as a guest", style = MaterialTheme.typography.headlineLarge)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            Variables.CornerM,
                            Alignment.Top
                        ),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = "Not signed up yet? You can try CCCD.IO as a guest, but you won’t see the results on the dashboard.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1D1B1E)
                        )
                        Text(
                            text = "Any photos, videos, or document details submitted will not be processed, stored, or otherwise used.",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1D1B1E)
                        )
                    }
                }
            }

        }
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(Variables.CornerS, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp)
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = { navController.navigate(Screen.IntoSDKScreen.route) }) {
                    Text(
                        text = "Continue as a guest",
                    )
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {  context.startActivity(intent)  },
                ) {
                    Text(
                        text = "Contact us"
                    )
                }
            }
        }
    }
}