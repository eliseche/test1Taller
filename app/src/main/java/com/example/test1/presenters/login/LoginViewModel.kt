import LoginState
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Initial)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginState.Error("Username or password cannot be blank")
        } else if (username.length <= 8 && password.length <= 8) {
            _uiState.value = LoginState.Error("Username or password needs to be longer than 8 characters")
        } else {
            _uiState.value = LoginState.Success
        }
    }
}

sealed class LoginState() {
    object Initial: LoginState()
    object Success : LoginState()
    data class Error(val error: String) : LoginState()
    object Loading : LoginState()
}

