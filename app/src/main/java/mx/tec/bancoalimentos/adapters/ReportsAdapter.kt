package mx.tec.bancoalimentos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.R

class ReportsAdapter (private var fechas : ArrayList<String>, private var listener : View.OnClickListener): RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var texto : TextView = itemView.findViewById(R.id.recycler_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_row, parent, false)

        view.setOnClickListener(listener)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texto.text = fechas[position]

    }

    override fun getItemCount(): Int {
        return fechas.size
    }
}