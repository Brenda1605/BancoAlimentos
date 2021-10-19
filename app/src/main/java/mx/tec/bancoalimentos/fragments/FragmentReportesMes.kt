package mx.tec.bancoalimentos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_reportes_mes.*
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.ReportViewModel
import mx.tec.bancoalimentos.ReportsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentReportesMes.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentReportesMes : Fragment(), ReportsAdapter.OnReportClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : ReportsAdapter

    //Inicializar viewModel #by lazy(Inicializarlo cuando lo necesito)
    private val viewModel by lazy{ ViewModelProvider(this).get(ReportViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_reportes_mes, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = ReportsAdapter(requireContext(), this)
        //Scroll recyclerView
        //Log.d("abis", "onCreate:  ${recyclerViewReports.layoutManager.toString()} ")
        //recyclerViewReports.layoutManager = LinearLayoutManager(requireContext())

        if (recyclerViewReports != null){
            recyclerViewReports.adapter = adapter

        }
        //Adapter(Context)
        //¿De dónde estamos mandando la información?

        observeData()
    }
    private fun observeData(){
        viewModel.fetchReportData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(date: String, img1: String, img2: String, img3: String) {
        val intent = Intent(requireContext(), ActivityAlbum::class.java)
        intent.putExtra("img1", img1)
        intent.putExtra("img2", img2)
        intent.putExtra("img3", img3)
        intent.putExtra("date", date)

        requireActivity().startActivity(intent)
        //Toast.makeText(this, "aaa $date", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentReportesMes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentReportesMes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}