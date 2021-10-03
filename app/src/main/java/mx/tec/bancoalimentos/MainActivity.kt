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
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class MainActivity : AppCompatActivity() {
    lateinit var correo : EditText
    lateinit var contraseña : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = Firebase.auth.currentUser
        if (user != null) {
            val intent = Intent(this, FragmentManager::class.java)
            startActivity(intent)
            finish()
        }

        val createAccountBtn : Button = findViewById(R.id.main_createAccount)
        val forgotPasswordBtn : Button = findViewById(R.id.main_forgotPassword)
        correo = findViewById(R.id.main_mail)
        contraseña = findViewById(R.id.main_password)

        createAccountBtn.setOnClickListener(){
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        forgotPasswordBtn.setOnClickListener(){
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }


    }

    fun login(view: View?){
        try {
            Firebase.auth.signInWithEmailAndPassword(
                correo.text.toString(),
                contraseña.text.toString()
            )
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Log.d("FIREBASE LOGIN", "Login existoso")
                        val intent = Intent(this, FragmentManager::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("FIREBASE LOGIN", "Login fracaso, error: ${it.exception?.message}")
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }catch (e:IllegalArgumentException){
            Toast.makeText(this, "Correo / Contraseña faltante!", Toast.LENGTH_SHORT).show()
        }
    }
}