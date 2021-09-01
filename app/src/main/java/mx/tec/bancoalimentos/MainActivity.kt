package mx.tec.bancoalimentos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logInBtn : Button = findViewById(R.id.main_logInBtn);
        val createAccountBtn : Button = findViewById(R.id.main_createAccount)

        logInBtn.setOnClickListener(){
            val intent = Intent(this, FragmentManager::class.java)
            startActivity(intent)
        }

        createAccountBtn.setOnClickListener(){
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }
    }
}