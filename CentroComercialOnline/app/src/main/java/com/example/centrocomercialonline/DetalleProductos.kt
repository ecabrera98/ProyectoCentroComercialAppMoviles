package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.centrocomercialonline.dto.ProductosDto

class DetalleProductos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_productos)

        val Producto = intent.getIntExtra("ItemPosition",0)
        Log.e("position :", "${Producto}")

        val itemList: MutableList<ProductosDto> = mutableListOf()
        itemList.add(ProductosDto(R.drawable.equipo1, "Equipo", "Prrecio:$ 300"))
        itemList.add(ProductosDto(R.drawable.tv1, "TV", "Precio:$ 1500"))


        val imagen = findViewById<ImageView>(R.id.iv_food)
        imagen.setImageResource(itemList[Producto].imageId)
        val tienda = findViewById<TextView>(R.id.tv_title_tienda)

        val categoria = findViewById<TextView>(R.id.tv_title_categoria)
        val producto = findViewById<TextView>(R.id.tv_title_producto)
        producto.setText(itemList[Producto].nombre_producto)
        val detalle = findViewById<TextView>(R.id.tv_title_descripcion_producto)
        val precio = findViewById<TextView>(R.id.tv_price)
        precio.setText(itemList[Producto].precio_producto)


        Log.e("position :", "${itemList[Producto]}")

        val carito = findViewById<Button>(R.id.tv_cart)
        carito.setOnClickListener {
            irActividad(Carrito::class.java)
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