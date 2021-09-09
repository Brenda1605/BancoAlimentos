package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.adapters.FoodAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var foodAdapter: FoodAdapter
    lateinit var rvFood: RecyclerView
    lateinit var foodManager: RecyclerView.LayoutManager
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.tbHome)
        toolbar.inflateMenu(R.menu.menu_search)
        val dataSet = arrayOf<String>("Frijoles", "Lentejas", "Soya", "Arroz", "Leche", "Harina",
            "Queso", "Agua", "Maiz", "Manzanas", "Guayabas")
        rvFood = view.findViewById(R.id.rvFood)
        foodAdapter = FoodAdapter(dataSet)
        foodManager = GridLayoutManager(context,2)
        rvFood.adapter = foodAdapter
        rvFood.layoutManager = foodManager

        val donateBtn: Button? = view?.findViewById(R.id.home_donteBtn)
        donateBtn?.setOnClickListener(this)

        /*Codigo aquí*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onClick(btn: View?) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer, FragmentDonateOptions())
        transaction?.commit()
    }
}
