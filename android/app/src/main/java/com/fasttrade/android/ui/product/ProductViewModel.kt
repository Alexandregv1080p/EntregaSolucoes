package com.fasttrade.android.ui.product

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
class ProductViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    fun load(id: Long) = viewModelScope.launch {
        _loading.value = true
        runCatching { api.product(id) }.onSuccess { _product.value = it }
        _loading.value = false
    }
}
