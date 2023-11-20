package com.example.cccd_io_kotlin_android.components.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cccd_io_kotlin_android.R
import com.example.cccd_io_kotlin_android.components.SVGLoader

@Composable
fun LogoIcon() {
    Box {
        SVGLoader(model = R.drawable.logo, modifier = Modifier.size(39.dp))
    }
}

