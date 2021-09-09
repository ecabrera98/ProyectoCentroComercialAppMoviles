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

class Pedido : AppCompatActivity(),AdapterCarrito.ClickListener {
    private val itemList: MutableList<ProductosDto> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    var adapter: AdapterCarrito? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        recyclerView = findViewById(R.id.rcv_pedido)
        prepareItem()
        adapter = AdapterCarrito(itemList)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter

        val botonTarjeta = findViewById<Button>(R.id.btn_metodo_pago)
        botonTarjeta.setOnClickListener {irActividad(MetodoPago::class.java) }

    }

    private fun prepareItem() {
        itemList.add(ProductosDto(R.drawable.equipo1, "Equipo", "Prrecio:$ 300"))
        itemList.add(ProductosDto(R.drawable.tv1, "TV", "Precio:$ 1500"))
    }

    override fun itemClicked(view: View?, position: Int) {
        when (position) {
            0 -> SharedPreferenceUtils(this).setCategoryItem("flower")
            1 -> SharedPreferenceUtils(this).setCategoryItem("fruit")
            2 -> SharedPreferenceUtils(this).setCategoryItem("leaves")
            3 -> SharedPreferenceUtils(this).setCategoryItem("root")
            4 -> SharedPreferenceUtils(this).setCategoryItem("salad")
            else -> Log.e("position :", position.toString())
        }

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