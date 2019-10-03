package co.za.dvt.myskilldevapp

import android.app.Application
import co.za.dvt.myskilldevapp.features.dashboard.DashboardRepository
import co.za.dvt.myskilldevapp.features.dashboard.DashboardViewModel
import co.za.dvt.myskilldevapp.features.dashboard.database.GameStatsDAO
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner::class)
class DashboardUnitTest {

    lateinit var dashboardViewModel: DashboardViewModel

    @Mock
    lateinit var repository: DashboardRepository
    @Mock
    lateinit var database: GameStatsDAO
    @Mock
    lateinit var application: Application

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        dashboardViewModel = DashboardViewModel(repository, database, application)
    }

    @Test
    fun isLuckyNumber_set_correctly() {
        // given
        var testLuckyNumber = 1

        // when
        dashboardViewModel.setLuckyNumber(testLuckyNumber)
        val actualResult = dashboardViewModel.luckyNumber.value?.luckyNumber != 0 && dashboardViewModel.isError.value == false
        val expectedResult = true

        // then
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun rolling_text_correctly_displaying() {
        // given
        var testRollingText = "Rolling..."

        // when
        dashboardViewModel.rollDice()

        // then
       assertEquals(dashboardViewModel.message.value, testRollingText)
    }

}