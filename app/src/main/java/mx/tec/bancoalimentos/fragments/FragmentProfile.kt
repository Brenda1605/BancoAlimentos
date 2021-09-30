package mx.tec.bancoalimentos.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toIcon
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.FragmentManager
import mx.tec.bancoalimentos.MainActivity
import mx.tec.bancoalimentos.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProfile : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var logoutBtn : Button? = null
    private var editBtn : ImageView? = null
    private var nombre : TextView? = null
    private var correo : TextView? = null
    private var cumpleaños : TextView? = null
    private var profileImage : ImageView? = null
    private var storage = Firebase.storage
    val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
        uploadImageToFirebase(uri)
    }


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
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val btn: Button? = view?.findViewById(R.id.profile_logoutBtn)
        btn?.setOnClickListener(this)
        // Inflate the layout for this fragment

        val profileRef : StorageReference = storage.reference.child("${"users/"+Firebase.auth.currentUser?.uid+"/profile.jpg"}")
        profileRef.downloadUrl.addOnSuccessListener { uri : Uri ->
            Picasso.get().load(uri).into(profileImage)
        }

        nombre = view.findViewById(R.id.profile_name)
        cumpleaños = view.findViewById(R.id.profile_birthday)
        correo = view.findViewById(R.id.profile_mail)

        editBtn = view.findViewById(R.id.profile_editBtn)
        profileImage = view.findViewById(R.id.profile_userImg)

        profileImage?.setOnClickListener {
            getImage.launch("image/*")
        }

        getData()

        return view
    }

    fun getData(){
        var currUser = Firebase.auth.currentUser

        if (currUser != null) {
            Firebase.firestore.collection("users").document(currUser.uid)
                .get().addOnSuccessListener {
                    nombre?.text = it.getString("nombre") + " " + it.getString("apellido")
                    correo?.text = currUser.email.toString()
                    cumpleaños?.text = it.getString("cumpleaños")
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
         * @return A new instance of fragment FragmentProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        Firebase.auth.signOut()
        val intent = Intent(activity, MainActivity::class.java)
        activity?.startActivity(intent)
    }

    fun uploadImageToFirebase(imgUri : Uri){
        val storageRef = storage.reference
        val fileRef = storageRef.child("${"users/"+Firebase.auth.currentUser?.uid+"/profile.jpg"}")
        val uploadTask = fileRef.putFile(imgUri)


        uploadTask.addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri : Uri ->
                Picasso.get().load(uri).into(profileImage)
            }
        }.addOnFailureListener{
            Toast.makeText(getActivity(), "Image ERROR", Toast.LENGTH_SHORT).show()
        }
    }

}