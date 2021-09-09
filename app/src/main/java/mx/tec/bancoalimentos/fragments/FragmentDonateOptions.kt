package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import mx.tec.bancoalimentos.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDonateOptions.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentDonateOptions : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var fragment : Fragment

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
        val view : View = inflater.inflate(R.layout.fragment_donate_options, container, false)

        val cardOp1: CardView = view?.findViewById(R.id.donate_op1Card)
        val cardOp2: CardView = view?.findViewById(R.id.donate_op2Card)
        val backBtn: Button = view?.findViewById(R.id.donate_backBtn)
        cardOp1?.setOnClickListener(this)
        cardOp2?.setOnClickListener(this)
        backBtn?.setOnClickListener(this)
        //Log.d("TEST", cardOp1.id.toString())

        /*val backBtn: Button? = view?.findViewById(R.id.donate_backBtn)
        backBtn?.setOnClickListener(this)*/

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentDonateOptions.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDonateOptions().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(card: View) {

        when (card.id){
            R.id.donate_op1Card -> fragment = FragmentMap()
            R.id.donate_op2Card -> fragment = FragmentPaymentMethod()
            R.id.donate_backBtn -> fragment = FragmentHome()
        }
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.flContainer,fragment)
        transaction?.commit()
    }

}