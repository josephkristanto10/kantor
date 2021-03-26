package com.pemerintah.beaconsampah

import java.io.Serializable
import java.sql.Time

data class datapetugas(var id :String, var namapetugas:String, var handphonepetugas:String, var poolpetugas:String, var harisenin:String, var hariselasa:String, var harirabu:String, var harikamis:String, var harijumat:String, var harisabtu:String, var hariminggu:String) : Serializable