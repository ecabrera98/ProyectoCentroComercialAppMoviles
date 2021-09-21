package com.example.centrocomercialonline

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Pedido : AppCompatActivity(){
    private lateinit var adaptadorCarrito: AdapterCarrito

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        val producto = intent.getParcelableExtra<ProductosDto>("Producto")
        var itemList: ArrayList<ProductosDto>
        itemList = arrayListOf()

        Log.i("Detalle0","${producto!!.imageId}")
        itemList.add(ProductosDto(producto!!.imageId,producto.nombre_producto,producto.precio_producto))
        //itemList.add(ProductosDto("camiseta1","camiseta","360"))
        recyclerView = findViewById(R.id.rcv_pedido)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adaptadorCarrito = AdapterCarrito(itemList,this)

        recyclerView.adapter = adaptadorCarrito

        val botonTarjeta = findViewById<Button>(R.id.btn_metodo_pago)
        botonTarjeta.setOnClickListener {irActividad(MetodoPago::class.java) }

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