package mx.tec.bancoalimentos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_layout_row.view.*
import mx.tec.bancoalimentos.fragments.FragmentAnuncios

class NewsAdapter(private val context: FragmentAnuncios): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    //Crear lista
    //Set data
    private var newsList = mutableListOf<News>()
    fun setListNews(data : MutableList<News>){
        newsList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_layout_row, parent, false)//////
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        //Obtener objeto entero de cada reporte de la lista
        val news:News = newsList[position]
        holder.bindView(news)
    }

    override fun getItemCount(): Int {
        return if(newsList.size > 0) {
            newsList.size
        }else{
            0
        }
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Inflar vistas (Ponerle los datos) los datos vienen de onBindViewHolder
        //
        fun bindView(news : News){
            itemView.txtDateNews.text = news.date
            Glide.with(context).load(news.img).into(itemView.imageViewNews)
            itemView.txtTitle.text = news.title
            itemView.txtBodyNews.text = news.body
        }
    }
}