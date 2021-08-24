package com.example.centrocomercialonline

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class Registrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        val botonIrRegistrarDatos = findViewById<Button>(R.id.registrarse1)
        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)
        var username = findViewById<EditText>(R.id.username)



        botonIrRegistrarDatos.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty() && username.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString(),
                    password.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showUserInformation(it.result?.user?.email ?: "",ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }

            }
    }
    fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showUserInformation(email:String, provider: ProviderType){
        val UserInformationIntent: Intent = Intent(this, UserInformation::class.java).apply {
            putExtra("email",email)
            //putExtra("password",password)
            putExtra("provider",provider.name)
        }
        startActivity(UserInformationIntent)
    }



}
