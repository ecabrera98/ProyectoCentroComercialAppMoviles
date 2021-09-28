package com.example.centrocomercialonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.centrocomercialonline.adapters.AdapterComprar
import com.example.centrocomercialonline.dto.ProductosCarritoDto
import com.example.centrocomercialonline.dto.UsuariosDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.hashMapOf as hashMapOf

class Comprar : AppCompatActivity(){
    private lateinit var adaptadorComprar: AdapterComprar
    private lateinit var productoArrayList : ArrayList<ProductosCarritoDto>
    private lateinit var recyclerView: RecyclerView
    var arregloUsuarios = arrayListOf<UsuariosDto>()

    private val title by lazy { findViewById<TextView>(R.id.title1) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu1) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)

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
        recyclerView = findViewById(R.id.rcv_pedido)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf()
        adaptadorComprar = AdapterComprar(productoArrayList,this)

        recyclerView.adapter = adaptadorComprar

        cargarProductoCarrito()
        uploadPersonas()
        val botonTarjeta = findViewById<Button>(R.id.btn_metodo_pago)
        botonTarjeta.setOnClickListener {
            irActividad(MetodoPago::class.java)
        }

        val botonComprar = findViewById<Button>(R.id.btn_comprar_pedido)
        botonComprar.setOnClickListener {
            guardarDatosPedido()
            guardarDatosCarrito()
            eliminarProductocCarrito()
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_pedido)
        botonCancelar.setOnClickListener {
            irActividad(Carrito::class.java)
        }
    }

    fun calcularTotal() : Double{
        var total_pagar = 20.0
        for (i in 0 until productoArrayList.size) {
            val productos_items= productoArrayList[i]
            var price: Double = productos_items.subtotal
            total_pagar += price
        }
        Log.i("resultado-total", "CC${total_pagar}")
        return total_pagar
    }


    fun cargarProductoCarrito(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        db.collection("Carritos")
            .document("carrito_${usuarioLocal!!.email.toString()}")
            .collection("carrito_${usuarioLocal!!.email.toString()}")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(ProductosCarritoDto::class.java)
                    producto!!.imageId = document.get("Imagen").toString()
                    producto!!.nombre_producto = document.get("NombreProducto").toString()
                    producto!!.precio_producto = document.getDouble("Precio")!!
                    producto!!.cantidad_producto = document.getLong("Cantidad")!!.toInt()
                    producto!!.subtotal = document.getDouble("Subtotal")!!
                    productoArrayList.add(producto)
                    val totalPagar = findViewById<TextView>(R.id.tv_TotalPago)
                    totalPagar.text = calcularTotal().toString()
                    adaptadorComprar?.notifyDataSetChanged()
                }

            }
            .addOnFailureListener {
                Log.i("CARRITO", "FALLO")
            }

    }


    fun guardarDatosCarrito(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        db.collection("Carritos")
            .document("carrito_${usuarioLocal!!.email.toString()}")
            .collection("carrito_${usuarioLocal!!.email.toString()}")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(ProductosCarritoDto::class.java)
                    producto!!.imageId = document.get("Imagen").toString()
                    producto!!.nombre_producto = document.get("NombreProducto").toString()
                    producto!!.precio_producto = document.getDouble("Precio")!!
                    producto!!.cantidad_producto = document.getLong("Cantidad")!!.toInt()
                    producto!!.subtotal = document.getDouble("Subtotal")!!
                    productoArrayList.add(producto)
                    val totalPagar = findViewById<TextView>(R.id.tv_TotalPago)
                    totalPagar.text = calcularTotal().toString()
                    adaptadorComprar?.notifyDataSetChanged()
                    guardarProductosCarrito(producto!!.imageId,producto!!.nombre_producto,
                        producto!!.precio_producto,  producto!!.cantidad_producto, producto!!.subtotal)
                }

            }
            .addOnFailureListener {
                Log.i("CARRITO", "FALLO")
            }

    }

    fun eliminarProductocCarrito(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val db = Firebase.firestore
        db.collection("Carritos")
            .document("carrito_${usuarioLocal!!.email.toString()}")
            .collection("carrito_${usuarioLocal!!.email.toString()}")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var producto = document.toObject(ProductosCarritoDto::class.java)
                    producto!!.imageId = document.get("Imagen").toString()
                    producto!!.nombre_producto = document.get("NombreProducto").toString()
                    producto!!.precio_producto = document.getDouble("Precio")!!
                    producto!!.cantidad_producto = document.getLong("Cantidad")!!.toInt()
                    producto!!.subtotal = document.getDouble("Subtotal")!!
                    productoArrayList.add(producto)
                    vaciarCarrito()
                }

            }
            .addOnFailureListener {
                Log.i("CARRITO", "FALLO")
            }

    }

    fun guardarDatosPedido(){
        val total = findViewById<TextView>(R.id.tv_TotalPago)
        val intent = intent
        val totalPago = intent.getDoubleExtra("iTotal",0.0)
        Log.i("resultado-total", "preciorecibido${totalPago}")
        var ingresoPago: Double = totalPago
        Log.i("resultado-total", "preciorecibido${ingresoPago}")
        total.text = "$${ingresoPago}"
        val db1 = Firebase.firestore
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val fecha = Date()
        val currentDate = sdf.format(Date())
        val nuevoPedido = hashMapOf<String, Any>(
                "Fecha Envío Pedido" to currentDate,
                "Total a Pagar" to ingresoPago
            )
        db1.collection("Pedidos")
            .document(usuarioLocal!!.email.toString())
            .collection("Datos_Pedido_${fecha}")
            .document(fecha.toString())
            .set(nuevoPedido)
            .addOnSuccessListener {
                Log.i("firestore-pedido", "Valio pedido")
            }
            .addOnFailureListener {
                Log.i("firestore-pedido", "no se pudo cargar los datos al firestore ")
            }
            irActividad(Tiendas::class.java)
    }

    fun uploadPersonas(){
        val dbu = Firebase.firestore
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val editTextNombreUsuario = findViewById<TextView>(R.id.tv_nombre_cliente_seteado)
        val editTextTelefono = findViewById<TextView>(R.id.tv_telefono_cliente_seteado)
        val editTextEmail = findViewById<TextView>(R.id.tv_email_cliente_seteado)
        val referencia = dbu.collection("usuarios")
        referencia
            .whereEqualTo("Email",usuarioLocal!!.email.toString())
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var user = document.toObject(UsuariosDto::class.java)
                    user!!.email = document.id
                    user!!.nombre = document.get("Nombre").toString()
                    user!!.telefono = document.get("Teléfono").toString()
                    arregloUsuarios.add(user)

                    editTextEmail.text = user.email
                    editTextNombreUsuario.text = user.nombre
                    editTextTelefono.text = user.telefono
                    guardarDatosUsuarioPedido(editTextNombreUsuario.text.toString(),  editTextTelefono.text.toString())
                }
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "no se cargaron los datos al listView")
            }
    }



    fun guardarDatosUsuarioPedido(NombreUsuario: String, TelefonoUsuario: String ){
        val db2 = Firebase.firestore
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val usuarioPedido = hashMapOf<String, Any>(
            "Nombre" to NombreUsuario,
            "Email" to usuarioLocal!!.email.toString(),
            "Teléfono" to TelefonoUsuario,
        )
        db2.collection("Pedidos")
            .document(usuarioLocal!!.email.toString())
            .collection("Datos_${usuarioLocal!!.email.toString()}")
            .document(NombreUsuario)
            .set(usuarioPedido)
            .addOnSuccessListener {
                Log.i("firestore-pedido", "Valio pedido")
            }
            .addOnFailureListener {
                Log.i("firestore-pedido", "no se pudo cargar los datos al firestore ")
            }
    }



    fun guardarProductosCarrito(
        IdP: String,
        NombreP: String,
        PrecioP: Double,
        CantidadP: Int,
        SubtotalP: Double,
    ){
        val db3 = Firebase.firestore
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        val fecha = Date()
        val productosPedido = hashMapOf<String, Any>(
            "Imagen ID" to IdP,
            "Nombre Producto" to NombreP,
            "Precio " to PrecioP,
            "Cantidad de Productos" to CantidadP,
            "Subtotal" to SubtotalP
        )
        db3.collection("Pedidos")
            .document(usuarioLocal!!.email.toString())
            .collection("Datos_Carrito_${fecha}")
            .document(IdP)
            .set(productosPedido)
            .addOnSuccessListener {
                Log.i("firestore-pedido", "Valio pedido")
            }
            .addOnFailureListener {
                Log.i("firestore-pedido", "no se pudo cargar los datos al firestore ")
            }
    }


    fun vaciarCarrito(){
        val db4 = Firebase.firestore
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser
        for (i in 0 until productoArrayList.size) {
            val productos_items= productoArrayList[i]
            db4.collection("Carritos")
                .document("carrito_${usuarioLocal!!.email.toString()}")
                .collection("carrito_${usuarioLocal!!.email.toString()}")
                .document(productos_items.imageId)
                .delete()
                .addOnSuccessListener {
                    Log.i("CARRITO", "Valio eliminar carrito")
                }
                .addOnFailureListener {
                    Log.i("CARRITO", "No Valio eliminar carrito")
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