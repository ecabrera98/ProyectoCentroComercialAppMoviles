package com.example.centrocomercialonline.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.centrocomercialonline.R
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AdapterCarrito (private val itemList : ArrayList<ProductosCarritoDto>, val context : Context) : RecyclerView.Adapter<AdapterCarrito.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_comprar, parent, false)
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

        var mMenus: RelativeLayout = holder.itemView.findViewById(R.id.relativeLayoutcarrito)
        mMenus.setOnClickListener {
            holder.popupMenus(it)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindItems(model: ProductosCarritoDto) {
            val producto: TextView = itemView.findViewById(R.id.txv_nombre_producto)
            val precio: TextView = itemView.findViewById(R.id.txv_precio_uc)
            val subtotal: TextView = itemView.findViewById(R.id.txv_precio_sc)
            val cantidad: TextView = itemView.findViewById(R.id.txv_cantidad_productoc)

            producto.text = model.nombre_producto
            precio.text = "Precio Unitario: $${model.precio_producto}"
            subtotal.text = "Subtotal: $${model.subtotal}"
            cantidad.text = "Cantidad: ${model.cantidad_producto}"

        }

        fun popupMenus(v:View) {
            val position = itemList[adapterPosition]
            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.menuproducto)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.eliminar_producto->{
                        AlertDialog.Builder(context)
                            .setTitle("Eliminar Productos Carrito")
                            .setMessage("Esta seguro de eliminar este producto")
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
                                    .collection("carrito_${usuarioLocal.email.toString()}")
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
