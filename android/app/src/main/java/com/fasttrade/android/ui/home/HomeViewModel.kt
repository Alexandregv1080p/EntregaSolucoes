package com.fasttrade.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasttrade.android.network.ApiService
import com.fasttrade.android.network.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init { load() }

    private fun load() = viewModelScope.launch {
        runCatching { api.products() }
            .onSuccess { _products.value = it }
        _loading.value = false
    }
}
