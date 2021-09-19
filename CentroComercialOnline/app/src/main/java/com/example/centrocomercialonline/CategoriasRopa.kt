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
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage


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

        val imagesElect1 = arrayListOf(
            "https://ae01.alicdn.com/kf/HTB1ZuHLbcnrK1RjSspkq6yuvXXal/Zapatos-deportivos-de-tejido-volador-para-hombre-zapatillas-con-cordones-transpirables-c-modas-y-con-absorci.jpg_Q90.jpg_.webp",
            "https://i.pinimg.com/564x/47/71/fc/4771fcb7e3f3a6a2fe805f7fcc1230c7.jpg",
            "https://www.calzadofaerma.com/wp-content/uploads/2020/07/Hermosos-tacones-de-mujer-de-color-Rojo2.jpg",
            "https://i.pinimg.com/originals/27/ab/d7/27abd74ef49c73f37051a2bffde7cccd.jpg"
        )
        val imagesElect2 = arrayListOf(
            "https://i.pinimg.com/736x/b4/61/73/b461731f717c93168cfdeed81119d23c.jpg",
            "https://i.pinimg.com/originals/9a/56/41/9a5641cc33f4d22a1448b31b673f31f8.jpg",
            "https://www.induvest.com.ec/wp-content/uploads/2021/04/CAMISA-JEAN-REFLECTIVA-FRENTE.jpg",
            "https://ae01.alicdn.com/kf/H168146c62e82494d839daf6f035a19cb6/Mother-Daughter-Baby-Clothes-Family-Matching-Outfits-Father-Son-T-Shirt-Plaid-Shirt-Mum-Mama-and.jpg_q50.jpg"
        )
        val imagesElect3 = arrayListOf(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLU3Fr5qwa5j0u1Ao-P6hqq-1iAxVU7o0Lc_HxaZHpWAv3IfrEFTRURYUOdBVpFJe4ICg&usqp=CAU",
            "https://i.pinimg.com/originals/14/c4/f3/14c4f341e0a6440c90920e7c23a237b4.jpg",
            "https://previews.123rf.com/images/georgerudy/georgerudy1605/georgerudy160501062/57603827-retrato-de-cuerpo-entero-de-ni%C3%B1os-poco-lindo-en-jeans-ropa-elegante-mirando-la-c%C3%A1mara-y-sonriente-de.jpg",
            "https://cdn.fashiola.mx/L549919575/reclaimed-vintage-inspired-the-83-unisex-relaxed-jean-in-light-wash-blue.jpg"
        )
        val imagesElect4 = arrayListOf(
            "https://m.media-amazon.com/images/I/416fbyLaFKS._SL500_.jpg",
            "https://bazar.ec/wp-content/uploads/2020/07/rBVaVl1TcAiATairAAgXXBmAZpM805.jpg",
            "https://laovejanegra.sogamosocompravirtual.com/921-large_default/chaqueta-hombre-lana-de-oveja-talla-m.jpg",
            "https://velavi.com/wp-content/uploads/2018/07/pp-conjunto-impermeable.jpg"
        )

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
            size = imagesElect1.size
            autoPlay = true
            autoPlayDelay = 3000
            resource = R.layout.start_carousel_elects_item
            setCarouselViewListener { view, position ->
                val imageView1 = view.findViewById<ImageView>(R.id.imageView)
                imageView1.setImage(CarouselItem(imagesElect1[position]))
                imageView1.setOnClickListener {
                    irActividad(ProductosZapatos::class.java)
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
                    irActividad(ProductosCamisetas::class.java)
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
                    irActividad(ProductosJeans::class.java)
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