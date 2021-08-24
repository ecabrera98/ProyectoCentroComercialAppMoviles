package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val botonIrRegistrarse = findViewById<Button>(
            R.id.login1
        )

        var email = findViewById<EditText>(R.id.email1)
        var password = findViewById<EditText>(R.id.password1)

        botonIrRegistrarse
            .setOnClickListener {
                if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showUserInformation(
                                    it.result?.user?.email ?: "",
                                    ProviderType.BASIC
                                )
                            } else {
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
        val UserInformationIntent: Intent = Intent(this, Tiendas::class.java).apply {
            putExtra("email",email)
            //putExtra("password",password)
            putExtra("provider",provider.name)
        }
        startActivity(UserInformationIntent)
    }

}