package co.za.dvt.myskilldevapp.features.dashboard.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.CarPrizesAdapter
import co.za.dvt.myskilldevapp.constants.CARS
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.dashboard.DashboardActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.models.CarModel

class CarPrizesFragment : BaseDialogFragment(), CarPrizesAdapter.ItemClickListener {
    private var dashboardActivity: DashboardActivity? = null
    private var carsRv: RecyclerView? = null
    private var cars: List<CarModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val parentView = super.onCreateView(inflater, container, savedInstanceState)
        initViews(parentView)
        return parentView
    }

    private fun initViews(parentView: View) {
        var title = arguments?.getString(TITLE)
        cars = arguments?.getParcelableArrayList(CARS)

        if(cars == null) return

        val carPrizesAdapter = CarPrizesAdapter(dashboardActivity as Context, cars as java.util.ArrayList<CarModel>)
        carPrizesAdapter.setClickListener(this)

        carsRv = parentView.findViewById(R.id.rvCars)
        carsRv?.layoutManager = LinearLayoutManager(dashboardActivity)
        carsRv?.adapter = carPrizesAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dashboardActivity = context as DashboardActivity
    }

    override fun onItemClick(view: View, position: Int) {
        var selectedCar = cars?.get(position) ?: return

        dashboardActivity?.onPriceItemClick(selectedCar)
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(cars: List<CarModel>?): BaseDialogFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList(CARS, cars as ArrayList<CarModel>)

            val carsListFragment = CarPrizesFragment()
            carsListFragment.arguments = bundle
            return carsListFragment
        }
    }
}