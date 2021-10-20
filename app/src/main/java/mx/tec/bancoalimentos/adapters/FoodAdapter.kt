package mx.tec.bancoalimentos.adapters

import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.R
import org.json.JSONArray
import org.json.JSONObject

typealias MyCategoryClickListener = (View, Int) -> Unit
class FoodAdapter(private val dataSet: List<DocumentSnapshot>, private val onClickListener: MyCategoryClickListener) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val tvItemCost: TextView
        val tvItemWeight: TextView
        val containerObejct: LinearLayout

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tvItemName)
            imageView = view.findViewById(R.id.ivItemImage)
            tvItemCost = view.findViewById(R.id.tvItemCost)
            tvItemWeight = view.findViewById(R.id.tvItemWeight)
            containerObejct = view.findViewById(R.id.containerObejct)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_food, viewGroup, false)
        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet.get(position).getString("name")
        viewHolder.tvItemCost.text = "$"+dataSet.get(position).get("price").toString()
        viewHolder.tvItemWeight.text = dataSet.get(position).getString("amount")
        val urlImage: String = dataSet.get(position).getString("image").toString()//.getJSONObject(position).getJSONObject("fields").getJSONObject("image").getString("stringValue")
        //viewHolder.containerObject.setBackgroundColor(-7829368)
        Picasso.get().load(urlImage).into(viewHolder.imageView)
        viewHolder.itemView.setOnClickListener{ view ->
            onClickListener(view, position)
        }
    }

    override fun getItemCount() = dataSet.size
}
