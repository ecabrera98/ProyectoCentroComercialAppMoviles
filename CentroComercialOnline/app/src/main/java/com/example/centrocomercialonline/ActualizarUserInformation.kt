package com.example.centrocomercialonline


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActualizarUserInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_user)
        val botonActualizarUsuario = findViewById<Button>(
            R.id.actualizar
        )

        setearUsuarioFirebase()

        botonActualizarUsuario
            .setOnClickListener {
                actualizarUsuario()
                irActividad(PerfilUsuario::class.java)
            }


    }
    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val editTextEmail = findViewById<TextView>(R.id.emailcapturado1)
        val editTextFecha = findViewById<TextView>(R.id.fechanacimiento1)
        var editTextNombreUsuario = findViewById<EditText>(R.id.username1)
        var editTextTelefono = findViewById<EditText>(R.id.telefono1)
        if (usuarioLocal != null) {
            if(usuarioLocal.email != null){
                val db = Firebase.firestore
                db.collection("usuarios").document(usuarioLocal.email.toString()).get()
                    .addOnSuccessListener {
                        editTextEmail.setText(it.get("Email") as String?)
                        editTextFecha.setText(it.get("Fecha de Nacimiento") as String?)
                        editTextNombreUsuario.setText(it.get("Nombre") as String?)
                        editTextTelefono.setText(it.get("Teléfono") as String?)
                        Log.i("firestore-usuarios", "Se estrajó el usuario con éxito")
                    }
                    .addOnFailureListener {
                        Log.i("firestore-usuarios", "Falló: $it")
                    }
            }
        }
    }


    fun actualizarUsuario(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        setearUsuarioFirebase()
        val editTextEmail = findViewById<TextView>(R.id.emailcapturado1)
        val editTextFecha = findViewById<TextView>(R.id.fechanacimiento1)
        var editTextNombreUsuario = findViewById<EditText>(R.id.username1)
        var editTextTelefono = findViewById<EditText>(R.id.telefono1)

            val usuarioActualizado = hashMapOf<String,Any>(
                "Email" to editTextEmail.text.toString(),
                "Nombre" to editTextNombreUsuario.text.toString(),
                "Fecha de Nacimiento" to editTextFecha.text.toString(),
                "Teléfono" to editTextTelefono.text.toString(),

        )

        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .document(usuarioLocal!!.email.toString())
            .set(
                usuarioActualizado
            )
            .addOnSuccessListener {
                editTextNombreUsuario.clearFocus()
                editTextTelefono.clearFocus()
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