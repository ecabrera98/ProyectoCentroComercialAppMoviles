package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.adapters.AdapterProduct
import com.example.centrocomercialonline.dto.BProductosFirebase
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import java.util.*
import kotlin.collections.ArrayList


class BuscarProducto : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    var db = Firebase.firestore
    private lateinit var productArrayList : ArrayList<BProductosFirebase>
    private lateinit var newproductArrayList : ArrayList<BProductosFirebase>
    private lateinit var adaptadorProductos: AdapterProduct
    private lateinit var productRecyclerview : RecyclerView

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_producto)


        toolbar = findViewById(R.id.toolbarbuscar)

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

        productRecyclerview = findViewById(R.id.recyclerViewProductosBuscar)
        productRecyclerview.layoutManager = LinearLayoutManager(this)
        productRecyclerview.setHasFixedSize(true)

        productArrayList = arrayListOf()
        newproductArrayList = arrayListOf()

        getUserData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.buscar,menu)
        val item = menu?.findItem(R.id.appSearchBar)
        if (item != null){
            val searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        newproductArrayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        productArrayList.forEach{
                            if(it.nombre_producto.toLowerCase(Locale.getDefault()).contains(search)){
                                newproductArrayList.add(it)
                            }
                        }
                        productRecyclerview.adapter!!.notifyDataSetChanged()
                    }else{
                        newproductArrayList.clear()
                        newproductArrayList.addAll(productArrayList)
                        productRecyclerview.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            })
        }
        return  super.onCreateOptionsMenu(menu)
    }

    private fun getUserData() {
        db = FirebaseFirestore.getInstance()
        db.collection("productos")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("FIrestore Error", error.message.toString())
                        return
                    }
                    for (de: DocumentChange in value?.documentChanges!!){
                        if(de.type == DocumentChange.Type.ADDED){
                            productArrayList.add(de.document.toObject(BProductosFirebase::class.java))
                            newproductArrayList.addAll(productArrayList)
                        }
                    }
                    adaptadorProductos = AdapterProduct(newproductArrayList,this@BuscarProducto)
                    productRecyclerview.adapter = adaptadorProductos
                    adaptadorProductos.notifyDataSetChanged()
                }
            })
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