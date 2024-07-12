package net.ezra.ui.products

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_VIEW_PROD
import net.ezra.R

data class Product(
    var id: String = "",
    val name: String = "",
    val description: String = "",
    var imageUrl: String = "",
    var videoUrl: String = ""
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, products: List<Product>) {
    var isLoading by remember { mutableStateOf(true) }
    var productList by remember { mutableStateOf(emptyList<Product>()) }
    var displayedProductCount by remember { mutableStateOf(90) }
    var progress by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        fetchProducts { fetchedProducts ->
            productList = fetchedProducts
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Inventory", fontSize = 30.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_HOME)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF000000),
                    titleContentColor = Color.White,
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF769DDE))
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(progress = progress / 100f)
                        Text(text = "Processing... $progress%", fontSize = 20.sp)
                    }
                } else {
                    if (productList.isEmpty()) {
                        Text(text = "Nothing added yet")
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(productList.take(displayedProductCount)) { product ->
                                ProductListItem(product)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (displayedProductCount < productList.size) {
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0B3065)),
                                onClick = { displayedProductCount += 1 },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = "Look up")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ProductListItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (product.videoUrl.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Video/GIF Placeholder", modifier = Modifier.padding(8.dp))
                }
            } else {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name)
            Text(text = "Description: ${product.description}")
        }
    }
}

private suspend fun fetchProducts(onSuccess: (List<Product>) -> Unit) {
    val firestore = Firebase.firestore
    val snapshot = firestore.collection("products").get().await()
    val productList = snapshot.documents.mapNotNull { doc ->
        val product = doc.toObject<Product>()
        product?.id = doc.id
        product
    }
    onSuccess(productList)
}




// package net.ezra.ui.products

// import android.annotation.SuppressLint
// import androidx.compose.foundation.Image
// import androidx.compose.foundation.background
// import androidx.compose.foundation.clickable
// import androidx.compose.foundation.layout.*
// //import androidx.compose.foundation.lazy.GridCells
// //import androidx.compose.foundation.lazy.LazyVerticalGrid
// import androidx.compose.foundation.lazy.grid.GridCells
// import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
// import androidx.compose.foundation.lazy.grid.items
// import androidx.compose.foundation.lazy.items
// import androidx.compose.material.Button
// import androidx.compose.material.ButtonDefaults
// import androidx.compose.material.Card
// import androidx.compose.material.CircularProgressIndicator
// import androidx.compose.material.MaterialTheme
// import androidx.compose.material.Scaffold
// import androidx.compose.material.Text
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.automirrored.filled.ArrowBack
// import androidx.compose.material3.CenterAlignedTopAppBar
// import androidx.compose.material3.ExperimentalMaterial3Api
// import androidx.compose.material3.Icon
// import androidx.compose.material3.IconButton
// import androidx.compose.material3.TopAppBarDefaults
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.LaunchedEffect
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.unit.dp
// import androidx.compose.ui.unit.sp
// import androidx.navigation.NavController
// import coil.compose.rememberImagePainter
// import com.google.firebase.firestore.ktx.firestore
// import com.google.firebase.firestore.ktx.toObject
// import com.google.firebase.ktx.Firebase
// import kotlinx.coroutines.tasks.await
// import net.ezra.navigation.ROUTE_HOME
// import net.ezra.navigation.ROUTE_VIEW_PROD

// data class Product(
//     var id: String = "",
//     val name: String = "",
//     val description: String = "",
//     var imageUrl: String = "",
//     var videoUrl: String = ""
// )

// @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun ProductListScreen(navController: NavController, products: List<Product>) {
//     var isLoading by remember { mutableStateOf(true) }
//     var productList by remember { mutableStateOf(emptyList<Product>()) }
//     var displayedProductCount by remember { mutableStateOf(90) }
//     var progress by remember { mutableStateOf(0) }

//     LaunchedEffect(Unit) {
//         fetchProducts { fetchedProducts ->
//             productList = fetchedProducts
//             isLoading = false
//         }
//     }

