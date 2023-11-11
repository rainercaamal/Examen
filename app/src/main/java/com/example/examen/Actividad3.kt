package com.example.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class Actividad3 : AppCompatActivity()
{
    private lateinit var busqueda: EditText
    private lateinit var btnGuardar: Button
    private lateinit var autores: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad3)

        // Inicializar las vistas
        busqueda = findViewById(R.id.Busqueda)
        btnGuardar = findViewById(R.id.BtnGuardar)
        autores = findViewById(R.id.Autores)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://50c0-2806-342-fdca-4658-286b-2f7-67ae-2549.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia de la interfaz Retrofit
        val blogApiService = retrofit.create(BlogSearchApiService::class.java)

        btnGuardar.setOnClickListener {
            realizarBusqueda(blogApiService)
        }
    }

    private fun realizarBusqueda(blogApiService: BlogSearchApiService) {
        val searchTerm = busqueda.text.toString()

        // Realizar la llamada a la API para buscar
        val call = blogApiService.searchBlogEntries(searchTerm)
        call.enqueue(object : Callback<List<BlogEntry>> {
            override fun onResponse(
                call: Call<List<BlogEntry>>,
                response: Response<List<BlogEntry>>
            ) {
                if (response.isSuccessful) {
                    // Obtener los resultados de la búsqueda
                    val resultadosBusqueda = response.body()

                    // Mostrar los resultados en el ListView
                    if (resultadosBusqueda != null) {
                        val adapter = ArrayAdapter(
                            this@Actividad3,
                            android.R.layout.simple_list_item_1,
                            resultadosBusqueda.map { entry -> entry.autor }
                        )
                        autores.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<BlogEntry>>, t: Throwable) {
                // Manejar el fallo de la llamada
            }
        })
    }
}