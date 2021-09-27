package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AgregarTarjeta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_tarjeta)

        val numero = findViewById<EditText>(R.id.et_numero_tarjeta)
        val nombre = findViewById<EditText>(R.id.ed_nombre_propietario)
        val fecha = findViewById<EditText>(R.id.ed_fecha_expiracion)
        val codigo = findViewById<EditText>(R.id.ed_codigo_seguridad)

        val carito = findViewById<Button>(R.id.btn_guardar_tarjeta)
        carito.setOnClickListener {
            irActividad(MetodoPago::class.java)
            val nuevaTarjeta = hashMapOf<String, Any>(
                "NumeroTarjeta" to numero.text.toString(),
                "NombreTitular" to nombre.text.toString(),
                "FechaExpiracion" to fecha.text.toString(),
                "CodigoTarjeta" to codigo.text.toString()
            )

            val db = Firebase.firestore
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuarioLocal = instanciaAuth.currentUser
            val referencia = db.collection("Tarjetas")
            referencia //"tarjeta_${usuarioLocal!!.email.toString()}"
                .document("tarjeta_${usuarioLocal!!.email.toString()}")
                .collection("tarjeta_${usuarioLocal!!.email.toString()}")
                .document(nombre.text.toString())
                .set(nuevaTarjeta)
                .addOnSuccessListener {
                    Log.i("firestore-persona", "Valio tarjeta")
                }
                .addOnFailureListener {
                    Log.i("firestore-persona", "no se pudo cargar los datos al firestore ")
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