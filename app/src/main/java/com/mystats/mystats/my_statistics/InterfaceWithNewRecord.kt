package com.mystats.mystats.my_statistics

import android.os.Parcelable
import java.io.Serializable

//интерфейс - мост между fragments myStats and NewRecord
interface InterfaceWithNewRecord : Serializable {
    fun addNewRecord();

}