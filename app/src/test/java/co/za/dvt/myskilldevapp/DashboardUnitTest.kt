package co.za.dvt.myskilldevapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.features.dashboard.DashboardRepository
import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.models.CarModel
import co.za.dvt.myskilldevapp.models.RoundModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.HashMap

@RunWith(MockitoJUnitRunner::class)
class DashboardUnitTest {

    lateinit var dashboardViewModel: DashboardViewModel

    @Mock
    lateinit var repository: DashboardRepository
    @Mock
    lateinit var database: GameStatsDAO
    @Mock
    lateinit var application: Application

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository.database = database
        dashboardViewModel = DashboardViewModel(repository, application)
    }

    @Test
    fun `test fetch start New Round`() = runBlocking  {
        val token = "dfd9d0d0j99je9999e9999e9j9"
        val user = "test_player"
        val payload = HashMap<String, String>()
        payload[USER] = user

       whenever(repository.fetchLuckyNumber(token, payload)).thenReturn(RoundModel(user,2))
       dashboardViewModel?.getLuckNumber(token, payload)

       val actual =  dashboardViewModel?.currentLuckyNumber.value ?: 0
       val expected =  2
       assertEquals(actual, expected)
    }

    @Test
    fun `test fetch jackport car prices`()   {
        runBlocking {
            val car = CarModel()
            car.brand = "Test brand"
            car.model = "Test model"

            val cars = ArrayList<CarModel>()
            cars.add(car)

            whenever(repository.fetchAvailableCars()).thenReturn(cars)
            dashboardViewModel?.getJackportPrices()

            val actual = dashboardViewModel.availableCars.value ?: ArrayList()
            val expected = cars
            assertEquals(actual, expected)
        }
    }

    @Test
    fun `test if roll complete increments tries`() {
        dashboardViewModel.currentLuckyNumber.value = 6
        dashboardViewModel.rolledNumber.value = 6

        dashboardViewModel?.onRollCompleted()

        val actualTries = dashboardViewModel.tries
        val expectedTries = 1

        assertEquals(actualTries, expectedTries)
    }

    @Test
    fun `test if roll complete increments if win`() {
        dashboardViewModel.currentLuckyNumber.value = 6
        dashboardViewModel.rolledNumber.value = 6

        dashboardViewModel?.onRollCompleted()

        val actualWins= dashboardViewModel.winCount.value
        val expectedWins = 1
        assertEquals(actualWins, expectedWins)
    }

}