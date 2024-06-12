package com.example.app_convert_moeda.network

import com.example.app_convert_moeda.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {
    @GET("json/last/{currencies}")
    suspend fun getCurrencies(@Path("currencies") currencies: String?): CurrencyResponse

}