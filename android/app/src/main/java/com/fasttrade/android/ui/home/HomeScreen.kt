package com.fasttrade.android.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fasttrade.android.network.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController, vm: HomeViewModel = hiltViewModel()) {
    val products by vm.products.collectAsState()
    val loading by vm.loading.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("EntregaSoluções") }) }) { padding ->
        if (loading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                items(products) { p -> ProductItem(p) { nav.navigate("product/${p.id}") } }
            }
        }
    }
}

@Composable
private fun ProductItem(p: Product, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable(onClick = onClick)) {
        Column(Modifier.padding(12.dp)) {
            Text(p.title, style = MaterialTheme.typography.titleMedium)
            Text("R$ ${"%.2f".format(p.price)}", color = MaterialTheme.colorScheme.primary)
        }
    }
}
