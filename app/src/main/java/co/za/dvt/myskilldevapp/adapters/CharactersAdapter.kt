package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.models.Character

class CharactersAdapter(context: Context,  private val layout: Int, private val characters: List<Character?>?) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var characterClickListener: CharacterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters?.get(position)

        holder.characterNameTv.text = character?.name
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var characterNameTv: TextView = itemView.findViewById(R.id.tvCharacterHouse)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (characterClickListener != null)
                characterClickListener?.onCharacterClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): Character? {
        return characters?.get(id)
    }

    fun setClickListener(itemClickListener: CharacterClickListener) {
        this.characterClickListener = itemClickListener
    }

    interface CharacterClickListener {
        fun onCharacterClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return characters?.size ?: 0
    }
}