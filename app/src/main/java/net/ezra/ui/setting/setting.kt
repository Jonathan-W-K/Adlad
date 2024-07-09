package net.ezra.ui.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import net.ezra.navigation.ROUTE_SETTING_PG
import net.ezra.navigation.ROUTE_VIEW_PROD
//import net.ezra.ui.products.addProductToFirestore

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import net.ezra.navigation.ROUTE_DASHBOARD

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Setting_Pg(navController: NavController) {
    val darkModeEnabled = remember { mutableStateOf(false) }
    val zoomLevel = remember { mutableStateOf(100) }
    val selectedLanguage = remember { mutableStateOf("English") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "SETTINGS", fontSize = 30.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_DASHBOARD)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Implement your Zoom, Dark Mode, Print, and Language buttons here
                    IconButton(onClick = {
                        // Handle zoom action
                    }) {
                        // Icon for Zoom
                    }
                    IconButton(onClick = {
                        darkModeEnabled.value = !darkModeEnabled.value
                        // Handle dark mode toggle
                    }) {
                        // Icon for Dark Mode
                    }
                    IconButton(onClick = {
                        // Handle print action
                    }) {
                        // Icon for Print
                    }
                    IconButton(onClick = {
                        // Handle language selection
                        // You can open a language selection dialog or navigate to a language settings page
                    }) {
                        // Icon for Language
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF015430),
                    titleContentColor = Color.White,
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF000000))
            ) {
                item {
                    // Example item
                    Text(
                        text = "Zoom Level: ${zoomLevel.value}%",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
                item {
                    // Example item
                    Text(
                        text = "Dark Mode: ${if (darkModeEnabled.value) "Enabled" else "Disabled"}",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
                // Add more items for other settings
            }
        }
    )
}
