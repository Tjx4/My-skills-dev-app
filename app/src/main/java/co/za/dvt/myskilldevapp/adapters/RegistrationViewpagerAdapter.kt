package co.za.dvt.myskilldevapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.registration.RegistrationActivity

class RegistrationViewpagerAdapter(private val fragments: List<Fragment>, fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return fragments.count()
    }

    override fun createFragment(position: Int): Fragment {
        return  fragments[position]
    }

}