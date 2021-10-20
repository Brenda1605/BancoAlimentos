package mx.tec.bancoalimentos.fragments

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.adapters.FoodAdapter
import mx.tec.bancoalimentos.adapters.SelectedAdapter
import java.util.ArrayList
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


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
    private lateinit var data : List<DocumentSnapshot>
    private lateinit var selected: List<DocumentSnapshot>
    private lateinit var objectsSelected: ArrayList<String>
    lateinit var selectedAdapter: SelectedAdapter
    private var total: Long = 0
    lateinit var tvTotalAmount: TextView
    lateinit var communicator: Communicator

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
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        selected = mutableListOf()

        Firebase.auth.currentUser?.let { currentUser ->
            Firebase.firestore.collection("users").document(currentUser.uid)
                .get().addOnSuccessListener { document ->
                    objectsSelected = document.get("selected") as ArrayList<String>
                }
        }

        val donateBtn: Button? = view?.findViewById(R.id.home_donteBtn)
        donateBtn?.setOnClickListener(this)

        Firebase.firestore.collection("products")
            .get().addOnSuccessListener { document ->
                selected = document.documents
                data = document.documents
                rvFood = view.findViewById(R.id.rvFood)
                foodAdapter = FoodAdapter(data, onClickListener = this::addObject)
                foodManager = GridLayoutManager(context,2)
                rvFood.adapter = foodAdapter
                rvFood.layoutManager = foodManager
                foodAdapter.notifyDataSetChanged()
            }
    }

    //@SuppressLint("ServiceCast")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.searchFood)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    objectsSearch(query)
                }
                searchView.clearFocus()
                searchView.onActionViewCollapsed()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("search", "Escuchando cambio de query")
                return true
            }
        })

    }

    override fun onClick(btn: View?) {
        var dialogBuilder: AlertDialog.Builder
        var dialog: AlertDialog
        dialogBuilder = AlertDialog.Builder(requireContext())
        var confirmationDonateView : View =
            LayoutInflater.from(context).inflate(R.layout.confirmation_donate, null)
        val btnConfirm : Button = confirmationDonateView.findViewById(R.id.btnConfirm)
        val btnCancel : Button = confirmationDonateView.findViewById(R.id.btnCancel)
        tvTotalAmount = confirmationDonateView.findViewById(R.id.tvTotalAmount)

        var rvItemsSelected : RecyclerView = confirmationDonateView.findViewById(R.id.rvItemsSelected)
        selectedAdapter = SelectedAdapter(objectsSelected, onClickListener = this::removeObject)
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
        total = 0
        var cont:Int = 0
        for(priceObject in objectsSelected){
            Firebase.firestore.collection("products").document(priceObject).get()
            .addOnSuccessListener { doc ->
                total = doc.getLong("price")?.let { total.plus(it) }!!
                cont = cont.plus(1)
                if(cont == objectsSelected.size){
                    tvTotalAmount.text = "Total: $" + total
                }
            }
        }
    }
    fun paymentOptions(){
        communicator = activity as Communicator
        communicator.passData(total, objectsSelected.size)
    }

    fun addObject(view: View, position: Int){
        var dialogBuilder: AlertDialog.Builder
        var dialog: AlertDialog
        dialogBuilder = AlertDialog.Builder(requireContext())
        var confirmationDonateView : View =
            LayoutInflater.from(context).inflate(R.layout.select_product, null)
        var btnConfirm : Button = confirmationDonateView.findViewById(R.id.btnConfirmSelect)
        var btnCancel : Button = confirmationDonateView.findViewById(R.id.btnCancelSelect)

        dialogBuilder.setView(confirmationDonateView)
        dialog = dialogBuilder.create()
        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnConfirm.setOnClickListener{
            makeAddObject(view, position)
            dialog.dismiss()
        }
    }

    fun makeAddObject(view: View, position: Int){
        Log.i("selected", "Objeto seleccionado: " + selected.get(position).id)
        objectsSelected.add(objectsSelected.size, selected.get(position).id)

        val userShoppingCart = hashMapOf("selected" to objectsSelected)
        Firebase.auth.currentUser?.let { currentUser ->
            Firebase.firestore.collection("users").document(currentUser.uid)
                .set(userShoppingCart, SetOptions.merge())
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(context, "Objeto añadido al carrito" , Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Log.d("FIRESTORE","Hubo un error: $e")
                }
        }
    }

    fun removeObject(view: View, position: Int){
        Firebase.firestore.collection("products").document(objectsSelected.get(position)).get().addOnSuccessListener { doc ->
            total = doc.getLong("price")?.let { total.minus(it) }!!
            tvTotalAmount.text = "Total: $" + total
        }
        objectsSelected.removeAt(position)
        selectedAdapter.notifyDataSetChanged()
        val userShoppingCart = hashMapOf("selected" to objectsSelected)
        Firebase.auth.currentUser?.let { currentUser ->
            Firebase.firestore.collection("users").document(currentUser.uid)
                .set(userShoppingCart, SetOptions.merge())
                .addOnSuccessListener { documentReference ->
                    Log.i("shopping", "Objeto removido del carrito")
                }.addOnFailureListener { e ->
                    Log.d("FIRESTORE","Hubo un error: $e")
                }
        }
    }

    fun objectsSearch(search: String){
        Firebase.firestore.collection("products").whereEqualTo("name", search)
            .get().addOnSuccessListener { doc->
            data = doc.documents
            foodAdapter = FoodAdapter(data, onClickListener = this::addObject)
            foodManager = GridLayoutManager(context,2)
            rvFood.adapter = foodAdapter
            rvFood.layoutManager = foodManager
            foodAdapter.notifyDataSetChanged()
        }
    }

}
