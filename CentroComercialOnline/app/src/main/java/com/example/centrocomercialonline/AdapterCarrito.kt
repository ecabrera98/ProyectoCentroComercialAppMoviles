package com.example.centrocomercialonline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto

class AdapterCarrito (var itemList: List<ProductosDto>?) : RecyclerView.Adapter<AdapterCarrito.MyViewHolder>() {

    private var clickListener: AdapterCarrito.ClickListener? = null

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var producto: TextView
        var precio: TextView
        var icon: ImageView

        init {
            producto = parent.findViewById(R.id.txv_nombre_producto)
            precio = parent.findViewById(R.id.txv_precio)
            icon = parent.findViewById(R.id.img_producto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_carrito, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row: ProductosDto = itemList!![position]
        holder.producto.setText(row.title)
        holder.precio.setText(row.subtitle)
        holder.icon.setImageResource(row.imageId)

    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    interface ClickListener {
        fun itemClicked(view: View?, position: Int)
    }

    fun setClickListener(clickListener: Carrito) {
        this.clickListener = clickListener
    }

}