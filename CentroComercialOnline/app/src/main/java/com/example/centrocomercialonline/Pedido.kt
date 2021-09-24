package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.adapters.AdapterCarrito
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Pedido : AppCompatActivity(){
    private lateinit var adaptadorCarrito: AdapterCarrito
    private lateinit var productoArrayList : ArrayList<ProductosCarritoDto>
    private lateinit var recyclerView: RecyclerView
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> irActividad(Tiendas::class.java)  to "Inicio"
                R.id.buscar -> irActividad(BuscarProducto::class.java) to "Buscar"
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
        recyclerView = findViewById(R.id.rcv_pedido)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf()
        adaptadorCarrito = AdapterCarrito(productoArrayList,this)

        recyclerView.adapter = adaptadorCarrito
        cargarProductoCarrito()

        val botonTarjeta = findViewById<Button>(R.id.btn_metodo_pago)
        botonTarjeta.setOnClickListener {irActividad(MetodoPago::class.java) }

    }

    fun cargarProductoCarrito(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        val referencia = db.collection("carrito_${usuarioLocal!!.email.toString()}")
        referencia
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(ProductosCarritoDto::class.java)
                    producto!!.imageId = document.get("Imagen").toString()
                    producto!!.nombre_producto = document.get("NombreProducto").toString()
                    producto!!.precio_producto = document.getDouble("Precio")!!

                    productoArrayList.add(producto)
                    adaptadorCarrito?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                Log.i("CARRITO", "FALLO")
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