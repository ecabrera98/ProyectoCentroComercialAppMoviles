package com.example.centrocomercialonline.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.*
import com.example.centrocomercialonline.R
import com.example.centrocomercialonline.adapters.AdapterProduct
import com.example.centrocomercialonline.dto.BProductosFirebase
import com.google.firebase.firestore.*
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage


class ProductosZapatos : AppCompatActivity() {
    var db = Firebase.firestore
    private lateinit var productRecyclerview : RecyclerView
    private lateinit var productArrayList : ArrayList<BProductosFirebase>
    private lateinit var adaptadorProductos: AdapterProduct

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

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

        val imagesProducts = arrayListOf(
            "https://ae01.alicdn.com/kf/HTB1ZuHLbcnrK1RjSspkq6yuvXXal/Zapatos-deportivos-de-tejido-volador-para-hombre-zapatillas-con-cordones-transpirables-c-modas-y-con-absorci.jpg_Q90.jpg_.webp",
            "https://i.pinimg.com/564x/47/71/fc/4771fcb7e3f3a6a2fe805f7fcc1230c7.jpg",
            "https://www.calzadofaerma.com/wp-content/uploads/2020/07/Hermosos-tacones-de-mujer-de-color-Rojo2.jpg",
            "https://i.pinimg.com/originals/27/ab/d7/27abd74ef49c73f37051a2bffde7cccd.jpg"

        )
        val carouselViewProductos = findViewById<CarouselView>(R.id.carouselViewProductos)

        carouselViewProductos.apply {
            size = imagesProducts.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.center_carousel_item
            indicatorAnimationType = IndicatorAnimationType.COLOR
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImage(CarouselItem(imagesProducts[position]))
                imageView1.setOnClickListener {irActividad(CategoriasElect::class.java) }
            }
            show()
        }

        productRecyclerview = findViewById(R.id.recyclerViewProductos)
        productRecyclerview.layoutManager = LinearLayoutManager(this)
        productRecyclerview.setHasFixedSize(true)

        productArrayList = arrayListOf()
       adaptadorProductos = AdapterProduct(productArrayList,this)

        productRecyclerview.adapter = adaptadorProductos
        getProductoData()
    }


    private fun getProductoData() {
        db = FirebaseFirestore.getInstance()
        db.collection("productos")
            .whereEqualTo("descripcion_categoria", "Zapatos")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("FIrestore Error", error.message.toString())
                        return
                    }
                    for (de: DocumentChange in value?.documentChanges!!){
                        if(de.type == DocumentChange.Type.ADDED){
                            productArrayList.add(de.document.toObject(BProductosFirebase::class.java))
                        }
                    }
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