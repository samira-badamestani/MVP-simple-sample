package simple.mvp.foodlist.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_item_row.view.*
import simple.mvp.foodlist.R
import simple.mvp.foodlist.data.model.Food

class HomeAdapter(
    private var items: MutableList<Food> = arrayListOf()
) : RecyclerView.Adapter<HomeAdapter.SimpleHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder =
        SimpleHolder(LayoutInflater.from(parent.context).inflate(R.layout.food_item_row, parent, false))

    inner class SimpleHolder(
        val view: View

    ) : RecyclerView.ViewHolder(view) {

        fun bind(food: Food) = with(food) {
            itemView.txt_description.text = food.ingredients
            Picasso.with(itemView.context).load(food.thumbnail).into(itemView.img_icon)

        }
    }

    fun add(list: MutableList<Food>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}