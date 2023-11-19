package com.example.lifecompanion

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class DiarioAgenda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario_agenda)
        val et2=findViewById<EditText>(R.id.et2)
        val elegirEmocion=findViewById<ImageButton>(R.id.elegirEmocion)
        val volver=findViewById<ImageButton>(R.id.volverACalendario)

        val idUsuario = intent.getStringExtra("id")
        var fechaAEnviar =intent.getStringExtra("fecha")
        elegirEmocion.setOnClickListener {
            //val nomarchivo = et1.text.toString().replace('/','-')
            try {

                /*ESTO FUNCIONA PERO SOLO FUNCIONA EN CONJUNTO CON ARCHIVOS LOCALES DE ABAJO*/
                /*val archivo = OutputStreamWriter(openFileOutput(nomarchivo, Activity.MODE_PRIVATE))
                archivo.write(et2.text.toString())
                archivo.flush()
                archivo.close()*/
            } catch (e: IOException) {
            }
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