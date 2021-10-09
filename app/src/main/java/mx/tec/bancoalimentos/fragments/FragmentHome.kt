package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.adapters.FoodAdapter
import mx.tec.bancoalimentos.adapters.SelectedAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHome : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var foodAdapter: FoodAdapter
    lateinit var rvFood: RecyclerView
    lateinit var foodManager: RecyclerView.LayoutManager
    lateinit var toolbar: Toolbar
    private lateinit var jsonRequest: JsonObjectRequest
    private lateinit var queue : RequestQueue
    private lateinit var data : JSONArray
    private lateinit var selected: MutableList<JSONObject>

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

        val donateBtn: Button? = view?.findViewById(R.id.home_donteBtn)
        donateBtn?.setOnClickListener(this)

        queue = Volley.newRequestQueue(context)

        val url = "https://firestore.googleapis.com/v1/projects/bancoalimentos-7964f/databases/(default)/documents/products/"

        jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("Request", response.getJSONArray("documents").getJSONObject(0).getJSONObject("fields").getJSONObject("name").getString("stringValue").toString())
                data = response.getJSONArray("documents")
                selected = ArrayList()
                rvFood = view.findViewById(R.id.rvFood)
                foodAdapter = FoodAdapter(data, onClickListener = this::addObject)
                foodManager = GridLayoutManager(context,2)
                rvFood.adapter = foodAdapter
                rvFood.layoutManager = foodManager
                foodAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.i("Request", error.toString())
            }
        )

        jsonRequest.tag = "Ejemplo de request"
        queue.add(jsonRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onClick(btn: View?) {
        var dialogBuilder: AlertDialog.Builder
        var dialog: AlertDialog
        dialogBuilder = AlertDialog.Builder(requireContext())
        var confirmationDonateView : View =
            LayoutInflater.from(context).inflate(R.layout.confirmation_donate, null)
        var btnConfirm : Button = confirmationDonateView.findViewById(R.id.btnConfirm)
        var btnCancel : Button = confirmationDonateView.findViewById(R.id.btnCancel)

        var rvItemsSelected : RecyclerView = confirmationDonateView.findViewById(R.id.rvItemsSelected)
        var selectedAdapter = SelectedAdapter(selected, onClickListener = this::addObject)
        var selectedManager = LinearLayoutManager(context)
        rvItemsSelected.adapter = selectedAdapter
        rvItemsSelected.layoutManager = selectedManager
        selectedAdapter.notifyDataSetChanged()

        dialogBuilder.setView(confirmationDonateView)
        dialog = dialogBuilder.create()
        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnConfirm.setOnClickListener{
            paymentOptions()
            dialog.dismiss()
        }

    }
    fun paymentOptions(){
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer, FragmentDonateOptions())
        transaction?.commit()
    }

    fun addObject(view: View, position: Int){
        selected.add(data.getJSONObject(position))
        Toast.makeText(context, "Selected: " , Toast.LENGTH_SHORT).show()
        Log.i("Selected", "Selected: " + selected)
    }
}
