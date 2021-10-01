package mx.tec.bancoalimentos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mx.tec.bancoalimentos.Product
import mx.tec.bancoalimentos.R


class ProductAdapter (private var products: ArrayList<Product>, val context: Context?): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.product_name)
        var quantity : TextView = itemView.findViewById(R.id.product_quantity)
        var price : TextView = itemView.findViewById(R.id.product_price)
        var photo : ImageView = itemView.findViewById(R.id.product_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_product, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = products[position].name
        holder.quantity.text = products[position].quantity
        holder.price.text = "$  " + products[position].price.toString()
        Picasso.get().load(products[position].photo).into(holder.photo)

    }

    override fun getItemCount(): Int {
        return products.size
    }
}