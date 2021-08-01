  package com.mystats.mystats.my_statistics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mystats.mystats.AdapterRecord
import com.mystats.mystats.MainActivity.MainActivity
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.NoteStats
import com.mystats.mystats.rowsData.RowStat
import kotlinx.coroutines.CoroutineScope
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

  //TODO авторизация в несуществующий аккаунт
//findnavcontroller.navigate() - приводит к тому, что цикл начинается с attach
class FragmentMyStatistics : MvpAppCompatFragment(), View.OnClickListener,
      MvpViewMyStatistics{


      //may be DI?
    private lateinit var buttonNewRecord : Button
    private lateinit var buttonEmptyNewRecord : Button
    private lateinit var buttonSearchInStats : ImageButton
    private lateinit var buttonSettingsStats : ImageButton
    private lateinit var buttonNewStats : Button
    private lateinit var recyclerData : RecyclerView
    private lateinit var layoutLoading : ConstraintLayout
    private lateinit var layoutNewStats : ConstraintLayout
    private lateinit var layoutMainData : ConstraintLayout
    private lateinit var layoutNewRecord : ConstraintLayout
    private lateinit var itemStats : MenuItem

        //добавляя аннотацию inject мы получим объект в это поле, вызывая инъекцию
    //провайдер дает отложенную инициализацию объекта. То есть инициализация идет тогда
    //когда мы в первый раз обращаемся к объекту
      @Inject
      lateinit var  presenterProvider : javax.inject.Provider<PresenterMyStatistics>

      val presenter by moxyPresenter { presenterProvider.get() }



      override fun onCreate(savedInstanceState: Bundle?) {
          //инъекция! Насыщаем поля нужными объектами
          MainApplication.getAppComponent().inject(this);
          super.onCreate(savedInstanceState)
      }



      override fun onResume() {
        (activity as MainActivity).EnableBars(true);


          //работаю с презентером в onResume, потому что на этой стадии presenter attach with fragment
          presenter.initActionFromLastFragment();

          when(findNavController().previousBackStackEntry?.destination?.id){
              R.id.fragmentStartApp, R.id.fragmentSignIn->{
                  presenter.appWasStarted()

              }

          }
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_my_statistics, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        buttonEmptyNewRecord = view.findViewById(R.id.fr_myStats_buttonEmptyStats_addRecord)
        buttonEmptyNewRecord.setOnClickListener(this)
        buttonNewRecord = view.findViewById(R.id.fr_myStats_button_newRecord)
        buttonNewRecord.setOnClickListener(this);
        buttonSearchInStats = view.findViewById(R.id.fr_myStats_button_searchInStats)
        buttonSearchInStats.setOnClickListener(this)
        buttonSettingsStats  = view.findViewById(R.id.fr_myStats_button_SettingsStats)
        buttonSettingsStats.setOnClickListener(this)
        buttonNewStats = view.findViewById(R.id.fr_myStats_button_createStats)
        buttonNewStats.setOnClickListener(this)
        layoutLoading  = view.findViewById(R.id.fr_myStats_layout_loading)
        layoutNewStats = view.findViewById(R.id.fr_myStats_layout_new_stats)
        layoutMainData = view.findViewById(R.id.fr_myStats_layout_mainData)
        layoutNewRecord = view.findViewById(R.id.fr_myStats_layout_emptyStats)

        recyclerData = view.findViewById(R.id.fr_myStats_recycler_data)
        recyclerData.adapter = presenter.getRecyclerAdapter()
        presenter.setRootInAdapter(recyclerData)
        presenter.setPreferences(requireActivity().getSharedPreferences("MyStats", Context.MODE_PRIVATE))

        super.onViewCreated(view, savedInstanceState)
    }

    public override fun showLoading(){
        layoutLoading.visibility = View.VISIBLE
        layoutMainData.visibility = View.GONE
        layoutNewStats.visibility = View.GONE
        layoutNewRecord.visibility = View.GONE
    }


    public override fun showDataLayout(){
        layoutLoading.visibility = View.GONE
        layoutMainData.visibility = View.VISIBLE
        layoutNewStats.visibility = View.GONE
        layoutNewRecord.visibility = View.GONE
        recyclerData.scheduleLayoutAnimation()


    }
    public override fun showNewStats(){
        layoutLoading.visibility = View.GONE
        layoutMainData.visibility = View.GONE
        layoutNewStats.visibility = View.VISIBLE
        layoutNewRecord.visibility = View.GONE

    }
    public override fun showEmptyStats(){
        layoutLoading.visibility = View.GONE
        layoutMainData.visibility = View.GONE
        layoutNewStats.visibility = View.GONE
        layoutNewRecord.visibility = View.VISIBLE
    }



      override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.fr_myStats_button_createStats ->{
                (activity as MainActivity).EnableBars(false)
                presenter.goToNewStats()
            }
            R.id.fr_myStats_button_newRecord, R.id.fr_myStats_buttonEmptyStats_addRecord ->{
                presenter.goToNewRecord()
                (activity as MainActivity).EnableBars(false)
            }
            R.id.fr_myStats_button_SettingsStats ->{
                presenter.goToSettings();
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_stats,menu)
        itemStats = menu.findItem(R.id.item_myStats_myStats);

        super.onCreateOptionsMenu(menu, inflater)
        presenter.updateMenuNamesStats()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_myStats_newStats->{
                (activity as MainActivity).EnableBars(false)
                presenter.goToNewStats();
            }
            R.id.item_myStats_settings->{
            }
            R.id.item_myStats_myStats ->{
                val subMenuSize = item.subMenu.size()
                if (subMenuSize==0){
                    Toast.makeText(requireContext(),"Somehow it's empty here", Toast.LENGTH_SHORT).show()
                }
            }
            else ->{
                //submenu выбор статистики
                requireActivity().setTitle(item.title.toString())
                presenter.saveLastStat(item.title.toString())
                presenter.getDataFromStats(item.title.toString(), true)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    fun addNamesStatsInSubMenu(name : String){
        itemStats.subMenu.add(0,0,0,name)
    }

    override fun  addNamesStatsInSubMenu(names : ArrayList<String>){
        val subMenu = itemStats.subMenu
        for(i : Int in 0..names.size-1){
            subMenu.add(0,0,0,names[i])
        }
    }

      override fun updateRecyclerAdapter(adapter: AdapterRecord) {
          recyclerData.adapter = adapter;
      }

      override fun clearRecycler() {
        recyclerData.removeAllViews()
    }

      override fun changeTitleName(nameStat: String?) {
        requireActivity().setTitle(nameStat)
    }

      override fun navigateToNewRecord(bundle: Bundle) {
          findNavController().navigate(R.id.action_myStatistics_to_fragmentNewRecord,bundle)
      }

      override fun navigateToSettings(bundle: Bundle) {
          findNavController().navigate(R.id.action_myStatistics_to_fragmentSettingsStats, bundle);
      }

      override fun navigateToCreatingNewStats(bundle: Bundle) {
          findNavController().navigate(R.id.action_myStatistics_to_fragmentTemplatesStats, bundle);
      }

      override fun doActionAfterFragment(numAction: Int) {
        when(numAction){
            PresenterMyStatistics.ADD_NEW_RECORD->{
                presenter.addRecordInRecycler(arguments?.getSerializable("NOTE") as NoteStats)
            }
            PresenterMyStatistics.SETTINGS_STATS_WAS_DELETE->{
                showNewStats();
                requireActivity().setTitle("MyStats")
                presenter.saveLastStat(null);
                presenter.updateMenuNamesStats()
            }
            PresenterMyStatistics.NEW_STATS_WAS_CREATED->{
                presenter.newStatsWasCreated(arguments?.getString("NAME"),arguments?.getSerializable("COLUMNS") as NoteStats )
                showEmptyStats()
                //todo после прихода от создания статистик не нужно с нуля делать запрос, нужно просто добавить только что созданную
                //addNamesStatsInSubMenu(nameStats!!)

            }
        }
      }




  }