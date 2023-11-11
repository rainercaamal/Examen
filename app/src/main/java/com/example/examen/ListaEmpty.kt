package com.example.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaEmpty : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_empty)
        listView = findViewById(R.id.list)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://50c0-2806-342-fdca-4658-286b-2f7-67ae-2549.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia de la interfaz Retrofit
        val blogApiService = retrofit.create(BlogApiService::class.java)

        // Realizar la llamada a la API
        val call = blogApiService.getAllBlogEntries()
        call.enqueue(object : Callback<List<BlogEntry>> {
            override fun onResponse(
                call: Call<List<BlogEntry>>,
                response: Response<List<BlogEntry>>
            ) {
                if (response.isSuccessful) {
                    val blogEntries = response.body()
                    Log.d("API_RESPONSE", "Blog Entries: $blogEntries")

                    if (blogEntries != null) {
                        // Mostrar los datos en el ListView
                        val adapter = ArrayAdapter(
                            this@ListaEmpty,
                            android.R.layout.simple_list_item_1,
                            blogEntries.map { entry -> entry.autor }
                        )
                        listView.adapter = adapter
                    }
                } else {
                    Log.e("API_RESPONSE", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<BlogEntry>>, t: Throwable) {
                Log.e("API_RESPONSE", "Error en la llamada: ${t.message}")
            }
        })
    }
}