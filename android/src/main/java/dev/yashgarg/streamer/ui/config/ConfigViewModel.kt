package dev.yashgarg.streamer.ui.config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yashgarg.streamer.data.daos.ConfigDao
import dev.yashgarg.streamer.data.models.StreamConfig
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ConfigViewModel @Inject constructor(private val configDao: ConfigDao) : ViewModel() {
    var state by mutableStateOf(ConfigFormState())
        private set

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun onEvent(event: ConfigFormEvent) {
        when (event) {
            is ConfigFormEvent.StreamNameChanged -> {
                state = state.copy(streamName = event.name)
            }
            is ConfigFormEvent.IpChanged -> {
                state = state.copy(ip = event.ip)
            }
            is ConfigFormEvent.PathChanged -> {
                state = state.copy(path = event.path)
            }
            is ConfigFormEvent.PortChanged -> {
                state = state.copy(port = event.port)
            }
            is ConfigFormEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is ConfigFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is ConfigFormEvent.ForceRtpTcpChanged -> {
                state = state.copy(forceRtpTcp = event.forceRtpTcp)
            }
            ConfigFormEvent.Save -> submitData()
        }
    }

    fun submitData() {
        val streamNameResult = ConfigFormValidator.validateStreamName(state.streamName)
        val ipResult = ConfigFormValidator.validateIp(state.ip)
        val pathResult = ConfigFormValidator.validatePath(state.path)
        val portResult = ConfigFormValidator.validatePort(state.port)

        val hasError =
            listOf(streamNameResult, ipResult, pathResult, portResult).any {
                it is ConfigFormValidatorResult.Invalid
            }

        if (hasError) {
            state =
                state.copy(
                    streamNameError =
                        (streamNameResult as? ConfigFormValidatorResult.Invalid)?.message,
                    ipError = (ipResult as? ConfigFormValidatorResult.Invalid)?.message,
                    pathError = (pathResult as? ConfigFormValidatorResult.Invalid)?.message,
                    portError = (portResult as? ConfigFormValidatorResult.Invalid)?.message
                )
            return
        } else {
            viewModelScope.launch { validationChannel.send(ValidationEvent.Success) }
        }
    }

    fun saveConfigToDb() {
        val config =
            StreamConfig(
                streamName = state.streamName.trim(),
                ip = state.ip.trim(),
                path = state.path.trim(),
                port = state.port.trim().toInt(),
                username = state.username?.trim(),
                password = state.password?.trim(),
                forceRtpTcp = state.forceRtpTcp,
            )

        viewModelScope.launch { configDao.addConfig(config) }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}
