package com.example.centrocomercialonline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.dto.BProductosFirebase
import com.example.centrocomercialonline.dto.ProductosDto
import com.google.firebase.storage.FirebaseStorage
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class AdapterCarrito (private val itemList : ArrayList<ProductosDto>, val context : Context) : RecyclerView.Adapter<AdapterCarrito.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_carrito, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = itemList[position]
        holder.bindItems(itemList[position])

        val icon: ImageView = holder.itemView.findViewById(R.id.img_producto)
        var imageName: String = model.imageId

        val storageRef = FirebaseStorage.getInstance().reference.child("imagesApp/$imageName.jpeg")
        Glide.with(context)
            .load(storageRef)
            .into(icon)


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindItems(model: ProductosDto) {
            val producto: TextView = itemView.findViewById(R.id.txv_nombre_producto)
            val precio: TextView = itemView.findViewById(R.id.txv_precio)

            producto.text = model.nombre_producto
            precio.text = model.precio_producto

        }
    }
    

}