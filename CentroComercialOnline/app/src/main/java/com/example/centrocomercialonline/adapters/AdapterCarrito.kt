package com.example.centrocomercialonline.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.Comprar
import com.example.centrocomercialonline.R
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AdapterCarrito (private val itemList : ArrayList<ProductosCarritoDto>, val context : Context) : RecyclerView.Adapter<AdapterCarrito.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_carrito, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = itemList[position]
        holder.bindItems(itemList[position])

        val icon: ImageView = holder.itemView.findViewById(R.id.img_producto_c)
        var imageName: String = model.imageId

        val storageRef = FirebaseStorage.getInstance().reference.child("imagesApp/$imageName.jpeg")
        Glide.with(context)
            .load(storageRef)
            .into(icon)

        holder.calcularTotal()

        val aumentar: ImageView = holder.itemView.findViewById(R.id.img_aumentar)
        val disminuir: ImageView = holder.itemView.findViewById(R.id.img_disminuir)

        val itent = Intent(context, Comprar::class.java)
        itent.putExtra("iTotal",  holder.calcularTotal())

        icon.setOnClickListener {
            holder.popupMenus(it)
        }

        aumentar.setOnClickListener {
            var total =holder.calcularTotalItem()
            holder.updateCantidad(model.cantidad_producto)
            holder.updateSubtotal(model.subtotal)
            itent.putExtra("iTotal",  total)
            context.startActivity(itent)
        }

        disminuir.setOnClickListener {
            var total = holder.disminuirTotalItem()
            holder.updateCantidad(model.cantidad_producto)
            holder.updateSubtotal(model.subtotal)
            itent.putExtra("iTotal",   total)
            context.startActivity(itent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var precio_aumento: Double = 0.0
        var total_sum: Double = 0.0
        var total_pagar: Double = 0.0
        val subtotal: TextView = itemView.findViewById(R.id.txv_precio_c)
        val precio_unitario: TextView = itemView.findViewById(R.id.txv_precio_u)
        val cantidad: TextView = itemView.findViewById(R.id.txv_cantidad_producto)
        fun bindItems(model: ProductosCarritoDto) {
            val producto: TextView = itemView.findViewById(R.id.txv_nombre_producto_c)

            producto.text = model.nombre_producto
            precio_unitario.text = "Precio Unitario: $${model.precio_producto}"
            cantidad.text = "Cantidad: ${model.cantidad_producto}"
            subtotal.text = "Subtotal: $${model.subtotal}"

        }

        fun aumentarPrecio(): Double {
            val model = itemList[position]
            if(model.cantidad_producto>1){
                model.subtotal=model.subtotal-(model.subtotal-(model.subtotal/model.cantidad_producto))
            }
            precio_aumento=0.0
            model.cantidad_producto = model.cantidad_producto + 1
            cantidad.text = "Cantidad: ${model.cantidad_producto}"
            precio_aumento = model.precio_producto*model.cantidad_producto
            subtotal.text = "Subtotal: $${precio_aumento}"
            updateCantidad(model.cantidad_producto)
            updateSubtotal(precio_aumento)
            Log.i("resultado-total", "precioaumento${model.precio_producto}")
            return precio_aumento
        }

        fun disminuirPrecio(): Double {
            val model = itemList[position]
            if(model.cantidad_producto>1 && model.subtotal!=0.0) {
                model.subtotal=model.subtotal-(model.subtotal-(model.subtotal/model.cantidad_producto))
                model.cantidad_producto = model.cantidad_producto - 1
                cantidad.text = "Cantidad: ${model.cantidad_producto}"
                precio_aumento = model.precio_producto * model.cantidad_producto
                subtotal.text = "Subtotal: $${precio_aumento}"
                updateCantidad(model.cantidad_producto)
                updateSubtotal(precio_aumento)
                Log.i("resultado-total", "${total_sum}")
            }else{
                model.subtotal = model.precio_producto
                subtotal.text = "Subtotal: $${model.subtotal}"
                model.cantidad_producto=1
            }
            return precio_aumento
        }

        fun calcularTotal(): Double {
            for (i in 0 until itemList.size) {
                val productos_items= itemList[i]
                var price: Double = productos_items.subtotal
                total_pagar += price
            }
            total_pagar.toString()
            Log.i("resultado-total", "${total_pagar}")
            return total_pagar
        }

        fun calcularTotalItem(): Double {
            total_pagar=20.0
            val model = itemList[position]
            model.subtotal=aumentarPrecio()
            for (i in 0 until itemList.size) {
                val productos_items= itemList[i]
                var price: Double = productos_items.subtotal
                total_pagar += price
            }
            total_pagar.toString()
            Log.i("resultado-total", "totalpagar${total_pagar}")
            return total_pagar
        }

        fun disminuirTotalItem(): Double {
            total_pagar=20.0
            val model = itemList[position]
            model.subtotal=disminuirPrecio()
            for (i in 0 until itemList.size) {
                val productos_items= itemList[i]
                var price: Double = productos_items.subtotal
                total_pagar += price
            }
            total_pagar.toString()
            Log.i("resultado-total", "totalpagar${total_pagar}")
            return total_pagar
        }

        fun updateSubtotal(subtotal: Double){
            val position = itemList[adapterPosition]
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuarioLocal = instanciaAuth.currentUser
            val db = FirebaseFirestore.getInstance()
            db.collection("Carritos")
                .document("carrito_${usuarioLocal!!.email.toString()}")
                .collection("carrito_${usuarioLocal!!.email.toString()}")
                .document(position.imageId)
                .update("Subtotal",subtotal )
                .addOnSuccessListener {
                    Log.i("recycler-view", "Eliminado correctamente")
                }.addOnFailureListener {
                    Log.i("recycler-view", "fallo")
                }
        }


        fun updateCantidad(cantidad: Int){
            val position = itemList[adapterPosition]
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuarioLocal = instanciaAuth.currentUser
            val db = FirebaseFirestore.getInstance()
            db.collection("Carritos")
                .document("carrito_${usuarioLocal!!.email.toString()}")
                .collection("carrito_${usuarioLocal!!.email.toString()}")
                .document(position.imageId)
                .update("Cantidad",cantidad)
                .addOnSuccessListener {
                    Log.i("recycler-view", "Eliminado correctamente")
                }.addOnFailureListener {
                    Log.i("recycler-view", "fallo")
                }
        }
        fun popupMenus(v:View) {
            val position = itemList[adapterPosition]
            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.menuproducto)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.eliminar_producto->{
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setMessage("Esta seguro de eliminar este elemento")
                            .setPositiveButton("Si"){
                                    dialog,_->
                                itemList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context,"Elemento eliminado exitosamente ${position.imageId}",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                val instanciaAuth = FirebaseAuth.getInstance()
                                val usuarioLocal = instanciaAuth.currentUser
                                val db = FirebaseFirestore.getInstance()
                                db.collection("Carritos")
                                    .document("carrito_${usuarioLocal!!.email.toString()}")
                                    .collection("carrito_${usuarioLocal!!.email.toString()}")
                                    .document(position.imageId)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.i("recycler-view", "Eliminado correctamente")
                                    }.addOnFailureListener {
                                        Log.i("recycler-view", "fallo")
                                    }
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

}