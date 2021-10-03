package mx.tec.bancoalimentos.adapters

import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.R
import org.json.JSONArray

class FoodAdapter(private val dataSet: JSONArray) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val containerObejct: LinearLayout

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tvItemName)
            containerObejct = view.findViewById(R.id.containerObejct)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_food, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet.getJSONObject(position).getJSONObject("fields").getJSONObject("name").getString("stringValue").toString()
        if((position+1)%3==0){
            viewHolder.containerObejct.setBackgroundColor(Color.RED)
        }
        else if((position+1)%2==0){
            viewHolder.containerObejct.setBackgroundColor(Color.GREEN)
        }
        else{
            viewHolder.containerObejct.setBackgroundColor(Color.BLUE)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.length()

}
