package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_anuncios.*
import kotlinx.android.synthetic.main.fragment_reportes_mes.*
import mx.tec.bancoalimentos.*
import mx.tec.bancoalimentos.adapters.NewsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAnuncios.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAnuncios : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : NewsAdapter

    //Inicializar viewModel #by lazy(Inicializarlo cuando lo necesito)
    private val viewModel by lazy{ ViewModelProvider(this).get(NewsViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_anuncios, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = NewsAdapter(this)
        recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())

        if (recyclerViewNews != null){
            recyclerViewNews.adapter = adapter
        }
        //Adapter(Context)
        //??De d??nde estamos mandando la informaci??n?

        observeData()
    }

    private fun observeData(){
        viewModel.fetchNewsData().observe(viewLifecycleOwner, Observer {
            adapter.setListNews(it)
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentAnuncios.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAnuncios().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}