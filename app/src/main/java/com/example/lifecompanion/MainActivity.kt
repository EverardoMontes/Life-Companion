package com.example.lifecompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class MainActivity : AppCompatActivity() {
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
        @GET("loginUsuariosLife.php")
        fun loginUsuario(@Query("user") user: String, @Query("password") password: String): Call<Resultados>
    }

    object UsuariosDBClient {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://everardomontes.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service=retrofit.create(usuariosDBService::class.java)

    }

    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonLogin = findViewById<Button>(R.id.buttonSignIn)
        val buttonRegister = findViewById<Button>(R.id.buttonSignUp)
        val user = findViewById<EditText>(R.id.UsuarioLogin)
        val pass = findViewById<EditText>(R.id.PassLogin)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        buttonRegister.setOnClickListener{
            val intento1 =  Intent(this, register::class.java)
            startActivity(intento1)
        }
        //Cargar los datos del usuario
        val preferencias = getSharedPreferences("datos", MODE_PRIVATE)
        user.setText(preferencias.getString("usuario", ""))

        pass.setText(preferencias.getString("contrasena", ""))

        //Si hay datos activar recordar

        if (preferencias.getString("usuario", "") != "")

            checkBox.isChecked = true
        buttonLogin.setOnClickListener(View.OnClickListener { view ->
            // Do some work here
            //  Toast.makeText(view.context,"No encontrado",Toast.LENGTH_LONG).show()
            val intento1 = Intent(this, DiarioAgenda::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val miusuario = UsuariosDBClient.service.loginUsuario(user.text.toString(), pass.text.toString())
                try {
                    val body = miusuario.execute().body()

                    if (body != null) {
                        runOnUiThread {
                            if (body.records.size != 0) {
                                Log.d("MainActivity", "Usuario:${body.records[0].nombre}")
                                obteneryguardarPropiedades()
                                intento1.putExtra("user",user.text.toString())
                                intento1.putExtra("pass",pass.text.toString())
                                intento1.putExtra("id",body.records.first().id.toString())
                                startActivity(intento1)
                            } else {
                                Log.d("MainActivity", "No Hay usuarios")
                                // Alerta("Usuarios","No esta registrado este usuario!")
                                Toast.makeText(
                                    view.context,
                                    "Usuario no encontrado!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }catch(e: IOException)
                {
                    runOnUiThread{
                    Toast.makeText(
                        view.context,
                        "Error al realizar la consulta!!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                }
            }
        })
    }

    fun obteneryguardarPropiedades()
    {
        val editText=findViewById<EditText>(R.id.UsuarioLogin)
        val editText2=findViewById<EditText>(R.id.PassLogin)
        val checkBox=findViewById<CheckBox>(R.id.checkBox)
        val preferencias = getSharedPreferences("datos", MODE_PRIVATE)
        val editor = preferencias.edit()

        if(checkBox.isChecked)

        {


            editor.putString("usuario", editText.text.toString())

            editor.putString("contrasena", editText2.text.toString())

            editor.commit()

        }else

        {

            val editor = preferencias.edit()

            editor.putString("usuario", "")

            editor.putString("contrasena", "")

            editor.commit()

        }
    }
}