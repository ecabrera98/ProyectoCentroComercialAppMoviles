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
import com.example.centrocomercialonline.utils.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.jama.carouselview.CarouselView


class CategoriasRopa : AppCompatActivity() {

    private val container by lazy { findViewById<View>(R.id.containerRegistrar) }
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    private var lastColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias_ropa)

        lastColor = (container.background as ColorDrawable).color

        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> irActividad(Tiendas::class.java)  to "Inicio"
                R.id.buscar -> R.color.colorSecundary to "Buscar"
                R.id.carrito -> R.color.colorTres to "Carrito"
                R.id.perfil -> irActividad(PerfilUsuario::class.java)  to "Perfil"
                else -> R.color.white to ""
            }

            title.text = option.second
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.perfil, 32)
        }
        val nombreTienda = findViewById<TextView>(R.id.txv_titulo_tienda)
        nombreTienda.setText("DE PRATI")

        val categoriaText1 = findViewById<TextView>(R.id.tv_categoria1)
        categoriaText1.setText("ZAPATOS")
        val categoriaText2 = findViewById<TextView>(R.id.tv_categoria2)
        categoriaText2.setText("CAMISETAS")
        val categoriaText3 = findViewById<TextView>(R.id.tv_categoria3)
        categoriaText3.setText("JEANS")
        val categoriaText4 = findViewById<TextView>(R.id.tv_categoria4)
        categoriaText4.setText("CHAQUETAS")

        val imagesElect = arrayListOf(R.drawable.elec1,R.drawable.elec2, R.drawable.elec3, R.drawable.equipo1)
        val electTitle1 = arrayListOf("Deportivos","Casuales","Tacones","Botas...")
        val electTitle2 = arrayListOf("Blusas","Busos","Camisas","Personalizadas...")
        val electTitle3 = arrayListOf("Caballeros","Damas","Niños","Unisex...")
        val electTitle4 = arrayListOf("Casuales","Cuero","Lana","Impermeables...")

        val carouselViewElect1 = findViewById<CarouselView>(R.id.carouselViewGanga1)
        val carouselViewElect2 = findViewById<CarouselView>(R.id.carouselViewGanga2)
        val carouselViewElect3 = findViewById<CarouselView>(R.id.carouselViewGanga3)
        val carouselViewElect4 = findViewById<CarouselView>(R.id.carouselViewGanga4)

        carouselViewElect1.apply {
            size = imagesElect.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImageDrawable(resources.getDrawable(imagesElect[position]))
                imageView1.setOnClickListener {
                    irActividad(ProductosZapatos::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle1[position]
            }
            show()
        }

        carouselViewElect2.apply {
            size = imagesElect.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView2 = view.findViewById<ImageView>(R.id.imageView)
                imageView2.setImageDrawable(resources.getDrawable(imagesElect[position]))
                imageView2.setOnClickListener {
                    irActividad(ProductosCamisetas::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle2[position]
            }
            show()
        }

        carouselViewElect3.apply {
            size = imagesElect.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImageDrawable(resources.getDrawable(imagesElect[position]))
                imageView1.setOnClickListener {
                    irActividad(ProductosJeans::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle3[position]
            }
            show()
        }

        carouselViewElect4.apply {
            size = imagesElect.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView2 = view.findViewById<ImageView>(R.id.imageView)
                imageView2.setImageDrawable(resources.getDrawable(imagesElect[position]))
                imageView2.setOnClickListener {
                    irActividad(ProductosChaquetas::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle4[position]
            }
            show()
        }
    }

    /*fun setearUsuarioFirebase(){
        val db = Firebase.firestore
        if (SharedPreferenceUtils(this).getCategoryItem() != "null") {
            database = FirebaseDatabase.getInstance()
            // allFoodData= database.getReference("AllFood/flower");
            allFoodData = database!!.getReference("AllFood/" + SharedPreferenceUtils(this).getCategoryItem())
//            progressDialog?.show()
            CustomProgressBar.showProgressbar()
        } else {

        }

    }*/

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