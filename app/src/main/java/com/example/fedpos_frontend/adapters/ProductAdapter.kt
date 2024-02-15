package com.example.fedpos_frontend.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fedpos_frontend.R
import com.example.fedpos_frontend.model.AddProductResponse


class ProductAdapter(
    private val onItemClicked: (AddProductResponse) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ExperienceViewHolder>() {
    private val items: ArrayList<AddProductResponse> = ArrayList()

    class ExperienceViewHolder(
        itemView: View,
        private val onItemClicked: (AddProductResponse) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.experienceImageView)
        private val name: TextView = itemView.findViewById(R.id.experienceNameTextView)
        private val price: TextView = itemView.findViewById(R.id.experiencePriceTextView)
        private val description: TextView =
            itemView.findViewById(R.id.experienceDescriptionTextView)


        fun bind(item: AddProductResponse) {
            // Initialize Glide to load the image from the URL.
            Glide.with(itemView.context)
//                .load(item.avatarUrl) // You can set a placeholder image
//                .into(imageView)

            name.text = item.name
            price.text = item.price.toString()
            description.text = item.name
            imageView.setImageResource(R.drawable.experience)





            itemView.setOnClickListener {
                onItemClicked(item)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val experienceItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_item, parent, false)

        return ExperienceViewHolder(experienceItem, onItemClicked)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(items: List<AddProductResponse>, clearItems: Boolean = false) {
        if (clearItems) {
            this.items.clear()
        }
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
