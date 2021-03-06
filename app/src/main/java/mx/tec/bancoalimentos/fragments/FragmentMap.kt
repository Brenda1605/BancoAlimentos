package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mx.tec.bancoalimentos.R
import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Marker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMap.newInstance] factory method to
 * create an instance of this fragment.
 */


class FragmentMap : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        val mapFragment = getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment;
        mapFragment.getMapAsync(this);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        /*val banco = LatLng(20.657140309266143, -103.35566210739388)
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(banco, 18.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(banco, 13f))
        val place = googleMap.addMarker(
            MarkerOptions()
                .title("Banco de Alimentos Guadalajara")
                .snippet("Trae tus donaciones!")
                .position(banco)
        )*/

        Firebase.firestore.collection("places").get().addOnSuccessListener {
            for(doc in it){
                val title : String = doc.get("nombre").toString()
                val latitude : Double = doc.get("latitud").toString().toDouble()
                val longitud : Double = doc.get("longitud").toString().toDouble()
                val snippet : String = doc.get("snippet").toString()

                googleMap.addMarker(
                    MarkerOptions()
                        .title(title)
                        .snippet(snippet)
                        .position(LatLng(latitude, longitud)))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitud), 13f))


                //Log.d("FIREBASE", doc.data.toString())
            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMap.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMap().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}