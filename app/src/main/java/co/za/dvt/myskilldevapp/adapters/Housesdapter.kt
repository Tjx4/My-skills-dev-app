package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.models.House

class HousesAdapter(context: Context, private val houses: List<House?>?) : RecyclerView.Adapter<HousesAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemClickListener: HouseClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.house_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val house = houses?.get(position)

        holder.houseNameTv.text = house?.name
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var houseNameTv: TextView = itemView.findViewById(R.id.txtHouseMascot)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (itemClickListener != null)
                itemClickListener?.onHouseClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): House? {
        return houses?.get(id)
    }

    fun setClickListener(itemClickListener: HouseClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface HouseClickListener {
        fun onHouseClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return houses?.size ?: 0
    }
}