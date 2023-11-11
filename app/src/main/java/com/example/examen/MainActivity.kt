package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private lateinit var txtTitulo: EditText
    private lateinit var txtAutor: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtContenido: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtTitulo = findViewById(R.id.TxtTitulo)
        txtAutor = findViewById(R.id.TxtAutor)
        txtFecha = findViewById(R.id.TxtFecha)
        txtContenido = findViewById(R.id.TxtContenido)

        val btnAceptar: Button = findViewById(R.id.BtnAceptar)
        btnAceptar.setOnClickListener {
            enviarDatosAlServidor()
        }
        val btnAbrirActivity2: Button = findViewById(R.id.btnAbrirActivity2)
        btnAbrirActivity2.setOnClickListener {
            abrirMainActivity2();
        }

        val btnAbrirActivity3: Button = findViewById(R.id.btnAbrirActivity3  )
        btnAbrirActivity3.setOnClickListener {
            abrirMainActivity3();
        }
    }

    private fun enviarDatosAlServidor() {
        val titulo = txtTitulo.text.toString()
        val autor = txtAutor.text.toString()
        val fecha = txtFecha.text.toString()
        val contenido = txtContenido.text.toString()

        val blogEntry = BlogEntryy(titulo, autor, fecha, contenido)

        val call: Call<Void> = ApiClient.blogEntryController.createBlogEntry(blogEntry)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Éxito, los datos se enviaron correctamente
                    mostrarMensaje("Valores enviados")
                    limpiarEditText()
                } else {
                    // Algo salió mal en el servidor
                    mostrarMensaje("Error en el servidor")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Error de red o problema en la llamada
                mostrarMensaje("Error de red: ${t.message}")
            }
        })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarEditText() {
        txtTitulo.text.clear()
        txtAutor.text.clear()
        txtFecha.text.clear()
        txtContenido.text.clear()
    }

    object ApiClient {
        private const val BASE_URL = "https://50c0-2806-342-fdca-4658-286b-2f7-67ae-2549.ngrok-free.app"

        val blogEntryController: BlogEntryController by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlogEntryController::class.java)
        }
    }

    // Función para abrir MainActivity2
    private fun abrirMainActivity2() {
        val intent = Intent(this, ListaEmpty::class.java)
        startActivity(intent)
    }
    // Función para abrir MainActivity3
    private fun abrirMainActivity3() {
        val intent = Intent(this, Actividad3::class.java)
        startActivity(intent)
    }

}
