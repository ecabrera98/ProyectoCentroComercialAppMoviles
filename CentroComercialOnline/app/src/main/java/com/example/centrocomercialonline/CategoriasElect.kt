package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import com.example.centrocomercialonline.utils.ProductosAudioVideo
import com.example.centrocomercialonline.utils.ProductosEntretenimiento
import com.example.centrocomercialonline.utils.ProductosLineaBlanca
import com.example.centrocomercialonline.utils.ProductosMovilidad
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.jama.carouselview.CarouselView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class CategoriasElect : AppCompatActivity(){

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias_ropa)

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

        val nombreTienda = findViewById<TextView>(R.id.txv_titulo_tienda)
        nombreTienda.setText("LA GANGA")

        val categoriaText1 = findViewById<TextView>(R.id.tv_categoria1)
        categoriaText1.setText("LINEA BLANCA")
        val categoriaText2 = findViewById<TextView>(R.id.tv_categoria2)
        categoriaText2.setText("AUDIO Y VIDEO")
        val categoriaText3 = findViewById<TextView>(R.id.tv_categoria3)
        categoriaText3.setText("ENTRETENIMIENTO")
        val categoriaText4 = findViewById<TextView>(R.id.tv_categoria4)
        categoriaText4.setText("MOVILIDAD")

        val imagesElect1 = arrayListOf(
            "https://crecos.vteximg.com.br/arquivos/ids/178405-1000-1000/cocina-mabe-EM7630FX0.jpg?v=637167079677600000",
            "https://gollo-prod-grupounicomer.netdna-ssl.com/media/catalog/product/cache/7536f51f1dcaf1415428fad840de9edd/1/0/109475_00.jpg",
            "https://livansud.vteximg.com.br/arquivos/ids/160255-1000-1000/licuadora-hamilton-beach-58615-9-velocidades-1.jpg?v=637488273431600000",
            "https://tiaecuador.vteximg.com.br/arquivos/ids/181029-1000-1000/imagen_2021-06-02_164736.png?v=637582672569500000"
        )
        val imagesElect2 = arrayListOf(
            "https://dalthron.com.pe/wp-content/uploads/2019/04/SC-AKX400PS-Product_ImageGlobal-1_pe_es.png",
            "https://m.media-amazon.com/images/I/61KxvageMJL._AC_SL1100_.jpg",
            "https://www.todoparati.online/wp-content/uploads/2019/06/Aud%C3%ADfonos-Bluetooth-Cat.png",
            "https://images.samsung.com/is/image/samsung/latin-fhdtv-j5290-un43j5290ahxpa-frontblack-197304796?\$720_576_PNG\$"
        )
        val imagesElect3 = arrayListOf(
            "https://i.blogs.es/feaa2f/nueva_sony_ps3_slim/450_1000.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/Microsoft-Xbox-One-Console-wKinect.png/1200px-Microsoft-Xbox-One-Console-wKinect.png",
            "https://img.redbull.com/images/c_crop,x_509,y_0,h_1527,w_1781/c_fill,w_650,h_540/q_auto,f_auto/redbullcom/2016/11/02/1331827173241_2/sony-ps4-pro",
            "https://phantom-marca.unidadeditorial.es/2c7447c907807c0e71460e0da3448ba0/crop/0x79/773x512/resize/1320/f/jpg/assets/multimedia/imagenes/2021/03/25/16166666969525.jpg"
        )
        val imagesElect4 = arrayListOf(
            "https://labicikleta.com/wp-content/uploads/2016/07/FeatureBiciMontana-770x513.jpg",
            "https://www.tuningmex.com/fabricaweb/wp-content/uploads/Dominar-perfil-01-780x443.jpg",
            "http://www.integraciondigital.com/presta/24-large_default/motoneta-d125.jpg",
            "https://intershop.com.ec/wp-content/uploads/2021/05/Patineta-NInos-4-scaled.jpg"
        )

//        val imagesElect = arrayListOf(R.drawable.elec1,R.drawable.elec2, R.drawable.elec3, R.drawable.equipo1)
        val electTitle1 = arrayListOf("Cocinas", "Refrigeradores","Licuadora", "Y algo más...",)
        val electTitle2 = arrayListOf("Equipos de sonido", "Mp3 ipods","Audifonos","smarttv con android tv...")
        val electTitle3 = arrayListOf("Play Station 3", "XBOX" ,"Play Station 4","PSP...")
        val electTitle4 = arrayListOf("Bicicletas" ,"Motocicletas","Motonetas", "Variedad de precios...")

        val carouselViewElect1 = findViewById<CarouselView>(R.id.carouselViewGanga1)
        val carouselViewElect2 = findViewById<CarouselView>(R.id.carouselViewGanga2)
        val carouselViewElect3 = findViewById<CarouselView>(R.id.carouselViewGanga3)
        val carouselViewElect4 = findViewById<CarouselView>(R.id.carouselViewGanga4)

        carouselViewElect1.apply {
            size = imagesElect1.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImage(CarouselItem(imagesElect1[position]))
                imageView1.setOnClickListener {
                    irActividad(ProductosLineaBlanca::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle1[position]
            }
            show()
        }

        carouselViewElect2.apply {
            size = imagesElect2.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView2 = view.findViewById<ImageView>(R.id.imageView)
                imageView2.setImage(CarouselItem(imagesElect2[position]))
                imageView2.setOnClickListener {
                    irActividad(ProductosAudioVideo::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle2[position]
            }
            show()
        }

        carouselViewElect3.apply {
            size = imagesElect3.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImage(CarouselItem(imagesElect3[position]))
                imageView1.setOnClickListener {
                    irActividad(ProductosEntretenimiento::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle3[position]
            }
            show()
        }

        carouselViewElect4.apply {
            size = imagesElect4.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView2 = view.findViewById<ImageView>(R.id.imageView)
                imageView2.setImage(CarouselItem(imagesElect4[position]))
                imageView2.setOnClickListener {
                    irActividad(ProductosMovilidad::class.java)
                }
                val textView = view.findViewById<TextView>(R.id.textViewTitle)
                textView.text = electTitle4[position]
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