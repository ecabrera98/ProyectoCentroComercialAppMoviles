package com.example.centrocomercialonline.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.DetalleProductos
import com.example.centrocomercialonline.R
import com.example.centrocomercialonline.dto.BProductosFirebase
import com.google.firebase.storage.FirebaseStorage


class AdapterProduct (private val productList : ArrayList<BProductosFirebase>, val context : Context) : RecyclerView.Adapter<AdapterProduct.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_productos,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = productList[position]
        holder.bindItems(productList[position])

        val productos =  holder.itemView.findViewById<LinearLayout>(R.id.main)
        val imagen =  holder.itemView.findViewById<ImageView>(R.id.icon)
        var imageName: String = model.imageName
        val storageRef = FirebaseStorage.getInstance().reference.child("imagesApp/$imageName.jpeg")

        Glide.with(context)
            .load(storageRef)
            .into(imagen)

        productos.setOnClickListener{
            var nombreProducto: String = model.nombre_producto
            var precioProducto: Double = model.precio_producto
            var categoriaProducto: String = model.descripcion_categoria
            var descripcionProducto: String = model.descripcion_producto

            val itent = Intent(context, DetalleProductos::class.java)
            itent.putExtra("iNombre", nombreProducto)
            itent.putExtra("iPrecio", precioProducto)
            itent.putExtra("iCat", categoriaProducto)
            itent.putExtra("iDesc", descripcionProducto)
            itent.putExtra("iIma",imageName)

            Log.i("Productos seteados", "Vale")
            context.startActivity(itent)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(model: BProductosFirebase) {
            val nombre : TextView = itemView.findViewById(R.id.title)
            val precio : TextView = itemView.findViewById(R.id.subtitle)

            nombre.text = model.nombre_producto
            precio.text = model.precio_producto.toString()
        }


    }
}
