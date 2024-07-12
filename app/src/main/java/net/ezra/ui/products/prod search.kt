package net.ezra.ui.products

//import android.annotation.SuppressLint
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import coil.compose.SubcomposeAsyncImage
//import coil.request.ImageRequest
//import com.google.firebase.firestore.firestore
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import com.google.firebase.Firebase
//import net.ezra.navigation.ROUTE_HOME
//import net.ezra.navigation.ROUTE_PRODUCT_SEARCH
//
//data class Product(
//    var id: String = "",
//    val name: String = "",
//    val description: String = "",
//    var imageUrl: String = ""
//)
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductSearch(navController: NavHostController) {
//    var searchText by remember { mutableStateOf(TextFieldValue()) }
//    var filteredData by remember { mutableStateOf(emptyList<Product>()) }
//
//    // Firestore reference
//    val firestore = Firebase.firestore
//
//    DisposableEffect(searchText.text) {
//        val query = firestore.collection("products")
//            .whereGreaterThanOrEqualTo("name", searchText.text)
//            .whereLessThanOrEqualTo("name", searchText.text + "\uf8ff")
//
//        val listener = query.addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                // Handle error
//                return@addSnapshotListener
//            }
//
//            snapshot?.let {
//                val data = it.toObjects(Product::class.java)
//                filteredData = data
//            }
//        }
//
//        onDispose {
//            listener.remove()
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(text = "Product Search", fontSize = 20.sp, color = Color.White)
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.navigate(ROUTE_HOME) {
//                            popUpTo(ROUTE_PRODUCT_SEARCH) { inclusive = true }
//                        }
//                    }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "backIcon",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF000000),
//                    titleContentColor = Color.White
//                )
//            )
//        },
//        content = {
//            Column(
//                modifier = Modifier
//                    .background(Color(0xF7537BC1))
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                TextField(
//                    value = searchText,
//                    onValueChange = { searchText = it },
//                    placeholder = { Text("Search?") },
//                    modifier = Modifier.fillMaxWidth(),
//                    trailingIcon = {
//                        Icon(Icons.Default.Search, contentDescription = "searchIcon")
//                    }
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                    items(filteredData) { product ->
//                        ProductListItem(product)
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun ProductListItem(product: Product) {
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(Color.White)
//            .border(1.dp, Color.Black)
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        SubcomposeAsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(product.imageUrl)
//                .crossfade(true)
//                .build(),
//            loading = {
//                CircularProgressIndicator()
//            },
//            contentDescription = product.name,
//            modifier = Modifier
//                .clip(RoundedCornerShape(10))
//                .size(150.dp)
//        )
//
//        Text(text = product.name, fontWeight = FontWeight.Bold)
//        Text(text = product.description)
//    }
//}
