package net.ezra.ui.products

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import net.ezra.navigation.ROUTE_VIEW_PROD

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: String) {
    // MutableState to hold the product details
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        fetchProduct(productId) { fetchedProduct ->
            product = fetchedProduct
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // Display the product name if available
                    Text(
                        text = product?.name ?: "Details",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_VIEW_PROD)
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
                    .background(Color(0xFF6D95DE)),
            ) {
                product?.let {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(it.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = it.name, style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Description: ${it.description}", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    )
}

suspend fun fetchProduct(productId: String, param: (Product?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val productsCollection = db.collection("products")

    try {
        val documentSnapshot = productsCollection.document(productId).get().await()
        if (documentSnapshot.exists()) {
            val productData = documentSnapshot.data
            val product = Product(
                id = productId,
                name = productData?.get("name") as? String ?: "",
                description = productData?.get("description") as? String ?: "",
                imageUrl = productData?.get("imageUrl") as? String ?: ""
            )
            param(product)
        } else {
            param(null)
        }
    } catch (e: Exception) {
        param(null)
    }
}



//package net.ezra.ui.products
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
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
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.rememberAsyncImagePainter
//import net.ezra.navigation.ROUTE_VIEW_PROD
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//
//
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductDetailScreen(navController: NavController, productId: String) {
//
//    var product by remember { mutableStateOf<Product?>(null) }
//
//    LaunchedEffect(Unit) {
//        fetchProduct(productId) { fetchedProduct ->
//            product = fetchedProduct
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    // Display the product name if available
//                    Text(
//                        text = product?.name ?: "Details",
//                        fontSize = 30.sp,
//                        color = Color.White
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.navigate(ROUTE_VIEW_PROD)
//                    }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            "backIcon",
//                            tint = Color.White
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
//                    .background(Color(0xFF6D95DE)),
//            ) {
//                product?.let {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Image(
//                            painter = rememberAsyncImagePainter(it.imageUrl),
//                            contentDescription = null,
//                            modifier = Modifier.size(60.dp)
//                        )
//                        Text(text = it.name, style = MaterialTheme.typography.h5)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(text = "Price: ${it.price}", style = MaterialTheme.typography.subtitle1)
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(text = it.description, style = MaterialTheme.typography.body1)
//                    }
//                }
//            }
//        }
//    )
//}
//
//
//suspend fun fetchProduct(productId: String, param: (Any) -> Unit): Product? {
//    val db = FirebaseFirestore.getInstance()
//    val productsCollection = db.collection("products")
//
//    return try {
//        val documentSnapshot = productsCollection.document(productId).get().await()
//        if (documentSnapshot.exists()) {
//            val productData = documentSnapshot.data ?: return null
//            Product(
//                id = productId,
//                name = productData["name"] as String,
//                // Add other product properties here
//            )
//        } else {
//            null
//        }
//    } catch (e: Exception) {
//        null
//    }
//}
