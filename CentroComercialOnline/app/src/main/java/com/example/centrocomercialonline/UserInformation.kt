package com.example.centrocomercialonline


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.centrocomercialonline.dto.BAuthUsuario.Companion.usuario
import com.example.centrocomercialonline.dto.UsuarioDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        val botonIrRegistrarse = findViewById<Button>(
            R.id.registrarse2
        )
        botonIrRegistrarse
            .setOnClickListener {
                crearUsuario()
                irActividad(Tiendas::class.java)
            }

        //setup
        val bundle = intent.extras
        val email =bundle?.getString("email")
        setup(email?: "")

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.apply()
    }

    private fun setup(correo: String) {
        val email = findViewById<TextView>(R.id.emailcapturado)
        email.text = correo
    }


    fun crearUsuario(){
        val editTextEmail = findViewById<TextView>(R.id.emailcapturado)
        val editTextNombreUsuario = findViewById<EditText>(R.id.username)
        val editTextFechaNacimiento = findViewById<EditText>(R.id.fechanacimiento)
        val editTextTelefono = findViewById<EditText>(R.id.telefono)

            val nuevoUsuario = hashMapOf<String,Any>(
                "Nombre" to editTextNombreUsuario.text.toString(),
                "Email" to editTextEmail.text.toString(),
                "Fecha de Nacimiento" to editTextFechaNacimiento.text.toString(),
                "Teléfono" to editTextTelefono.text.toString()
        )
            val db = Firebase.firestore
        val identificadorUsuario = editTextEmail.text.toString()

            val referencia = db.collection("usuarios").get()
        referencia.addOnSuccessListener{
                for (document in it) {
                    var usuario = document.toObject(UsuarioDto::class.java)
                    usuario.email = document.id
                    if (document.id != identificadorUsuario) {
                        db.collection("usuarios")
                            .document(identificadorUsuario)
                            .set(nuevoUsuario)
                            .addOnSuccessListener {
                                Log.i("firebase-firestore", "Se creó el usuario con éxito")
                            }
                            .addOnFailureListener {
                                Log.i("firebase-firestore", "Falló: $it")
                            }
                    }else{
                        irActividad(Tiendas::class.java)
                        Log.i("firebase-firestore", "Falló: $it")
                    }
                }
            }
            .addOnFailureListener{

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