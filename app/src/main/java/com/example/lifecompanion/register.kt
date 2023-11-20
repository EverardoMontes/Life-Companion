package com.example.lifecompanion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

//import okhttp3.RequestBody.Companion.toRequestBody


class register : AppCompatActivity() {

    data class records(
        val id:String,
        val nombre:String,
        val usuario:String,
        val contrasena:String
    )

    data class Resultados(
        val records:List<records>
    )

    interface usuariosDBService{
        @GET("usuarioEspecificoLife.php")
        fun findUser(@Query("user") user: String): Call<Resultados>
    }

    object UsuariosDBClient {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://everardomontes.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val servicios=retrofit.create(usuariosDBService::class.java)

    }

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

            CoroutineScope(Dispatchers.IO).launch {
                val consulta = register.UsuariosDBClient.servicios.findUser(user.text.toString())
                try {
                    val body = consulta.execute().body()

                    if (body != null) {
                        runOnUiThread {
                            if (body.records.size != 0) {
                                Toast.makeText(this@register,"YA EXISTE UN USUARIO CON ESE NOMBRE",Toast.LENGTH_LONG).show()


                            } else {
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
                }catch(e: IOException)
                {

                }
            }


        }
    }

}


