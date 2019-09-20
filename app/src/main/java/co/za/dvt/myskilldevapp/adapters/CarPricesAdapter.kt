package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.helpers.loadImageFromInternet
import co.za.dvt.myskilldevapp.models.Car
import com.makeramen.roundedimageview.RoundedImageView


class CarPricesAdapter(context: Context, private val cars: List<Car>) : RecyclerView.Adapter<CarPricesAdapter.ViewHolder>() {

    private val dashboardActivity: DashboardActivity
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    init {
        this.mInflater = LayoutInflater.from(context)
        dashboardActivity = context as DashboardActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.price_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]
        val model = car.model
        val brand = car.brand
        val color = car.color
        val year = car.year
        val pic = car.pic

        holder.carNameTv.text = "$brand $model"
        loadImageFromInternet(dashboardActivity, pic?: "", holder.carImg, R.drawable.ic_car)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var carNameTv: TextView
        internal var carImg: RoundedImageView

        init {
            carNameTv = itemView.findViewById(R.id.txtEmployeeName)
            carImg = itemView.findViewById(R.id.imgProfpic)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null)
                mClickListener!!.onItemClick(view, getAdapterPosition())
        }
    }

    internal fun getItem(id: Int): String? {
        return cars[id].model
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}