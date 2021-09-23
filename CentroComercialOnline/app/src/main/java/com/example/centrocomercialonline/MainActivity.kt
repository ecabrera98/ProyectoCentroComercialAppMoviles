package com.example.centrocomercialonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIrRegistrarse = findViewById<Button>(
            R.id.sign_up
        )
        botonIrRegistrarse
            .setOnClickListener { irActividad(Registrar::class.java) }

        val botonIrIniciarSesion = findViewById<Button>(
            R.id.sign_in
        )
        botonIrIniciarSesion
            .setOnClickListener { irActividad(LoginPage::class.java) }
        session()
    }


    private fun session(){
        val registrar = findViewById<ConstraintLayout>(R.id.mainActivity)
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        if(usuarioLocal != null){
            irActividad(Tiendas::class.java)
        }
        else{
            registrar.visibility = View.VISIBLE
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
