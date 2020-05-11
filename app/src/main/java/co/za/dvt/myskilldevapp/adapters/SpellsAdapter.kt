package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.models.Spell

class SpellsAdapter(context: Context, private val spells: List<Spell?>?) : RecyclerView.Adapter<SpellsAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemClickListener: SpellClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.spell_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells?.get(position)
        holder.spellNameTv.text = spell?.spell
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var spellNameTv: TextView = itemView.findViewById(R.id.txtSpellName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (itemClickListener != null)
                itemClickListener?.onSpellClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): Spell? {
        return spells?.get(id)
    }

    fun setClickListener(itemClickListener: SpellClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface SpellClickListener {
        fun onSpellClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return spells?.size ?: 0
    }
}