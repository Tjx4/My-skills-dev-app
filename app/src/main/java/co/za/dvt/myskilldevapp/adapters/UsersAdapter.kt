package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.database.tables.UsersTable
import co.za.dvt.myskilldevapp.features.login.LoginActivity

class UsersAdapter(context: Context, private val users: List<UsersTable>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val loginActivity: LoginActivity = context as LoginActivity
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.previous_user_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.nameTv.text = user.name
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var nameTv: TextView

        init {
            nameTv = itemView.findViewById(R.id.tvUserName)
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