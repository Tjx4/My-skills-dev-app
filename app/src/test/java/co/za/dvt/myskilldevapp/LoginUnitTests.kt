package co.za.dvt.myskilldevapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.za.dvt.myskilldevapp.features.login.LoginRepository
import co.za.dvt.myskilldevapp.features.login.LoginViewModel
import co.za.dvt.myskilldevapp.features.database.USERSDAO
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUnitTests {
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var repository: LoginRepository
    @Mock
    lateinit var database: USERSDAO
    @Mock
    lateinit var application: Application

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository.database = database
        loginViewModel = LoginViewModel(application, repository)
    }

    @Test
    fun `test if valid username is entered`(){
        var username = "NOT_null"

        var actual = loginViewModel.validateUsername(username)
        var expected = true

        assertEquals(actual, expected)
    }
    // Indetail checks

    @Test
    fun `test if invalid username is entered`(){
        var username = ""

        var actual = loginViewModel.validateUsername(username)
        var expected = false

        assertEquals(actual, expected)
    }

    @Test
    fun `test if valid password is entered`(){
        var password = "Tl@12346"

        var actual = loginViewModel.validatePassword(password)
        var expected = true

        assertEquals(actual, expected)
    }

    // Indetail checks

    @Test
    fun `test if invalid password is entered`(){
        var password = "TI@12346"

        var actual = loginViewModel.validatePassword(password)
        var expected = false

        assertEquals(actual, expected)
    }

    @Test
    fun `test login call`(){

        // whenever(repository.login(username, password)).thenReturn(LoginModel(user,2))
        //loginViewModel.signIn()
    }
}