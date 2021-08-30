package mx.tec.bancoalimentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Toast
import mx.tec.bancoalimentos.fragments.FragmentHome
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.tec.bancoalimentos.fragments.FragmentProfile


class FragmentManager : AppCompatActivity() {
    lateinit private var bottom_navigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_manager)
        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.flContainer, FragmentHome())
                    transaction.commit()
                    Toast.makeText(applicationContext, "Bienvenido al home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_map -> {
                    Toast.makeText(applicationContext, "Bienvenido a map", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_reports -> {
                    Toast.makeText(applicationContext, "Bienvenido a reportes", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_announcements -> {
                    Toast.makeText(applicationContext, "Bienvenido a anuncios", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_user -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.flContainer, FragmentProfile())
                    transaction.commit()
                    Toast.makeText(applicationContext, "Bienvenido a perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
            true
        }

        bottom_navigation.selectedItemId = R.id.action_home
        /*
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flContainer, FragmentHome())
        transaction.commit()*/
    }
}

