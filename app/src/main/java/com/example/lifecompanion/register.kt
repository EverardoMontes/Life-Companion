package com.example.lifecompanion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlinx.coroutines.runBlocking

//import okhttp3.RequestBody.Companion.toRequestBody


class register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val back = findViewById<Button>(R.id.buttonBack)
        val user = findViewById<EditText>(R.id.editTextUsername)
        val nombre = findViewById<EditText>(R.id.editTextName)
        val apellido = findViewById<EditText>(R.id.editTextSurname)
        val edad = findViewById<EditText>(R.id.editTextAge)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        back.setOnClickListener {
            finish()
        }
        val registrar = findViewById<Button>(R.id.buttonSend)

        registrar.setOnClickListener {
            val registro  = RetrofitClient.consumirApi.agregarUsersLife(user.text.toString(),nombre.text.toString(),
                apellido.text.toString(),edad.text.toString(),email.text.toString(),password.text.toString())
            registro.enqueue(object: Callback<Usuario>{
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    Toast.makeText(this@register,"Registro establecido",Toast.LENGTH_LONG).show()
                    finish()
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@register,"Registro fallido, algo salió mal",Toast.LENGTH_LONG).show()
                }

            })
        }
    }

}


