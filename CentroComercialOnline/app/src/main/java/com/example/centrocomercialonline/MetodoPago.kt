package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.adapters.AdapterMetodoPago
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.example.centrocomercialonline.dto.TarjetaDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MetodoPago : AppCompatActivity(){
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }
    private lateinit var adaptadorTarjeta: AdapterMetodoPago
    private lateinit var recyclerView: RecyclerView
    private lateinit var productoArrayList : ArrayList<TarjetaDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metodo_pago)

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


        recyclerView = findViewById(R.id.rcv_tarjeta)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf()
        //productoArrayList.add(TarjetaDto("1789654123","Joselyn Taco","20/08","149"))
        adaptadorTarjeta = AdapterMetodoPago(productoArrayList,this)

        recyclerView.adapter = adaptadorTarjeta
        cargarTarjetas()

        val imgCard = findViewById<ImageButton>(R.id.img_añadir)
        imgCard.setOnClickListener {
            irActividad(AgregarTarjeta::class.java)
            Log.i("Tarjeta","Se fue a la Tarjeta")
        }


    }

    private fun cargarTarjetas() {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        val referencia = db.collection("Tarjetas")
        referencia
            .document("tarjeta_${usuarioLocal!!.email.toString()}")
            .collection("tarjeta_${usuarioLocal!!.email.toString()}")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(TarjetaDto::class.java)
                    producto!!.numeroTarjeta= document.get("NumeroTarjeta").toString()
                    producto!!.nombreTitular = document.get("NombreTitular").toString()
                    producto!!.fechaExpiracion = document.get("FechaExpiracion").toString()

                    productoArrayList.add(producto)
                    adaptadorTarjeta?.notifyDataSetChanged()
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