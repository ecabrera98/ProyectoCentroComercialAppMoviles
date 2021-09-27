package com.example.centrocomercialonline.dto

import android.os.Parcel
import android.os.Parcelable

class TarjetaDto(
    var numeroTarjeta:String = "",
    var nombreTitular: String = "",
    var fechaExpiracion: String = "",
    var codigoTarjeta: String = ""
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(numeroTarjeta)
        parcel.writeString(nombreTitular)
        parcel.writeString(fechaExpiracion)
        parcel.writeString(codigoTarjeta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TarjetaDto> {
        override fun createFromParcel(parcel: Parcel): TarjetaDto {
            return TarjetaDto(parcel)
        }

        override fun newArray(size: Int): Array<TarjetaDto?> {
            return arrayOfNulls(size)
        }
    }

}