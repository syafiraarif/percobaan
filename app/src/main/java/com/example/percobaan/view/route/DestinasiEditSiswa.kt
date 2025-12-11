package com.example.percobaan.view.route

import com.example.percobaan.R

object DestinasiEditSiswa : DestinasiNavigasi{
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}