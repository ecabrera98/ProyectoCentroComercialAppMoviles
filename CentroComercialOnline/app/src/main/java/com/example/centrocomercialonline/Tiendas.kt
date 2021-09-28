package com.example.centrocomercialonline

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.example.centrocomercialonline.dto.TiendasDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class Tiendas : AppCompatActivity(){

    private val container by lazy { findViewById<View>(R.id.containerRegistrar) }
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    private lateinit var tiendasArrayList : ArrayList<TiendasDto>

    private var lastColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)

        lastColor = (container.background as ColorDrawable).color

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

        val imageView1 = findViewById<ImageView>(R.id.img_tienda1)
        imageView1.setOnClickListener {irActividad(CategoriasElect::class.java) }

        val imageView2 = findViewById<ImageView>(R.id.img_tienda2)
        imageView2.setOnClickListener {irActividad(CategoriasRopa::class.java) }

        tiendasArrayList = arrayListOf()
        setearTiendaElectFirebase()
        setearTiendaRopaFirebase()
    }


    fun setearTiendaElectFirebase(){
        val etE = findViewById<TextView>(R.id.tv_tienda_elect)
        val db = Firebase.firestore
        db.collection("tiendas")
            .whereEqualTo("categoria_tienda", "Electrodomésticos")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    val tiendas = document.toObject(TiendasDto::class.java)
                    tiendas.title= document.get("nombre_tienda").toString()
                    etE.text = tiendas.title
                    tiendasArrayList.add(tiendas)
                    Log.i("firestore-tiendas", "Se estrajó la tienda con éxito ${tiendas.title}")
                }
            }
            .addOnFailureListener {
                Log.i("firestore-tiendas", "Falló: $it")
            }
    }


    fun setearTiendaRopaFirebase(){
        val etR = findViewById<TextView>(R.id.tv_tienda_ropa)
        val db = Firebase.firestore
        db.collection("tiendas")
            .whereEqualTo("categoria_tienda", "Ropa")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    val tiendas = document.toObject(TiendasDto::class.java)
                    tiendas.title= document.get("nombre_tienda").toString()
                    etR.text = tiendas.title
                    tiendasArrayList.add(tiendas)
                    Log.i("firestore-tiendas", "Se estrajó la tienda con éxito ${tiendas.title}")
                }
            }
            .addOnFailureListener {
                Log.i("firestore-tiendas", "Falló: $it")
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
