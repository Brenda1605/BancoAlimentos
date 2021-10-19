package mx.tec.bancoalimentos.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.report_layout_row.view.*
import mx.tec.bancoalimentos.R
import mx.tec.bancoalimentos.Report


class ReportsAdapter(private val context: Context,
                     private val itemClickListener: OnReportClickListener): RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder>(){

    interface OnReportClickListener{
        fun onItemClick(date: String, img1: String, img2: String, img3: String)
    }

    private var dataList = mutableListOf<Report>()

    fun setListData(data:MutableList<Report>){
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_layout_row, parent, false)
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
            itemView.setOnClickListener{itemClickListener.onItemClick(report.date, report.img1, report.img2, report.img3)}
            itemView.Date.text = report.date

        }
    }
}
