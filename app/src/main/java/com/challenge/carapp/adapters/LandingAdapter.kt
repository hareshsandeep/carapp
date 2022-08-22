package com.challenge.carapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.carapp.R
import com.challenge.carapp.components.FilterManager
import com.challenge.carapp.models.CarsModel
import com.challenge.carapp.util.getImagePath
import com.challenge.carapp.util.priceInThousands
import com.challenge.carapp.viewmodels.LandingViewModel

class LandingAdapter(
    private val viewModel: LandingViewModel
) : RecyclerView.Adapter<LandingAdapter.CarViewHolder>(),
    FilterManager.FilterChangeListener,
    Filterable{
    private val separator = "||||%$#"
    private val data: List<CarsModel>
        get() = viewModel.carListLiveData.value ?: emptyList()
    private var filteredData: List<CarsModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder =
        CarViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.row_item_car_details, parent, false)
        )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = filteredData?.get(position)
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

            holder.itemView.setOnClickListener {
                viewModel.setExpanded(position)
            }

            if (viewModel.expanded.value == position) {
                // Expand
                performExpansion(holder, item)
            }

            if (viewModel.collapsed.value == position) {
                // Collapse
                prosConsContainer.visibility = View.GONE
            }
        }
    }

    private fun performExpansion(holder: CarViewHolder, item: CarsModel) {
        if (item.prosList.isNotEmpty()) {
            holder.prosLabel.visibility = View.VISIBLE
            holder.prosConsContainer.visibility = View.VISIBLE
            holder.prosContainer.visibility = View.VISIBLE
            addBulletPointViews(holder.prosContainer, item.prosList)
        } else {
            holder.prosLabel.visibility = View.GONE
            holder.prosContainer.visibility = View.GONE
        }

        if (item.consList.isNotEmpty()) {
            holder.prosConsContainer.visibility = View.VISIBLE
            holder.consLabel.visibility = View.VISIBLE
            holder.consContainer.visibility = View.VISIBLE
            addBulletPointViews(holder.consContainer, item.consList)
        } else {
            holder.consLabel.visibility = View.GONE
            holder.consContainer.visibility = View.GONE
        }
    }

    private fun addBulletPointViews(container: LinearLayout, bulletList: List<String>) {
        container.removeAllViews()
        val inflater = LayoutInflater.from(container.context)
        bulletList.forEach {
            if (it.trim().isNotEmpty()) {
                val bulletPointView = inflater.inflate(R.layout.item_bullet_point, container, false)
                val textView = bulletPointView.findViewById<TextView>(R.id.bullet_point)
                textView.text = it
                container.addView(bulletPointView)
            }
        }
    }

    override fun getItemCount(): Int = filteredData?.size ?: 0

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val ratings: RatingBar = itemView.findViewById(R.id.ratings)
        val prosConsContainer: View = itemView.findViewById(R.id.pros_cons_container)
        val prosLabel: TextView = itemView.findViewById(R.id.pros_label)
        val prosContainer: LinearLayout = itemView.findViewById(R.id.pros_container)
        val consLabel: TextView = itemView.findViewById(R.id.cons_label)
        val consContainer: LinearLayout = itemView.findViewById(R.id.cons_container)
    }

    fun refresh() {
        filteredData = data
        notifyItemRangeChanged(0, itemCount - 1)
    }

    override fun onSearchClicked(make: String, model: String, closeKeyboard: Boolean) {
        if(closeKeyboard) {
            viewModel.closeKeyBoard()
        }
        filter.filter("$make$separator$model")
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint?.toString() ?: ""
                return FilterResults().apply { values = filteredData(searchString) }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = if (results?.values == null)
                    emptyList()
                else
                    results.values as List<CarsModel>
                notifyDataSetChanged()
            }
        }
    }

    private fun filteredData(searchString: String): List<CarsModel> {
        val makeString = searchString.split(separator)[0]
        val modelString = searchString.split(separator)[1]

        if (makeString.isEmpty() && modelString.isEmpty()) {
            return data
        }

        val filteredList = ArrayList<CarsModel>()
        data.filter { item ->
            item.make?.contains(makeString, true) ?: false
                    && item.model?.contains(modelString, true) ?: false
        }.forEach { filteredList.add(it) }

        return filteredList
    }
}