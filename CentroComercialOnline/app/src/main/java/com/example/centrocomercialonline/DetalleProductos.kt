package com.example.centrocomercialonline

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class DetalleProductos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_productos)

        val itemList: MutableList<ProductosDto> = mutableListOf()
        itemList.add(ProductosDto(R.drawable.equipo1, "Equipo", "Prrecio:$ 300"))
        itemList.add(ProductosDto(R.drawable.tv1, "TV", "Precio:$ 1500"))

        val carito = findViewById<TextView>(R.id.tv_cart)
        carito.setOnClickListener {
            irActividad(Carrito::class.java)
        }
    }

    fun iniciarRecyclerViewCarrito(
        listaTendencias: List<ProductosDto>,
        actividad: MainActivity,
        recyclerView: RecyclerView
    ){
        val adaptador = AdapterCarrito(
            listaTendencias,
        )
        recyclerView.adapter= adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }

    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        parametros?.forEach {
            val nombreVariable = it.first
            val valorVariable: Any? = it.second

            when (it.second) {
                is String -> {
                    valorVariable as String
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                is Parcelable -> {
                    valorVariable as Parcelable
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                is Int -> {
                    valorVariable as Int
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
                else -> {
                    valorVariable as String
                    intentExplicito.putExtra(nombreVariable, valorVariable)
                }
            }


        }

        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)

        }
    }

}