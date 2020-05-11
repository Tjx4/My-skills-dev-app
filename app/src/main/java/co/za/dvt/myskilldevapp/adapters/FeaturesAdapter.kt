package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.enums.AppFeatures
import com.makeramen.roundedimageview.RoundedImageView

class FeaturesAdapter(context: Context, private val features: Array<AppFeatures>) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var featureClickListener: FeatureClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.feature_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature = features[position]
        holder.featureNameTv.text = feature.featureName
        holder.iconRimgv.setImageResource(feature.featureIcon)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var featureNameTv: TextView = itemView.findViewById(R.id.tvFeatureName)
        internal var iconRimgv: RoundedImageView = itemView.findViewById(R.id.rimgvIcon)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (featureClickListener != null)
                featureClickListener?.onFeatureClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): AppFeatures? {
        return features?.get(id)
    }

    fun setClickListener(itemClickListener: FeatureClickListener) {
        this.featureClickListener = itemClickListener
    }

    interface FeatureClickListener {
        fun onFeatureClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return features?.size
    }
}