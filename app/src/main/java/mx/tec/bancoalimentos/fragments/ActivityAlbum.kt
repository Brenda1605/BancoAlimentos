package mx.tec.bancoalimentos.fragments

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album.*
import mx.tec.bancoalimentos.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ActivityAlbum.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActivityAlbum : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

            if(intent.extras != null){
                Glide.with(this).load(intent.getStringExtra("img1")).into(imageViewalbum1)
                Glide.with(this).load(intent.getStringExtra("img2")).into(imageViewalbum2)
                Glide.with(this).load(intent.getStringExtra("img3")).into(imageViewalbum3)

                val date = intent.getStringExtra("date")
                txtDateAlbum.text = date
            }

        }
    }
