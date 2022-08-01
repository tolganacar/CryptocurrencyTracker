package com.tolganacar.kotlincryptotracker.service

import com.tolganacar.kotlincryptotracker.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Observable<List<CryptoModel>>
}