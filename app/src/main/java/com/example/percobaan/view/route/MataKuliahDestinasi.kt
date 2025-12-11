package com.example.percobaan.view.route

interface MataKuliahDestinasi {
    val route: String
}

object ListMataKuliah : MataKuliahDestinasi {
    override val route = "list_matakuliah"
}

object EntryMataKuliah : MataKuliahDestinasi {
    override val route = "entry_matakuliah"
}

object EditMataKuliah : MataKuliahDestinasi {
    override val route = "edit_matakuliah"
    const val argId = "id"
    val routeWithArgs = "$route/{$argId}"
}

object DetailMataKuliah : MataKuliahDestinasi {
    override val route = "detail_matakuliah"
    const val argId = "id"
    val routeWithArgs = "$route/{$argId}"
}
