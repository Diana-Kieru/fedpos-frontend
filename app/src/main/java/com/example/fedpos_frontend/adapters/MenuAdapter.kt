package com.example.fedpos_frontend.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fedpos_frontend.R

enum class MenuItems {
    AddProduct,
}


data class MenuItem(
    val item: MenuItems,
    val icon: Int,
    val title: String
)

class MenuAdapter(
    private val onItemClicked: (item: MenuItems) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val items = arrayListOf(
        MenuItem(MenuItems.AddProduct, R.drawable.cash_payment_icon, "Add Product"),
//        MenuItem(MenuItems.EVENTS, R.drawable.baseline_event_24, "Events"),
    )
    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imgMenu)
        private val textView: TextView = itemView.findViewById(R.id.tvMenu)
        fun bind(item: MenuItem) {
            imageView.setImageResource(item.icon)
            textView.text = item.title

            itemView.setOnClickListener {
                Log.i("crushError", "When CLicked Crushes here")
                onItemClicked(item.item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val menuItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_item, parent, false)
        return MenuViewHolder(menuItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }
}