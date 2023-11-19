package com.example.lifecompanion

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.Toast

class calendario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val calendar = Calendar.getInstance()
        val volver = findViewById<ImageButton>(R.id.botonVolver)
        val escribir = findViewById<ImageButton>(R.id.botonAvanzar)
        var fechaSeleccion=""
        val idUsuario = intent.getStringExtra("id")
        var intentoEntrada= Intent(this,DiarioAgenda::class.java)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            /*Toast.makeText(
                this,
                "${dayOfMonth}/${month}/${year} es la fecha que seleccionaste. Estás menso o que?",
                Toast.LENGTH_LONG
            ).show();*/
            fechaSeleccion="${year}-${month}-${dayOfMonth}"
        }

        volver.setOnClickListener {
            finish()
        }

        escribir.setOnClickListener {
            intentoEntrada.putExtra("fecha", fechaSeleccion)
            intentoEntrada.putExtra("id", idUsuario)
            startActivity(intentoEntrada)
        }
    }
}