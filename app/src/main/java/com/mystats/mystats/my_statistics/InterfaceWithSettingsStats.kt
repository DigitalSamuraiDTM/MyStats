package com.mystats.mystats.my_statistics

import android.os.Parcelable
import java.io.Serializable

//интерфейс - мост между fragments myStats and Settings
interface InterfaceWithSettingsStats : Serializable {
    fun statsWasDelete();
}