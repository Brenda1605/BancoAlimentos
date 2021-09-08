package mx.tec.bancoalimentos.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.MainActivity
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.RecyclerAdapter





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentReportes.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentReportes : Fragment(), View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var datos:ArrayList<String>

    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        datos = ArrayList()

        datos.add("01/09/21")
        datos.add("01/08/21")
        datos.add("01/07/21")
        datos.add("01/06/21")
        datos.add("01/05/21")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_reportes, container, false)
        recyclerView = view.findViewById(R.id.reports_recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = RecyclerAdapter(datos, this)
        var linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.VERTICAL

        recyclerView.layoutManager = linearLayout
        recyclerView.adapter = adapter


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentReportes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentReportes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(row: View) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer, FragmentReporteAlbum())
        transaction?.commit()
    }
}