//     Scaffold(
//         topBar = {
//             CenterAlignedTopAppBar(
//                 title = {
//                     Text(text = "Products", fontSize = 30.sp, color = Color.White)
//                 },
//                 navigationIcon = {
//                     IconButton(onClick = {
//                         navController.navigate(ROUTE_HOME)
//                     }) {
//                         Icon(
//                             Icons.AutoMirrored.Filled.ArrowBack,
//                             "backIcon",
//                             tint = Color.White
//                         )
//                     }
//                 },
//                 colors = TopAppBarDefaults.topAppBarColors(
//                     containerColor = Color(0xFF000000),
//                     titleContentColor = Color.White,
//                 )
//             )
//         },
//         content = {
//             Column(
//                 modifier = Modifier
//                     .fillMaxSize()
//                     .background(Color(0xFF769DDE))
//             ) {
//                 if (isLoading) {
//                     // Progress indicator
//                     Box(
//                         modifier = Modifier.fillMaxSize(),
//                         contentAlignment = Alignment.Center
//                     ) {
//                         CircularProgressIndicator(progress = progress / 100f)
//                         Text(text = "Processing... $progress%", fontSize = 20.sp)
//                     }
//                 } else {
//                     if (productList.isEmpty()) {
//                         // No products found
//                         Text(text = "Nothing added yet")
//                     } else {
//                         // Products list
//                         LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                             items(productList.take(displayedProductCount)) { product ->
//                                 ProductListItem(product) {
//                                     // Navigate to detailed view
//                                     navController.navigate("$ROUTE_VIEW_PROD/${product.id}")
//                                 }
//                             }
//                         }
//                         Spacer(modifier = Modifier.height(16.dp))
//                         // Load More Button
//                         if (displayedProductCount < productList.size) {
//                             Button(
//                                 colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//                                     0xFF0B3065
//                                 )
//                                 ),
//                                 onClick = { displayedProductCount += 1 },
//                                 modifier = Modifier.align(Alignment.CenterHorizontally)
//                             ) {
//                                 Text(text = "See More")
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//     )
// }

@Composable
fun ProductListItem(product: Product, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(product.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(){
                Column {
                Text(text = "Mpesa")
                Image(painter = painterResource(id = R.drawable.safad), contentDescription = "")
            }
                Column {
                    Text(text = "Vehicle")
                    Image(painter = painterResource(id = R.drawable.vehicle), contentDescription = "")
                }}
            // Product Image or Video
            if (product.videoUrl.isNotEmpty()) {
                // Display Video (You can implement video player or gif player)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Video/GIF Placeholder", modifier = Modifier.padding(8.dp))
                }
            } else {
                // Display Image
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Product Details
            Text(text = product.name)
            Text(text = "Description: ${product.description}")
        }
    }
}

private suspend fun fetchProducts(onSuccess: (List<Product>) -> Unit) {
    val firestore = Firebase.firestore
    val snapshot = firestore.collection("products").get().await()
    val productList = snapshot.documents.mapNotNull { doc ->
        val product = doc.toObject<Product>()
        product?.id = doc.id
        product
    }
    onSuccess(productList)
}


//package net.ezra.ui.products
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.Card
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Scaffold
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.firestore.ktx.toObject
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.tasks.await
//import net.ezra.navigation.ROUTE_HOME
//import net.ezra.navigation.ROUTE_VIEW_PROD
//
//data class Product(
//    var id: String = "",
//    val name: String = "",
//    val description: String ="",
//    var imageUrl: String = "",
//    var videoUrl: String = ""
//)
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductListScreen(navController: NavController, products: List<Product>) {
//    var isLoading by remember { mutableStateOf(true) }
//    var productList by remember { mutableStateOf(emptyList<Product>()) }
//    var displayedProductCount by remember { mutableStateOf(1) }
//    var progress by remember { mutableStateOf(0) }
//
//    LaunchedEffect(Unit) {
//        fetchProducts { fetchedProducts ->
//            productList = fetchedProducts
//            isLoading = false
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(text = "Products", fontSize = 30.sp, color = Color.White)
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.navigate(ROUTE_HOME)
//                    }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            "backIcon",
//                            tint = Color(0xFF00e5ff)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF000000),
//                    titleContentColor = Color.White,
//                )
//            )
//        },
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF769DDE))
//            ) {
//                if (isLoading) {
//                    // Progress indicator
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(progress = progress / 100f)
//                        Text(text = "Loading... $progress%", fontSize = 20.sp)
//                    }
//                } else {
//                    if (productList.isEmpty()) {
//                        // No products found
//                        Text(text = "No products found")
//                    } else {
//                        // Products list
//                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                            items(productList.take(displayedProductCount)) { product ->
//                                ProductListItem(product) {
//                                    navController.navigate("productDetail/${product.id}")
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                        // Load More Button
//                        if (displayedProductCount < productList.size) {
//                            Button(
//                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//                                    0xFF0B3065
//                                )
//                                ),
//                                onClick = { displayedProductCount += 1 },
//                                modifier = Modifier.align(Alignment.CenterHorizontally)
//                            ) {
//                                Text(text = "See More")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun ProductListItem(product: Product, onItemClick: (String) -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { onItemClick(product.id) }
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Product Image or Video
//            if (product.videoUrl.isNotEmpty()) {
//                // Display Video (You can implement video player or gif player)
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Video/GIF Placeholder", modifier = Modifier.padding(8.dp))
//                }
//            } else {
//                // Display Image
//                Image(
//                    painter = rememberImagePainter(product.imageUrl),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            // Product Details
//            Text(text = product.name)
//            Text(text = "Description: ${product.description}")
//        }
//    }
//}
//
//private suspend fun fetchProducts(onSuccess: (List<Product>) -> Unit) {
//    val firestore = Firebase.firestore
//    val snapshot = firestore.collection("products").get().await()
//    val productList = snapshot.documents.mapNotNull { doc ->
//        val product = doc.toObject<Product>()
//        product?.id = doc.id
//        product
//    }
//    onSuccess(productList)
//}


