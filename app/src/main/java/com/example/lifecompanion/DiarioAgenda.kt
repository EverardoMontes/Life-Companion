package com.example.lifecompanion

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class DiarioAgenda : AppCompatActivity() {

    data class entrada(
        val id:String,
        val user:String,
        val entry:String,
        val feel:String,
        var fecha:String
    )

    data class Resultados(
        val records:List<entrada>
    )

    interface usuariosDBService{
        @GET("getEntradasLifeCompanion.php")
        fun getEntry(@Query("user") user: String, @Query("fecha") fecha: String): Call<Resultados>

    }

    interface usuariosDBServiceSEND{
        @GET("agregarEntradasLife.php")
        fun sendEntry(@Query("user") user: String, @Query("entry") entry: String, @Query("feel") feel: String, @Query("fecha") fecha: String): Call<Resultados>
    }

    interface usuariosDBServiceUPDATE{
        @GET("agregarEntradasLife.php")
        fun sendEntry(@Query("user") user: String, @Query("entry") entry: String, @Query("feel") feel: String, @Query("fecha") fecha: String): Call<Resultados>
    }

    object UsuariosDBClient {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://everardomontes.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service=retrofit.create(usuariosDBService::class.java)
        val send=retrofit.create(usuariosDBServiceSEND::class.java)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario_agenda)
        val et2=findViewById<EditText>(R.id.et2)
        val elegirEmocion=findViewById<ImageButton>(R.id.elegirEmocion)
        val volver=findViewById<ImageButton>(R.id.volverACalendario)
        val sentimiento = findViewById<EditText>(R.id.textoSentimiento)
        val idUsuario = intent.getStringExtra("id")
        var fechaAEnviar =intent.getStringExtra("fecha")
        var entradaExistente = false

        // ESTO BUSCA ALGUNA ENTRADA QUE YA EXISTA EN LA BASE DE DATOS
        CoroutineScope(Dispatchers.IO).launch {
            val consulta = DiarioAgenda.UsuariosDBClient.service.getEntry(idUsuario.toString(), fechaAEnviar.toString())
            try {
                val body = consulta.execute().body()

                if (body != null) {
                    runOnUiThread {
                        if (body.records.size != 0) {
                            val entrada = body.records.first().entry.toString()
                            val feel = body.records.first().feel.toString()
                            et2.setText(entrada)
                            sentimiento.setText(feel)
                            entradaExistente=true
                        } else {
                            et2.setText("")
                            sentimiento.setText("")
                        }
                    }
                }
            }catch(e: IOException)
            {

            }
        }


        elegirEmocion.setOnClickListener {
            //val nomarchivo = et1.text.toString().replace('/','-')
            CoroutineScope(Dispatchers.IO).launch {
                if(entradaExistente==true){

                }else{
                    val consulta = DiarioAgenda.UsuariosDBClient.send.sendEntry(idUsuario.toString(),et2.text.toString(),sentimiento.text.toString(),fechaAEnviar.toString())
                    try {
                        val body = consulta.execute().body()

                        if (body != null) {
                            runOnUiThread {
                                if (body.records.size != 0) {
                                    finish()
                                } else {

                                }
                            }
                        }
                    }catch(e: IOException)
                    {

                    }
                }

            }
        }

        volver.setOnClickListener {
            finish()
        }
        /*et1.doAfterTextChanged {
            var nomarchivo = et1.text.toString().replace('/', '-')
        //ESTO CLAARO QUE FUNCIONA, PERO ES PARA LEER POR ARCHIVOS LOCALES   --------------
        *//*var nomarchivo = et1.text.toString().replace('/', '-')
            if (fileList().contains(nomarchivo)) {
                try {
                    val archivo = InputStreamReader(openFileInput(nomarchivo))
                    val br = BufferedReader(archivo)
                    var linea = br.readLine()
                    val todo = StringBuilder()
                    while (linea != null) {
                        todo.append(linea + "\n")
                        linea = br.readLine()
                    }
                    br.close()
                    archivo.close()
                    et2.setText(todo)
                } catch (e: IOException) {
                }
            } else {
                et2.setText("")
            }*//*
            // AQUI TERMINA ARCHIVOS LOCALES ----------------

        }*/
    }
   /* private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment {day, month, year -> onDaySelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDaySelected(day:Int, month:Int, year:Int){
        val et1 = findViewById<EditText>(R.id.et1)
        et1.setText("${day}/${month+1}/${year}")

    }*/
}