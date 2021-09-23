package com.example.centrocomercialonline.dto

import android.os.Parcel
import android.os.Parcelable

data class ProductosDto(
    var imageId:String = "",
    var nombre_producto: String = "",
    var precio_producto: Double = 0.0
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(imageId)
        dest.writeString(nombre_producto)
        dest.writeDouble(precio_producto)
    }

    companion object CREATOR : Parcelable.Creator<ProductosDto> {
        override fun createFromParcel(parcel: Parcel): ProductosDto {
            return ProductosDto(parcel)
        }

        override fun newArray(size: Int): Array<ProductosDto?> {
            return arrayOfNulls(size)
        }
    }

}