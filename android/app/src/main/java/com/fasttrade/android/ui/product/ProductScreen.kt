package com.fasttrade.android.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(id: Long, nav: NavController, vm: ProductViewModel = hiltViewModel()) {
    LaunchedEffect(id) { vm.load(id) }
    val product by vm.product.collectAsState()
    val loading by vm.loading.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(product?.title ?: "Produto") },
            navigationIcon = {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }) { padding ->
        if (loading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else product?.let { p ->
            Column(Modifier.padding(padding).padding(16.dp)) {
                AssistChip(onClick = {}, label = { Text(p.kind) })
                Spacer(Modifier.height(8.dp))
                Text(p.title, style = MaterialTheme.typography.headlineSmall)
                p.description?.let { Text(it, Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant) }
                Spacer(Modifier.height(16.dp))
                Text("R$ ${"%.2f".format(p.price)}", style = MaterialTheme.typography.titleLarge,
                     color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { /* TODO: adicionar ao carrinho */ }, Modifier.fillMaxWidth()) {
                    Text("Adicionar ao carrinho")
                }
            }
        }
    }
}
