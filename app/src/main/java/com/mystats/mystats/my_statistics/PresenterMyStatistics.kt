package com.mystats.mystats.my_statistics

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import kotlin.coroutines.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mystats.mystats.AdapterRecord
import com.mystats.mystats.rowsData.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

// ! не работают режимы анфокуса. Сортировка записей по дате создания
@InjectViewState
class PresenterMyStatistics() : MvpPresenter<MvpViewMyStatistics>(),
    InterfaceWithNewRecord,
    InterfaceWithSettingsStats,
    InterfaceWithCreatingNewStats{
    private  var columns : NoteStats? = null
    private lateinit var preferences : SharedPreferences
    private  var sizeStat : Int = 0

    private  var lastAction : Int = 0
    private  var nameStat : String? = null
    private  var recyclerData =  ArrayList<NoteStats>()
    private  var recyclerAdapter : AdapterRecord;
    private var Started : Boolean = false;

    override fun attachView(view: MvpViewMyStatistics?) {
        super.attachView(view)
    }

    public fun setPreferences(pref : SharedPreferences){
        preferences = pref;
    }

    init {
        recyclerAdapter = AdapterRecord(recyclerData,false)
    }

    public fun setRootInAdapter(root : ViewGroup){
        recyclerAdapter.setRoot(root)
    }

    public fun setDataInAdapter(adapter : AdapterRecord){
        adapter.setArrayData(recyclerData)
    }

    public  fun getDataFromStats(name : String?, clearColumns : Boolean) = GlobalScope.launch(Dispatchers.Unconfined) {
            //todo использую диспетчер, который работает вместе с главным потоком. Правильно ли это?
        if (name!=null){
            nameStat = name
        }
        viewState.showLoading();
        recyclerData = ArrayList<NoteStats>()
        if (clearColumns){
                columns = null
            }
            if (columns == null || (columns != null && columns?.data?.isEmpty() == true)) {
                columns = getColumnsFromStats(nameStat!!);
            }
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(nameStat!!).collection("DATA")
                //todo возможности разной сортировки
            .orderBy("FIRESTORE_DATESTAMP_CREATE", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { snap ->

                    for (i: Int in 0..snap.size() - 1) {
                        //выполняем глубокое копирование, чтобы в columns не хранились данные
                        val noteData  = (columns!!.data.map { it.clone() }) as ArrayList<RowStat>

                        //сохраняем данные в записи и запись добавляем в общий массив записей
                        for (j: Int in 0..noteData.size - 1) {
                            noteData[j].setData(snap.documents[i].get(noteData[j].getNameRow()))
                        }
                        recyclerData.add(NoteStats(noteData, snap.documents[i].id))
                    }
                    viewState.clearRecycler()
                    recyclerAdapter.setNewData(recyclerData)
                    if (recyclerData.size ==0){
                        viewState.showEmptyStats()
                    } else{
                        recyclerAdapter.notifyDataSetChanged()
                        viewState.showDataLayout()
                    }
                }.addOnFailureListener {
                    //todo обработка ошибок
                }
    }
     suspend  fun getColumnsFromStats(name : String) : NoteStats? {
         return suspendCoroutine { continuation ->
             FirebaseFirestore.getInstance().collection("Users")
                 .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                 .collection("STATS").document(name)
                 .collection("COLUMNS").document("COLUMNS").get()
                 .addOnSuccessListener { doc ->
                     val names = doc.get("NAMES") as List<String>
                     val types = doc.get("TYPES") as List<Int>
                     val columns = ArrayList<RowStat>()
                     for (i: Int in 0..types.size - 1) {

                         when (types[i]) {
                             RowStat.TYPE_STRING -> {
                                 val col = StringRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_INT -> {
                                 val col = NumberRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_DATE -> {
                                 val col = DateRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_STATE -> {
                                 val col = StateRowStat(names[i], null)
                                 columns.add(col)
                             }
                         }
                     }
                    continuation.resume(NoteStats(columns, null))
                 }.addOnFailureListener {
                     Log.d("FIRESTORE", "ops")
                 }
         }
     }


    fun saveLastStat(name : String?){
        if (name==null){
            preferences.edit().putString("LastStatName",null).apply()
        } else{
            preferences.edit().putString("LastStatName",name).apply()
        }
    }
    fun loadLastStat() : String?{
        nameStat = preferences.getString("LastStatName", null);
        return nameStat
    }
    fun updateMenuNamesStats(){
        //todo сохранять их в презентер, чтобы не делать лишний запрос
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").get().addOnSuccessListener { snap ->

                    val data = ArrayList<String>()
                    for(i : Int in 0..snap.size()-1){
                        data.add(snap.documents[i].id)
                    }
                    viewState.addNamesStatsInSubMenu(data)
                }
    }

    public fun addRecordInRecycler(data : NoteStats){
        this.recyclerData.add(0,data)
        recyclerAdapter.setNewData(recyclerData);
        viewState.showDataLayout()
    }

    //Правильно ли?
    fun getRecyclerAdapter(): AdapterRecord {
        return recyclerAdapter
    }


    public fun goToNewRecord() {
        //todo нужно ли нам будет знать о том, какое имя у документа с записью?
        viewState.navigateToNewRecord(Bundle().also {
            it.putSerializable("MS", this as InterfaceWithNewRecord)
            it.putInt("SIZESTAT",sizeStat)
            it.putSerializable("COLUMNS", (NoteStats((columns!!.data.map { it.clone() } as ArrayList<RowStat>),null)))
            it.putString("NAMESTAT",nameStat)
        });
    }


    fun goToSettings() {
        viewState.navigateToSettings(Bundle().also {
            it.putSerializable("MS", this as InterfaceWithSettingsStats)
            it.putString("NameStat",nameStat)
        })
    }

    fun newStatsWasCreated(nameStat: String?, columns: NoteStats) {
        recyclerData.clear()
        this.nameStat = nameStat
        this.columns = columns
        saveLastStat(nameStat)
        viewState.showEmptyStats()
        viewState.changeTitleName(nameStat)

    }

    fun appWasStarted() {
        if (!Started) {
            Started = true
            nameStat = loadLastStat()
            if (nameStat == null) {
                viewState.showNewStats()
            } else {
                viewState.changeTitleName(nameStat)
                this.getDataFromStats(null, false)
            }
        }
    }
    fun getNameStat() : String? {
        return nameStat;
    }

    override fun newStatsWasCreated() {
        lastAction = NEW_STATS_WAS_CREATED;
    }

    override fun addNewRecord() {
        lastAction = ADD_NEW_RECORD;
    }

    override fun statsWasDelete() {
        lastAction = SETTINGS_STATS_WAS_DELETE;
    }

    fun initActionFromLastFragment(){
        viewState.doActionAfterFragment(lastAction)
        lastAction = NOTHING;
    }

    fun goToNewStats() {
        viewState.navigateToCreatingNewStats(Bundle().also {
            it.putSerializable("MS", this as InterfaceWithCreatingNewStats)
        })
    }

    companion object{
        const val ADD_NEW_RECORD = 1;
        const val SETTINGS_STATS_WAS_DELETE = 2;
        const val NEW_STATS_WAS_CREATED = 3;
        const val NOTHING = -1;
    }
}