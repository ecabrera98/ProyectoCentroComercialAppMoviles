package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide.with
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class DetalleProductos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_productos)


        val nombre = findViewById<TextView>(R.id.tv_nombre_producto)
        val categoria = findViewById<TextView>(R.id.tv_categoria_producto)
        val detalle = findViewById<TextView>(R.id.tv_descripcion_producto)
        val imagen = findViewById<ImageView>(R.id.iv_product)

        val intent = intent
        val nombreProducto = intent.getStringExtra("iNombre")
        val precioProducto = intent.getDoubleExtra("iPrecio",0.0)
        val catProducto = intent.getStringExtra("iCat")
        val descProducto = intent.getStringExtra("iDesc")
        val imagenProducto = intent.getStringExtra("iIma")

        nombre.text = nombreProducto
        var ingresoPrecio: Double = precioProducto
        categoria.text = catProducto
        detalle.text = descProducto

        val storageRef = FirebaseStorage.getInstance().reference.child("imagesApp/$imagenProducto.jpeg")
        with(this)
                .load(storageRef)
                .into(imagen)

        val carito = findViewById<Button>(R.id.tv_cart)
        carito.setOnClickListener {
            irActividad(Carrito::class.java,
                arrayListOf(Pair("Producto",ProductosCarritoDto
                    (imagenProducto!!,nombreProducto!!,precioProducto))
                )
            )
            val nuevoProducto = hashMapOf<String, Any>(
                "Imagen" to imagenProducto.toString(),
                "NombreProducto" to nombre.text,
                "Precio" to ingresoPrecio,
            )
            val db = Firebase.firestore
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuarioLocal = instanciaAuth.currentUser
            val referencia = db.collection("carrito${usuarioLocal!!.email.toString()}")
            referencia
                .document(imagenProducto)
                .set(nuevoProducto)
                .addOnSuccessListener {
                    Log.i("firestore-persona", "Valio carrito")
                }
                .addOnFailureListener {
                    Log.i("firestore-persona", "no se pudo cargar los datos al firestore ")
                }
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