//package net.ezra.ui.products
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.Card
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Scaffold
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.firestore.ktx.toObject
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.tasks.await
//import net.ezra.navigation.ROUTE_HOME
//import net.ezra.navigation.ROUTE_VIEW_PROD
//import net.ezra.navigation.ROUTE_VIEW_STUDENTS
//import androidx.compose.material.LinearProgressIndicator
//
//data class Product(
//    var id: String = "",
//    val name: String = "",
//    val description: String ="",
//    val price: Double = 0.0,
//    var imageUrl: String = ""
//)
//
//
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductListScreen(navController: NavController, products: List<Product>) {
//    var isLoading by remember { mutableStateOf(true) }
//    var productList by remember { mutableStateOf(emptyList<Product>()) }
//    var displayedProductCount by remember { mutableStateOf(1) }
//    var progress by remember { mutableStateOf(0) }
//
//    LaunchedEffect(Unit) {
//        fetchProducts { fetchedProducts ->
//            productList = fetchedProducts
//            isLoading = false
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(text = "Products",fontSize = 30.sp, color = Color.White)
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.navigate(ROUTE_HOME)
//                    }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            "backIcon",
//                            tint = Color.White
//                        )
//                    }
//                },
//
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF000000),
//                    titleContentColor = Color.White,
//
//                    )
//
//            )
//        },
//        content = {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF769DDE))
//            ) {
//                if (isLoading) {
//                    // Progress indicator
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(progress = progress / 100f)
//                        Text(text = "Loading... $progress%", fontSize = 20.sp)
//                    }
//                } else {
//                    if (productList.isEmpty()) {
//                        // No products found
//                        Text(text = "No products found")
//                    } else {
//                        // Products list
//                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                            items(productList.take(displayedProductCount)) { product ->
//                                ProductListItem(product) {
//                                    navController.navigate("productDetail/${product.id}")
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                        // Load More Button
//                        if (displayedProductCount < productList.size) {
//                            Button(
//                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(
//                                    0xFF0B3065
//                                )
//                                ),
//                                onClick = { displayedProductCount += 1 },
//                                modifier = Modifier.align(Alignment.CenterHorizontally)
//                            ) {
//                                Text(text = "See More")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun ProductListItem(product: Product, onItemClick: (String) -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { onItemClick(product.id) }
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Product Image
//            Image(
//                painter = rememberImagePainter(product.imageUrl),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(60.dp)
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // Product Details
//            Column {
//                Text(text = product.name)
//                Text(text = "Price: ${product.price}")
//            }
//        }
//    }
//}
//
//private suspend fun fetchProducts(onSuccess: (List<Product>) -> Unit) {
//    val firestore = Firebase.firestore
//    val snapshot = firestore.collection("products").get().await()
//    val productList = snapshot.documents.mapNotNull { doc ->
//        val product = doc.toObject<Product>()
//        product?.id = doc.id
//        product
//    }
//    onSuccess(productList)
//}
//
//suspend fun fetchProduct(productId: String, onSuccess: (Product?) -> Unit) {
//    val firestore = Firebase.firestore
//    val docRef = firestore.collection("products").document(productId)
//    val snapshot = docRef.get().await()
//    val product = snapshot.toObject<Product>()
//    onSuccess(product)
//}
//
