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
import com.example.centrocomercialonline.dto.ProductosDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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

        var mMenus: RelativeLayout = holder.itemView.findViewById(R.id.relativeLayoutcarrito)
        mMenus.setOnClickListener {
            holder.popupMenus(it)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindItems(model: ProductosDto) {
            val producto: TextView = itemView.findViewById(R.id.txv_nombre_producto)
            val precio: TextView = itemView.findViewById(R.id.txv_precio)

            producto.text = model.nombre_producto
            precio.text = model.precio_producto.toString()

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
                                val db = FirebaseFirestore.getInstance()
                                db.collection("carrito")
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
