package com.example.centrocomercialonline.dto

data class BProductosFirebase(
    var id_tienda: String = "",
    var nombre_producto: String = "",
    var precio_producto: String = "",
    var descripcion_producto: String = "",
    var nombre_tienda: String = "",
    var descripcion_categoria: String ="",
    var imageName: String =""
)