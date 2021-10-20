package mx.tec.bancoalimentos.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.R
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class SelectedAdapter(private val dataSet: ArrayList<String>, private val onClickListener: MyCategoryClickListener) :
    RecyclerView.Adapter<SelectedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameItemSelected: TextView = view.findViewById(R.id.tvNameItemSelected)
        val ivItemSelected: ImageView = view.findViewById(R.id.ivItemSelected)
        val tvPriceItemSelected: TextView = view.findViewById(R.id.tvPriceItemSelected)
        val tvItemWeightSelected: TextView = view.findViewById(R.id.tvItemWeightSelected)
        val btnDeleteObject: ImageButton = view.findViewById(R.id.btnDeleteObject)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_selected, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Firebase.firestore.collection("products").document(dataSet.get(position)).get().addOnSuccessListener { doc ->
            viewHolder.tvNameItemSelected.text = doc.getString("name")
            viewHolder.tvPriceItemSelected.text = "$"+doc.get("price").toString()
            viewHolder.tvItemWeightSelected.text = doc.getString("amount")
            val urlImage: String = doc.get("image").toString()
            //viewHolder.containerObejct.setBackgroundColor(-7829368)
            Picasso.get().load(urlImage).into(viewHolder.ivItemSelected)
            viewHolder.btnDeleteObject.setOnClickListener{ view ->
                onClickListener(view, position)
            }
        }

    }

    override fun getItemCount() = dataSet.size
}