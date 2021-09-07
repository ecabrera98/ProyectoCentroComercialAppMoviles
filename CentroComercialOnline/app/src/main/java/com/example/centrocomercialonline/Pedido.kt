package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto

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

    }
}