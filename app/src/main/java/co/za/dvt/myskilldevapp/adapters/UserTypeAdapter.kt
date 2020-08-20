package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.enums.UserTypes

class UserTypeAdapter(context: Context) : RecyclerView.Adapter<UserTypeAdapter.ViewHolder>() {

    private val userTypes = UserTypes.values()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.user_type_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userType = userTypes[position]
        holder.iconImgB.setImageResource(userType.icon)
        holder.nameTv.text = userType.name
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var nameTv: TextView
        internal var iconImgB: ImageButton

        init {
            nameTv = itemView.findViewById(R.id.tvUserType)
            iconImgB = itemView.findViewById(R.id.imgbUserType)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (itemClickListener != null)
                itemClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): UserTypes? {
        return userTypes[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return userTypes.size
    }
}