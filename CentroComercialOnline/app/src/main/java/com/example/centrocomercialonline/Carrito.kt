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
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.dto.ProductosDto
import com.google.firebase.storage.FirebaseStorage
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Carrito : AppCompatActivity(){
    private lateinit var adaptadorCarrito: AdapterCarrito

    private lateinit var recyclerView: RecyclerView

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        menu.setOnItemSelectedListener { id ->
        val option = when (id) {
            R.id.home -> irActividad(Tiendas::class.java)  to "Inicio"
            R.id.buscar -> R.color.colorSecundary to "Buscar"
            R.id.carrito -> irActividad(Carrito::class.java) to "Carrito"
            R.id.perfil -> irActividad(PerfilUsuario::class.java)  to "Perfil"
            else -> R.color.white to ""
        }

        title.text = option.second
    }


    if (savedInstanceState == null) {
        menu.showBadge(R.id.home)
        menu.showBadge(R.id.perfil, 32)
    }

        val producto = intent.getParcelableExtra<ProductosDto>("Producto")

        var itemList: ArrayList<ProductosDto>
        itemList = arrayListOf()

        Log.i("Detalle-Carrito","${producto!!.imageId}")

        itemList.add(ProductosDto(producto.imageId,producto.nombre_producto,producto.precio_producto))
        itemList.add(ProductosDto("camiseta1","camiseta","$360"))

        recyclerView = findViewById(R.id.rcv_carrito)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adaptadorCarrito = AdapterCarrito(itemList,this)

        recyclerView.adapter = adaptadorCarrito


        val comprar = findViewById<TextView>(R.id.btn_comprar_carrito)
        comprar.setOnClickListener {
            irActividad(Pedido::class.java,
                arrayListOf(Pair("Producto",ProductosDto
                    (producto.imageId,producto.nombre_producto,producto.precio_producto))
                )
            )
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