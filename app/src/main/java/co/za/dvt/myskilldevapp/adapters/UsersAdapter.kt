package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable

class UsersAdapter(context: Context, private val users: List<UsersTable>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val dashboardActivity: DashboardActivity
    private val layoutInflater: LayoutInflater
    private var itemClickListener: ItemClickListener? = null

    init {
        layoutInflater = LayoutInflater.from(context)
        dashboardActivity = context as DashboardActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.stats_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.gameStartTimeTv.text = user.name
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var gameStartTimeTv: TextView
        internal var gameTriesTv: TextView
        internal var gameJackpotPriceTv: TextView
        internal var gameEndTimeTv: TextView

        init {
            gameStartTimeTv = itemView.findViewById(R.id.tvGameStartTime)
            gameTriesTv = itemView.findViewById(R.id.tvGameTries)
            gameJackpotPriceTv = itemView.findViewById(R.id.tvGameJackpotPrice)
            gameEndTimeTv = itemView.findViewById(R.id.tvGameEndTime)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (itemClickListener != null)
                itemClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): UsersTable? {
        return users[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}