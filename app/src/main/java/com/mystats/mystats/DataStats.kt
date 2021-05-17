package com.mystats.mystats

import com.mystats.mystats.rowsData.RowStat

class DataStats() {
    companion object{
        public var data = ArrayList<ArrayList<RowStat>>()
        public var nameStat : String? = null
        public var sizeStat : Int = 0
        public var columns = ArrayList<RowStat>()
    }
}
