package mx.tec.bancoalimentos.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.R
import org.json.JSONArray
import org.json.JSONObject

class SelectedAdapter(private val dataSet: MutableList<JSONObject>, private val onClickListener: MyCategoryClickListener) :
    RecyclerView.Adapter<SelectedAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameItemSelected: TextView = view.findViewById(R.id.tvNameItemSelected)
        val ivItemSelected: ImageView = view.findViewById(R.id.ivItemSelected)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_selected, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvNameItemSelected.text = dataSet.get(position).getJSONObject("fields").getJSONObject("name").getString("stringValue")
        Log.i("Adapter", viewHolder.tvNameItemSelected.text.toString())
        val urlImage: String = dataSet.get(position).getJSONObject("fields").getJSONObject("image").getString("stringValue")
        //viewHolder.containerObejct.setBackgroundColor(-7829368)
        Picasso.get().load(urlImage).into(viewHolder.ivItemSelected)
        viewHolder.itemView.setOnClickListener{ view ->
            onClickListener(view, position)
        }
    }

    override fun getItemCount() = dataSet.size
}