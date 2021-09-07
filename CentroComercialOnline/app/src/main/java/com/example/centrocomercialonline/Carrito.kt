package com.example.centrocomercialonline

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Carrito : AppCompatActivity(),AdapterCarrito.ClickListener {
    private val itemList: MutableList<ProductosDto> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    var adapter: AdapterCarrito? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        recyclerView = findViewById(R.id.rcv_carrito)
        prepareItem()
        adapter = AdapterCarrito(itemList)
        adapter?.setClickListener(this)
        adapter?.setClickListener(this)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter

        val comprar = findViewById<TextView>(R.id.btn_comprar_carrito)
        comprar.setOnClickListener {
            irActividad(Pedido::class.java)
        }

    }

    private fun prepareItem() {
        itemList.add(ProductosDto(R.drawable.equipo1, "Equipo", "Prrecio:$ 300"))
        itemList.add(ProductosDto(R.drawable.tv1, "TV", "Precio:$ 1500"))
        itemList.add(ProductosDto(R.drawable.equipo1, "Equipo2", "Precio: $ 700"))
        itemList.add(ProductosDto(R.drawable.tv1, "TV2", "Pricio: $ 300"))
        itemList.add(ProductosDto(R.drawable.equipo2, "Equipo3", "Pricio: $ 200"))
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
        startActivity(Intent(this@Carrito, Pedido::class.java).putExtra("ItemPosition ", position))
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