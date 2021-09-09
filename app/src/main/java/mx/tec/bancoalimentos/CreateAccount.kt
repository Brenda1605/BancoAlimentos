package mx.tec.bancoalimentos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException

class CreateAccount : AppCompatActivity() {
    lateinit var nombre : EditText
    lateinit var apellido : EditText
    lateinit var cumpleaños : EditText
    lateinit var correo : EditText
    lateinit var contraseña : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        correo = findViewById(R.id.createAcc_mail)
        contraseña = findViewById(R.id.createAcc_password)
        nombre = findViewById(R.id.createAcc_name)
        apellido = findViewById(R.id.createAcc_lastName)
        cumpleaños = findViewById(R.id.createAcc_brithday)
    }

    fun registro(view: View?){

        try {
            Firebase.auth.createUserWithEmailAndPassword(
                correo.text.toString(),
                contraseña.text.toString()
            )
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "CUENTA CREADA!", Toast.LENGTH_SHORT).show()
                        Log.d("FIREBASE", "Cuenta creada!")

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.w(
                            "FIREBASE",
                            "Registro fracaso ${it.exception}"
                        ) //exception nos dice exactamente que fue lo que falló
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: IllegalArgumentException){
            Toast.makeText(this, "DATOS FALTANTES!", Toast.LENGTH_SHORT).show()
        }

        val user = hashMapOf(
            "nombre" to nombre.text.toString(),
            "apellido" to apellido.text.toString(),
            "cumpleaños" to cumpleaños.text.toString()
        )
        var currUser = Firebase.auth.currentUser

        if (currUser != null) {
            Firebase.firestore.collection("users").document(currUser.uid)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("FIRESTORE", "id:${documentReference}")
                }.addOnFailureListener { e ->
                    Log.d("FIRESTORE","Hubo un error: $e")
                }
        }
    }

    fun back (view: View?){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}