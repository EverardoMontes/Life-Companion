package com.example.lifecompanion

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConsumirApiRegister {
    @GET("agregarUsersLife.php")
    fun agregarUsersLife(@Query("user") user: String, @Query("nombre") nombre: String, @Query("apellido") apellido: String, @Query("edad") edad: String, @Query("email") email: String, @Query("password") password: String): Call<Usuario>
}