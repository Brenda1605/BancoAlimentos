package mx.tec.bancoalimentos.adapters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.tec.bancoalimentos.Post
import mx.tec.bancoalimentos.R
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.fragments.FragmentAnuncios


class NewsAdapter(private var posts: ArrayList<Post>, val context: Context?): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var date : TextView = itemView.findViewById(R.id.news_date)
        var description : TextView = itemView.findViewById(R.id.news_description)
        var photo : ImageView = itemView.findViewById(R.id.news_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = posts[position].date
        holder.description.text = posts[position].description
        Picasso.get().load(posts[position].photo).into(holder.photo)

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}