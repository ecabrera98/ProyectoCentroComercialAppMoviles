package com.example.centrocomercialonline

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.dto.ProductosDto

class AdapterElect (var itemList: List<ProductosDto>?) : RecyclerView.Adapter<AdapterElect.MyViewHolder>() {
    //    private var itemList: List<ItemModel>? = null
    private var clickListener: ClickListener? = null
    /*fun MyAdapter(itemList: List<ItemModel>?) {
        this.itemList = itemList
    }*/

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var title: TextView
        var subtitle: TextView
        var icon: ImageView
        var main: LinearLayout

        init {
            title = parent.findViewById(R.id.title)
            subtitle = parent.findViewById(R.id.subtitle)
            icon = parent.findViewById(R.id.icon)
            main = parent.findViewById(R.id.main)
        }
    }

    fun setClickListener(clickListener: Productos) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_productos, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val row: ProductosDto = itemList!![position]
        holder.title.setText(row.nombre_producto)
        holder.subtitle.setText(row.precio_producto)
        holder.icon.setImageResource(row.imageId)
        holder.main.setOnClickListener {
            Log.e("Position adapter:", position.toString())
            if (clickListener != null) {
                clickListener?.itemClicked(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    interface ClickListener {
        fun itemClicked(view: View?, position: Int)
    }

}
