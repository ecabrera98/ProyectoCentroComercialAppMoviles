package com.example.centrocomercialonline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class PerfilUsuario : AppCompatActivity() {

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)


        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> irActividad(Tiendas::class.java)  to "Inicio"
                R.id.buscar -> irActividad(BuscarProducto::class.java) to "Buscar"
                R.id.carrito -> irActividad(Carrito::class.java) to "Carrito"
                R.id.perfil -> irActividad(PerfilUsuario::class.java)  to "Perfil"
                else -> R.color.white to ""
            }

            title.text = option.second
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.perfil, 32)
        }

        setearUsuarioFirebase()

        val botonIrMetodosPago= findViewById<TextView>(R.id.textViewMetodosPago)
        botonIrMetodosPago.setOnClickListener {
            irActividad(MetodoPago::class.java)
        }

        val botonActualizarPerfil= findViewById<TextView>(R.id.textViewEditarPerfil)
        botonActualizarPerfil.setOnClickListener {
            irActividad(ActualizarUserInformation::class.java)
        }

        val botonCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        botonCerrarSesion.setOnClickListener{
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
            irActividad(MainActivity::class.java)

        }

    }


    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val editTextEmail = findViewById<TextView>(R.id.txtCorreoUsuario)
        val editTextNombreUsuario = findViewById<TextView>(R.id.txtNombreUsuario)

        if (usuarioLocal != null) {
            if(usuarioLocal.email != null){
                val db = Firebase.firestore
                db.collection("usuarios").document(usuarioLocal.email.toString()).get()
                    .addOnSuccessListener {
                        editTextEmail.setText(it.get("Email") as String?)
                        editTextNombreUsuario.setText(it.get("Nombre") as String?)
                        var imagenName = usuarioLocal.email.toString()
                        val imagen =  findViewById<ImageView>(R.id.imagenPerfil)
                        val storageRef = FirebaseStorage.getInstance().reference.child("perfilesusuarios/$imagenName.jpeg")
                        Glide.with(this)
                            .load(storageRef)
                            .into(imagen)

                        Log.i("firestore-usuarios", "Se estrajó el usuario con éxito $imagenName")
                    }
                    .addOnFailureListener {
                        Log.i("firestore-usuarios", "Falló: $it")
                    }
            }
        }
    }


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