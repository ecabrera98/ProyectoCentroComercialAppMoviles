package com.example.centrocomercialonline

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.adapters.AdapterCarrito
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Carrito : AppCompatActivity(){
    private lateinit var adaptadorCarrito: AdapterCarrito
    private lateinit var recyclerView: RecyclerView
    private lateinit var productoArrayList : ArrayList<ProductosCarritoDto>
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

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

        recyclerView = findViewById(R.id.rcv_carrito)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf()
        adaptadorCarrito = AdapterCarrito(productoArrayList,this)

        recyclerView.adapter = adaptadorCarrito
        cargarProductoCarrito()
        val botonAgregar = findViewById<TextView>(R.id.btn_agregar_productos)

        botonAgregar.setOnClickListener {
            irActividad(BuscarProducto::class.java)
        }
    }

    fun cargarProductoCarrito(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        db.collection("Carritos")
            .document("carrito_${usuarioLocal!!.email.toString()}")
            .collection("carrito_${usuarioLocal.email.toString()}")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(ProductosCarritoDto::class.java)
                    producto.imageId = document.get("Imagen").toString()
                    producto.nombre_producto = document.get("NombreProducto").toString()
                    producto.precio_producto = document.getDouble("Precio")!!
                    producto.cantidad_producto = document.getLong("Cantidad")!!.toInt()
                    producto.subtotal = document.getDouble("Subtotal")!!
                    productoArrayList.add(producto)
                    adaptadorCarrito.notifyDataSetChanged()
                }
                val comprar = findViewById<TextView>(R.id.btn_comprar_carrito)
                comprar.setOnClickListener {
                if(adaptadorCarrito.itemCount == 0) {
                    showAlert()
                }else{
                    irActividad(Comprar::class.java)
                }
                }
            }
            .addOnFailureListener {
                Log.i("CARRITO", "FALLO")
            }

    }


    fun showAlert(){
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("CARRITO VACIO DETECTADO")
        builder.setMessage("Porfavor añada artículos con el Botón AGREGAR")
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
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