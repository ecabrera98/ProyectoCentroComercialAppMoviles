package com.example.centrocomercialonline

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.TarjetaDto
import com.firebase.ui.auth.AuthUI
import java.security.AccessController.getContext

class AdapterMetodoPago(var itemList: List<TarjetaDto>?) : RecyclerView.Adapter<AdapterMetodoPago.MyViewHolder>() {

    private var clickListener: ClickListener? = null

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        var numeroTarjeta: TextView
        var nombreTitular: TextView
        var fechaExpiracion: TextView

        init {

            numeroTarjeta = parent.findViewById(R.id.txv_numero_tarjeta)
            nombreTitular = parent.findViewById(R.id.txv_nombre_tarjeta)
            fechaExpiracion = parent.findViewById(R.id.txv_fecha_expiración)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_tarjeta, parent, false)

        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row: TarjetaDto = itemList!![position]
        holder.numeroTarjeta.setText(row.numeroTarjeta)
        holder.nombreTitular.setText(row.nombreTitular)
        holder.fechaExpiracion.setText(row.fechaExpiracion)
    }


    override fun getItemCount(): Int {
        return itemList!!.size
    }

    interface ClickListener {
        fun itemClicked(envio: ImageButton)
    }

}