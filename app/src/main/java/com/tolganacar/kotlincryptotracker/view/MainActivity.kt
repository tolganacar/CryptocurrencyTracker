package com.tolganacar.kotlincryptotracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tolganacar.kotlincryptotracker.adapter.CryptoAdapter
import com.tolganacar.kotlincryptotracker.databinding.ActivityMainBinding
import com.tolganacar.kotlincryptotracker.model.CryptoModel
import com.tolganacar.kotlincryptotracker.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private lateinit var cryptoAdapter: CryptoAdapter
    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

    }

    private fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            cryptoAdapter = CryptoAdapter(it)
            binding.recyclerView.adapter = cryptoAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}