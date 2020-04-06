package co.za.dvt.myskilldevapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.za.dvt.myskilldevapp.constants.USER
import co.za.dvt.myskilldevapp.features.dashboard.DashboardRepository
import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import co.za.dvt.myskilldevapp.features.database.GameStatsDAO
import co.za.dvt.myskilldevapp.models.RoundModel
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
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
    fun `test fetch start New Round`() {
        val token = "dfd9d0d0j99je9999e9999e9j9"
        val user = "test_player"
        val payload = HashMap<String, String>()
        payload[USER] = user

        whenever(repository.fetchLuckyNumber(token, payload)).thenReturn(RoundModel(user,2))
       val actual = dashboardViewModel?.startNewRound()

       //  assert()
    }

    @Test
    fun `test fetch jackport prices`() {
        // given
        var testLuckyNumber = 1

        // when
        val actualResult = dashboardViewModel?.currentLuckyNumber?.value != 0 &&  dashboardViewModel?.currentLuckyNumber?.value!! < 7
        val expectedResult = true

        // then
        assertEquals(expectedResult, actualResult)
    }

}