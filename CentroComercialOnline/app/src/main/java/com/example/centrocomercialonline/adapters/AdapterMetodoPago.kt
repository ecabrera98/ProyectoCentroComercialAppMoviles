package com.example.centrocomercialonline.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.R
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.example.centrocomercialonline.dto.TarjetaDto
import com.google.firebase.firestore.FirebaseFirestore

class AdapterMetodoPago(private val itemList : ArrayList<TarjetaDto>, val context : Context) : RecyclerView.Adapter<AdapterMetodoPago.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_tarjeta, parent, false)

        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = itemList[position]
        holder.bindItems(itemList[position])

        var mMenus: RelativeLayout = holder.itemView.findViewById(R.id.rl_tarjeta)
        mMenus.setOnClickListener {
            holder.popupMenus(it)
        }


    }


    override fun getItemCount(): Int {
        return itemList!!.size
    }

    inner class MyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        fun bindItems(model: TarjetaDto) {
            val numeroTarjeta: TextView = itemView.findViewById(R.id.txv_numero_tarjeta)
            val nombreTitular: TextView = itemView.findViewById(R.id.txv_nombre_tarjeta)
            val fechaExpiracion: TextView = itemView.findViewById(R.id.txv_fecha_expiración)

            numeroTarjeta.text = model.numeroTarjeta
            nombreTitular.text = model.nombreTitular
            fechaExpiracion.text = model.fechaExpiracion

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
                                Toast.makeText(context,"Elemento eliminado exitosamente ${position.nombreTitular}",
                                    Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                val db = FirebaseFirestore.getInstance()
                                db.collection("carrito")
                                    .document(position.nombreTitular.toString())
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