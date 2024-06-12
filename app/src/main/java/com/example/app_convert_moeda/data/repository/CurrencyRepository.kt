package com.example.app_convert_moeda.data.repository

import com.example.app_convert_moeda.network.CurrencyApiService
import com.example.app_convert_moeda.network.RetrofitInstance

class CurrencyRepository {
    private val apiService: CurrencyApiService = RetrofitInstance.api
    suspend fun getCurrencies(currencies: String) = apiService.getCurrencies(currencies)
}