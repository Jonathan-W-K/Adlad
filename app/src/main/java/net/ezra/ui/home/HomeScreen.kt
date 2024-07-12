package net.ezra.ui.home






import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import net.ezra.R
import net.ezra.navigation.ROUTE_ABOUT
import net.ezra.navigation.ROUTE_ADD_PRODUCT
import net.ezra.navigation.ROUTE_ADD_STUDENTS
import net.ezra.navigation.ROUTE_DASHBOARD
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_SEARCH
import net.ezra.navigation.ROUTE_SETTING_PG
import net.ezra.navigation.ROUTE_VIEW_PROD
import net.ezra.navigation.ROUTE_VIEW_STUDENTS


data class Screen(val title: String, val icon: Int)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    var isDrawerOpen by remember { mutableStateOf(false) }

    val callLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { _ ->

        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.apen))
                },

                actions = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF000000),
                    titleContentColor = Color.White,

                )

            )
        },

        content = @Composable {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (isDrawerOpen) {
                            isDrawerOpen = false
                        }
                    }
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xF704E9FE)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.call),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W300,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {

                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:+254725368376")

                                callLauncher.launch(intent)
                            }
                    )

                    Text(
                        text = stringResource(id = R.string.developer),
                        fontSize = 15.sp,
                    )

                    Spacer(modifier = Modifier.height(15.dp))
Row {
    Column {
        Row{
        Text(
            modifier = Modifier

                .clickable {
                    navController.navigate(ROUTE_SETTING_PG) {
                        popUpTo(ROUTE_HOME) { inclusive = true }
                    }
                },
            text = "Settings",
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            )
    }}
    Spacer(modifier = Modifier.width(15.dp))
    Column {
        Text(
            modifier = Modifier

                .clickable {
                    navController.navigate(ROUTE_ADD_PRODUCT) {
                        popUpTo(ROUTE_HOME) { inclusive = true }
                    }
                },
            
            text = "Create",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
                    Spacer(modifier = Modifier.height(25.dp))
                    Row {
 Column {
     Text(
         modifier = Modifier

             .clickable {
                 navController.navigate(ROUTE_ADD_STUDENTS) {
                     popUpTo(ROUTE_HOME) { inclusive = true }
                 }
             },
         text = "Register",
         textAlign = TextAlign.Left,
         fontSize = 20.sp,
         fontWeight = FontWeight.Bold,
         color = MaterialTheme.colorScheme.onSurface
     )
 }
                        Spacer(modifier = Modifier.width(15.dp))
                        Column {
                            Text(
                                modifier = Modifier

                                    .clickable {
                                        navController.navigate(ROUTE_VIEW_PROD) {
                                            popUpTo(ROUTE_HOME) { inclusive = true }
                                        }
                                    },
                                text = "View 'em",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    // Text(
                    //     text = "",
                    //     fontSize = 30.sp,
                    //     color = Color.White
                    // )



                }

            }

        },

        bottomBar = { BottomBar(navController = navController) }







    )}

//     AnimatedDrawer(
//         isOpen = isDrawerOpen,
//         onClose = { isDrawerOpen = false }
//     )
// }

// @Composable
// fun AnimatedDrawer(isOpen: Boolean, onClose: () -> Unit) {
//     val drawerWidth = remember { Animatable(if (isOpen) 250f else 0f) }

//     LaunchedEffect(isOpen) {
//         drawerWidth.animateTo(if (isOpen) 250f else 0f, animationSpec = tween(durationMillis = 300))
//     }

//     Surface(
//         modifier = Modifier
//             .fillMaxHeight()
//             .width(drawerWidth.value.dp)
//             ,
//         color = Color.LightGray,
// //        elevation = 16.dp
//     ) {
//         Column {
//             Text(
//                 text = "Drawer Item 1"

//             )
//             Text(
//                 text = "Drawer Item 2"
//             )
//             Text(
//                 text = "Drawer Item 3",
//                 modifier = Modifier.clickable {  }
//             )
//             Spacer(modifier = Modifier.height(16.dp))
//             Text(text = stringResource(id = R.string.developer))

//         }
//     }
// }






@Composable
fun BottomBar(navController: NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(
        elevation = 10.dp,
        backgroundColor = Color(0xff000000)


    ) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Home,"", tint = Color.White)
        },
            label = { Text(text = "Dash",color =  Color.White) }, selected = (selectedIndex.value == 0), onClick = { navController.navigate(ROUTE_DASHBOARD)

            })

//        BottomNavigationItem(icon = {
//            Icon(imageVector = Icons.Default.Favorite,"",tint = Color.White)
//        },
//            label = { Text(text = "Favorite",color =  Color.White) }, selected = (selectedIndex.value == 1), onClick = {
//
//            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person, "",tint = Color.White)
        },
            label = { Text(
                text = "Present",
                color =  Color(0xFFA7A4A4)
            ) },
            selected = (selectedIndex.value == 2),
            onClick = {

                navController.navigate(ROUTE_SEARCH) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }

            })

    }
}
