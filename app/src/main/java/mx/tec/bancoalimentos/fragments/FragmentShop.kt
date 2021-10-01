package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.Post
import mx.tec.bancoalimentos.Product
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.adapters.FoodAdapter
import mx.tec.bancoalimentos.adapters.NewsAdapter
import mx.tec.bancoalimentos.adapters.ProductAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var rvFood: RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentShop.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentShop : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var products:ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        products = ArrayList()
        products.add(
            Product("Arroz", "2kg", "https://www.clikisalud.net/wp-content/uploads/2021/01/vida-util-arroz-seco-crudo.jpg", 20))
        products.add(
            Product("Frijol", "2kg", "https://cdn.shopify.com/s/files/1/0080/1076/0255/products/frijol_peruano_3.jpg?v=1554192134", 15))
        products.add(
            Product("Aceite", "1lt", "https://static3.abc.es/media/bienestar/2020/03/05/aceite-oliva-beneficios-1-kYhB--1200x630@abc.jpg", 30))
        products.add(
            Product("Sopa", "1kg", "https://cdn.shopify.com/s/files/1/0706/6309/products/mayoreototal-caja-sopa-moderna-caracol-no1-de-200-grs-con-20-bolsas-la-moderna-sopas-la-moderna-sku_2048x.jpg?v=1563806823", 25))


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appContext = context?.applicationContext
        val adapter = ProductAdapter(products, appContext)
        rvFood = view.findViewById(R.id.products_recyclerView)

        var linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL

        rvFood.layoutManager = linearLayout
        rvFood.adapter = adapter

        val donateBtn: Button? = view?.findViewById(R.id.home_donteBtn)
        donateBtn?.setOnClickListener(this)
    }

    override fun onClick(btn: View?) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer, FragmentDonateOptions())
        transaction?.commit()
    }

}