package com.hencoder.coroutinescamp.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hencoder.coroutinescamp.Api
import com.hencoder.coroutinescamp.model.Repo
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RengViewModel : ViewModel() {
  val repos = liveData {
    emit(loadUsers())
  }

  private suspend fun loadUsers(): List<Repo> {
    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .build()
    val api = retrofit.create(Api::class.java)
    return api.listReposKt("rengwuxian")
  }
}