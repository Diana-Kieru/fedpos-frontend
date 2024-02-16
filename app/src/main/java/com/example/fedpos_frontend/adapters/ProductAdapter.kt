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

) : RecyclerView.Adapter<ProductAdapter.ExperienceViewHolder>() {
    private val items: ArrayList<AddProductResponse> = ArrayList()

    class ExperienceViewHolder(
        itemView: View,
        //private val onItemClicked: (AddProductResponse) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.experienceImageView)
        private val name: TextView = itemView.findViewById(R.id.experienceNameTextView)
        private val price: TextView = itemView.findViewById(R.id.experiencePriceTextView)
        private val description: TextView = itemView.findViewById(R.id.experienceDescriptionTextView)
        private val discount: TextView = itemView.findViewById(R.id.editTextDiscount)
        private val vat: TextView = itemView.findViewById(R.id.editTextVAT)
        private val targetAmount: TextView = itemView.findViewById(R.id.experiencePriceTextView)



        fun bind(item: AddProductResponse) {
            // Initialize Glide to load the image from the URL.
            Glide
                .with(itemView.context)
                .load(item.image) // You can set a placeholder image
                .placeholder(R.drawable.toothbrush)
                .into(imageView)
            //private val onItemClicked: (AddProductResponse) -> Unit

            name.text = item.name
            price.text = item.targetAmount
            description.text = item.description.toString()
            discount.text = "Discount: " + item.discount.toString() + "%"
            vat.text = "VAT: " + item.vat.toString() + "%"
            targetAmount.text = item.targetAmount






//            imageView.setImageResource(R.drawable.experience)

//            itemView.setOnClickListener {
//                onItemClicked(item) onItemClicked
//            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val experienceItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_item, parent, false)

        return ExperienceViewHolder(experienceItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(items: List<AddProductResponse>) {
//        if (clearItems) {
//            this.items.clear()
//        }
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
