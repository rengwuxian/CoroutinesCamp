package com.hencoder.coroutinescamp

import com.hencoder.coroutinescamp.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
  @GET("users/{user}/repos")
  fun listRepos(@Path("user") user: String): Response<List<Repo>>
}