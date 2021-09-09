package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto
import com.example.centrocomercialonline.dto.TarjetaDto

class MetodoPago : AppCompatActivity(),AdapterMetodoPago.ClickListener {
    private val itemList: MutableList<TarjetaDto> = mutableListOf()
    private var recyclerView: RecyclerView? = null
    var adapter: AdapterMetodoPago? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metodo_pago)

        recyclerView = findViewById(R.id.rcv_tarjeta)
        prepareItem()
        adapter = AdapterMetodoPago(itemList)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter

        val imgCard = findViewById<ImageButton>(R.id.img_añadir)
        imgCard.setOnClickListener {
            irActividad(AgregarTarjeta::class.java)
        }


    }

    private fun prepareItem() {
        itemList.add(TarjetaDto("1758412365805289", "Araceli", "05/24","149"))

    }

    override fun itemClicked (envio: ImageButton) { }

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