package co.za.dvt.myskilldevapp.features.forgotPassword

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.za.dvt.myskilldevapp.extensions.isValidEmail
import co.za.dvt.myskilldevapp.extensions.isValidMobileNumber
import co.za.dvt.myskilldevapp.extensions.isValidOTP
import co.za.dvt.myskilldevapp.features.viewModels.BaseVieModel
import co.za.dvt.myskilldevapp.models.OtpModel
import co.za.dvt.myskilldevapp.models.ResetModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(application: Application, val forgotPasswordRepository: ForgotPasswordRepository) : BaseVieModel(application) {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
    get() = _email

    private var _mobileNumber: MutableLiveData<String> = MutableLiveData()
    val mobileNumber: LiveData<String>
    get() = _mobileNumber

    private var _errorMessage: MutableLiveData<String> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
        get() = _errorMessage

    private var _password: MutableLiveData<String> = MutableLiveData()
    val password: LiveData<String>
    get() = _password

    private var _otp: MutableLiveData<String> = MutableLiveData()
    val otp: LiveData<String>
    get() = _otp

    var busyMessage: String = "Fetching otp, please wait"

    fun checkAndFetchOtp(){
        if(!checkIsValidEmail(_email.value)){
            _errorMessage.value = "Please enter a valid email"
            return
        }

        if(!checkIsValidMobile(_mobileNumber.value)){
            _errorMessage.value = "Please enter a valid mobile number"
            return
        }

        busyMessage = "Fetching otp, please wait"
        _showLoading.value = true

        ioScope.launch {
            var otp = fetchOtp()

// Todo: remove delay
            delay(1000)

            uiScope.launch {

                if(otp.success){
                    _otp.value = otp.otp
                }
                else{
                    _errorMessage.value = otp.message
                }
            }
        }
    }

    suspend fun fetchOtp() : OtpModel {
        var email = _email.value ?: ""
        var mobile = _mobileNumber.value ?: ""

        return forgotPasswordRepository.getOtp(email, mobile)
    }

    fun checkAndReset() {
        if(!checkIsValidOTP(_otp.value)){
            _errorMessage.value = "Please enter a valid email"
            return
        }

        busyMessage = "Resetting password, please wait"
        _showLoading.value = true

        ioScope.launch {
            var resetModel = resetPassword()

// Todo: remove delay
delay(1000)

            uiScope.launch {

                if(resetModel.success){
                   // Do something if reset successful
                }
                else{
                    _errorMessage.value = resetModel.message
                }
            }
        }
    }

    suspend fun resetPassword() : ResetModel {
        var params = mutableMapOf<String, String>()
        params["email"] = _email.value ?: ""
        params["mobile"] = _mobileNumber.value ?: ""
        params["password"] = _password.value ?: ""

        return forgotPasswordRepository.resetPassword(params)
    }

    fun checkIsValidEmail(email: String?): Boolean {
        return email?.isValidEmail() ?: false
    }

    fun checkIsValidMobile(mobile: String?): Boolean {
        return mobile?.isValidMobileNumber() ?: false
    }

    fun checkIsValidOTP(otp: String?): Boolean {
        return otp?.isValidOTP() ?: false
    }
}