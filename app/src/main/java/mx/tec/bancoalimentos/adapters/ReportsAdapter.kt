package mx.tec.bancoalimentos.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.button_row.view.*
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.Report


class ReportsAdapter: RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder>(){


    private var dataList = mutableListOf<Report>()

    fun setListData(data:MutableList<Report>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_row, parent, false)
        return ReportsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportsViewHolder, position: Int) {
        val report:Report = dataList[position]
        holder.bindView(report)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0){
            return dataList.size
        }else{
            return 0
        }
    }

    inner class ReportsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindView(report:Report){

            itemView.btnReporte.text = report.date

        }
    }

}
