package com.challenge.carapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.carapp.R
import com.challenge.carapp.util.getImagePath
import com.challenge.carapp.util.priceInThousands
import com.challenge.carapp.viewmodels.LandingViewModel

class LandingAdapter(
    private val viewModel: LandingViewModel
) : RecyclerView.Adapter<LandingAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_item_car_details,parent,false)
        )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = viewModel.carListLiveData.value?.get(position)
        require(item != null) { "Invalid state. Item can't be null" }

        holder.apply {
            Glide
                .with(holder.itemView)
                .load(item.model.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.header_tacoma)
                .into(image)

            name.text = item.model
            price.text = String.format(
                holder.itemView.context.getString(R.string.content_price),
                item.marketPrice.priceInThousands()
            )
            ratings.rating = (item.rating ?: 1).toFloat()
        }
    }

    override fun getItemCount(): Int =
        viewModel.carListLiveData.value?.size ?: 0

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val ratings: RatingBar = itemView.findViewById(R.id.ratings)
    }

    fun refresh() {
        notifyItemRangeChanged(0, itemCount - 1)
    }
}