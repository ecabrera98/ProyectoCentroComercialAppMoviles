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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType

class Productos : AppCompatActivity(),  AdapterElect.ClickListener {
    private val itemList: MutableList<ProductosDto> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    var adapter: AdapterElect? = null

    private val container by lazy { findViewById<View>(R.id.containerRegistrar) }
    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    private var lastColor: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        lastColor = (container.background as ColorDrawable).color

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

        val imagesProducts = arrayListOf(R.drawable.ropa2,R.drawable.ropa3, R.drawable.ropa1, R.drawable.ropa3)
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
                imageView1.setImageDrawable(resources.getDrawable(imagesProducts[position]))
                imageView1.setOnClickListener {irActividad(CategoriasElect::class.java) }
            }
            show()
        }


        recyclerView = findViewById(R.id.recyclerViewProductos)
        prepareItem()
        adapter = AdapterElect(itemList)
        adapter?.setClickListener(this)
        adapter?.setClickListener(this)

        recyclerView?.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
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

        irActividad(DetalleProductos::class.java, arrayListOf(Pair("ItemPosition",position)))

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