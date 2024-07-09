package net.ezra.ui.products

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.ezra.R

class ProductViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    val productState = MutableLiveData<Product>()

    fun getProduct(productId: String) {
        viewModelScope.launch {
            val document = firestore.collection("products").document(productId).get().await()
            val product = document.toObject(Product::class.java) ?: return@launch
            productState.value = product
        }
    }

    fun updateProduct(product: Product) {
        firestore.collection("products").document(product.id).set(product)
    }
}

@Composable
fun UpdateProductScreen(navController: NavController, productId: String, productViewModel: ProductViewModel) {
    var updatedProductName by remember { mutableStateOf("") }
    var updatedProductDescription by remember { mutableStateOf("") }
    var updatedProductImageUri by remember { mutableStateOf<Uri?>(null) }

    // Observe product state
    val productState by productViewModel.productState.observeAsState()

    // Initialize product with observed state or default values
    val product = productState ?: Product()

    // Populate fields with product data
    updatedProductName = product.name
    updatedProductDescription = product.description
    updatedProductImageUri = Uri.parse(product.imageUrl) // Convert imageUrl to Uri

    // Firebase storage instance
    val storage = FirebaseStorage.getInstance()

    // Function to upload image to Firebase Storage
    fun uploadImageToStorage(productId: String, imageUri: Uri?, onSuccess: (String) -> Unit) {
        if (imageUri != null) {
            val storageRef = storage.reference.child("product_images").child("$productId.jpg")
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
        }
    }

    // Activity result launcher for getting image from gallery
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        updatedProductImageUri = uri
    }

    // Composable content
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Edit Product", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        // Display image preview if image URI is not null
        updatedProductImageUri?.let { uri ->
            Image(
                painter = painterResource(id = R.drawable.logo), // Placeholder image
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Product name text field
        TextField(
            value = updatedProductName,
            onValueChange = { updatedProductName = it },
            label = { Text(updatedProductName.takeUnless { it.isBlank() } ?: "Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Product description text field
        TextField(
            value = updatedProductDescription,
            onValueChange = { updatedProductDescription = it },
            label = { Text(updatedProductDescription.takeUnless { it.isBlank() } ?: "Product Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Button to select image from gallery
        Button(onClick = {
            getContent.launch("image/*")
        }) {
            Text("Select Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Button to save changes
        Button(onClick = {
            val updatedProduct = Product(
                id = productId,
                name = updatedProductName,
                description = updatedProductDescription,
                imageUrl = product.imageUrl, // Preserve original imageUrl
                videoUrl = product.videoUrl // Preserve original videoUrl
            )

            // Upload new image if updatedProductImageUri is not null
            if (updatedProductImageUri != null) {
                uploadImageToStorage(productId, updatedProductImageUri) { imageUrl ->
                    updatedProduct.imageUrl = imageUrl
                    productViewModel.updateProduct(updatedProduct)
                    navController.popBackStack()
                }
            } else {
                // Update product without uploading image
                productViewModel.updateProduct(updatedProduct)
                navController.popBackStack()
            }
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Save")
        }
    }
}



//package net.ezra.ui.products
//
//
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.FirebaseStorage
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import net.ezra.R
//
//class ProductViewModel : ViewModel() {
//    private val firestore = FirebaseFirestore.getInstance()
//
//    val productState = MutableLiveData<Product>()
//
//    fun getProduct(productId: String) {
//        viewModelScope.launch {
//            val document = firestore.collection("products").document(productId).get().await()
//            val product = document.toObject(Product::class.java) ?: return@launch
//            productState.value = product
//        }
//    }
//
//    fun updateProduct(product: Product) {
//        firestore.collection("products").document(product.id).set(product)
//    }
//}
//
//@Composable
//fun UpdateProductScreen(navController: NavController, productId: String, productViewModel: ProductViewModel) {
//
//    var updatedProductName by remember { mutableStateOf("") }
//    var updatedProductDescription by remember { mutableStateOf("") }
//    var updatedProductPrice by remember { mutableStateOf("") }
//    var updatedProductImageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val productState by productViewModel.productState.observeAsState()
//
//    val product = productState ?: Product()
//
//    updatedProductName = product.name
//    updatedProductDescription = product.description
//    updatedProductPrice = product.price.toString()
//    updatedProductImageUri = Uri.parse(product.imageUrl)
//
//    val storage = FirebaseStorage.getInstance()
//    fun uploadImageToStorage(productId: String, imageUri: Uri?, onSuccess: (String) -> Unit) {
//        if (imageUri != null) {
//            val storageRef = storage.reference.child("product_images").child("$productId.jpg")
//            val uploadTask = storageRef.putFile(imageUri)
//            uploadTask.addOnSuccessListener { taskSnapshot ->
//                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                    onSuccess(uri.toString())
//                }
//            }
//        }
//    }
//
//    val context = LocalContext.current
//    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        updatedProductImageUri = uri
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Edit Product", style = MaterialTheme.typography.h6)
//        Spacer(modifier = Modifier.height(16.dp))
//        updatedProductImageUri?.let { uri ->
//            Image(
//                painter = painterResource(id = R.drawable.logo), // Placeholder image
//                contentDescription = null,
//                modifier = Modifier.size(120.dp)
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = updatedProductName,
//            onValueChange = { updatedProductName = it },
//            label = { Text(updatedProductName.takeUnless { it.isBlank() } ?: "Product Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = updatedProductDescription,
//            onValueChange = { updatedProductDescription = it },
//            label = { Text(updatedProductDescription.takeUnless { it.isBlank() } ?: "Product Description") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = updatedProductPrice,
//            onValueChange = { updatedProductPrice = it },
//            label = { Text(updatedProductPrice.takeUnless { it.isBlank() } ?: "Product Price") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(onClick = {
//            getContent.launch("image/*")
//        }) {
//            Text("Select Image")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            val updatedProduct = Product(
//                id = productId,
//                name = updatedProductName,
//                description = updatedProductDescription,
//                price = updatedProductPrice.toDouble(),
//                imageUrl = ""
//            )
//
//            if (updatedProductImageUri != null) {
//                uploadImageToStorage(productId, updatedProductImageUri) { imageUrl ->
//                    updatedProduct.imageUrl = imageUrl
//                    productViewModel.updateProduct(updatedProduct)
//                    navController.popBackStack()
//                }
//            } else {
//                productViewModel.updateProduct(updatedProduct)
//                navController.popBackStack()
//            }
//        }, modifier = Modifier.align(Alignment.End)) {
//            Text("Save")
//        }
//    }
//}
