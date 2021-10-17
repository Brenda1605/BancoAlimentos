package mx.tec.bancoalimentos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.report_layout_row.view.*
import mx.tec.bancoalimentos.fragments.FragmentReportesMes

class ReportsAdapter(private val context: FragmentReportesMes): RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>(){

    //Crear lista
    //Set data
    private var dataList = mutableListOf<Report>()
    fun setListData(data : MutableList<Report>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.report_layout_row, parent, false)//////
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        //Obtener objeto entero de cada reporte de la lista
        val report:Report = dataList[position]
        holder.bindView(report)
    }

    override fun getItemCount(): Int {
        return if(dataList.size > 0) {
            dataList.size
        }else{
            0
        }
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Inflar vistas (Ponerle los datos) los datos vienen de onBindViewHolder
        //
        fun bindView(report : Report){
            itemView.Date.text = report.date
        }
    }
}