package com.example.centrocomercialonline

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType



class Tiendas : AppCompatActivity(){

    private val container by lazy { findViewById<View>(R.id.container) }
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    private var lastColor: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas)

        lastColor = (container.background as ColorDrawable).color

        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> irActividad(Tiendas::class.java)  to "Inicio"
                R.id.buscar -> R.color.colorSecundary to "Buscar"
                R.id.carrito -> R.color.colorTres to "Carrito"
                R.id.perfil -> R.color.white to "Perfil"
                else -> R.color.white to ""
            }

            title.text = option.second
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.perfil, 32)
        }


        val imagesElect = arrayListOf(R.drawable.elec1,R.drawable.elec2, R.drawable.elec3, R.drawable.equipo1)
        val carouselViewElect = findViewById<CarouselView>(R.id.carouselViewTiendas1)

        val imagesRopa = arrayListOf(R.drawable.ropa2,R.drawable.ropa3, R.drawable.ropa1, R.drawable.ropa3)
        val carouselViewRopa = findViewById<CarouselView>(R.id.carouselViewTiendas2)

        carouselViewElect.apply {
            size = imagesElect.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.center_carousel_item
            indicatorAnimationType = IndicatorAnimationType.COLOR
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImageDrawable(resources.getDrawable(imagesElect[position]))
                imageView1.setOnClickListener {irActividad(CategoriasElect::class.java) }
            }
            show()
        }

        carouselViewRopa.apply {
            size = imagesRopa.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.center_carousel_item
            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView2 = view.findViewById<ImageView>(R.id.imageView)
                imageView2.setImageDrawable(resources.getDrawable(imagesRopa[position]))
                imageView2.setOnClickListener {irActividad(CategoriasRopa::class.java) }
            }
            show()
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
