package com.example.app_convert_moeda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_convert_moeda.data.model.CurrencyResponse
import com.example.app_convert_moeda.data.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel (private val repository: CurrencyRepository): ViewModel() {
    fun getCurrencies(currencies: String, onResult: (CurrencyResponse) -> Unit){
        viewModelScope.launch{
            val result = withContext(Dispatchers.IO){
                repository.getCurrencies(currencies)
            }
            onResult(result)
        }
    }
}