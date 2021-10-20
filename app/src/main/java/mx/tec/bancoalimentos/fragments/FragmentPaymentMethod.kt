package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import mx.tec.bancoalimentos.R
import com.paypal.android.sdk.payments.PayPalConfiguration
import androidx.core.app.ActivityCompat.startActivityForResult

import com.paypal.android.sdk.payments.PaymentActivity

import com.paypal.android.sdk.payments.PayPalService

import android.content.Intent
import android.util.Log
import android.widget.TextView

import com.paypal.android.sdk.payments.PayPalPayment
import java.math.BigDecimal


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPaymentMethod.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPaymentMethod : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val clientKey = "AcoinYhOkK7K627PBgqqbVTjP3KzyLbYhjOztRICLXwhY5tMgeAjT0zTYNNDtTX5BMLMd86v-HYsM_0a"
    val PAYPAL_REQUEST_CODE = 123
    var precio : Long? = 0
    var total : Int? = 0
    lateinit var totalProductos : TextView
    lateinit var totalPrecio : TextView

    // Paypal Configuration Object
    private val config = PayPalConfiguration() // Start with mock environment.  When ready,
        // switch to sandbox (ENVIRONMENT_SANDBOX)
        // or live (ENVIRONMENT_PRODUCTION)
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // on below line we are passing a client id.
        .clientId(clientKey)

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
        val view : View = inflater.inflate(R.layout.fragment_payment_method, container, false)
        val cancelBtn: Button = view?.findViewById(R.id.payment_cancelBtn)
        cancelBtn?.setOnClickListener(this)

        precio = arguments?.getLong("Precio")
        total = arguments?.getInt("Total")

        totalProductos = view.findViewById(R.id.payment_total)
        totalPrecio = view.findViewById(R.id.payment_price)

        totalProductos.text = total.toString() + "  Productos"
        totalPrecio.text = "$ " + precio.toString() + "    MXN"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val makePaymentBtn:Button = view.findViewById(R.id.payment_button)

        // on below line adding click listener to our make payment button.
        makePaymentBtn.setOnClickListener( View.OnClickListener() {
            getPayment()
        })
    }

    private fun getPayment() {

        // Getting the amount from editText
        val amount = precio

        // Creating a paypal payment on below line.
        val payment = PayPalPayment(
            amount?.let { BigDecimal(it) }, "MXN", "Total",
            PayPalPayment.PAYMENT_INTENT_SALE
        )

        // Creating Paypal Payment activity intent
        val intent = Intent(activity, PaymentActivity::class.java)

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentPaymentMethod.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentPaymentMethod().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer,FragmentHome())
        transaction?.commit()
    }
}