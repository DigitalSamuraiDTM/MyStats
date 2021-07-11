package com.mystats.mystats.rowsData

import java.io.Serializable

data class NoteStats( var data: ArrayList<RowStat>, var noteId: String?) : Serializable {

}