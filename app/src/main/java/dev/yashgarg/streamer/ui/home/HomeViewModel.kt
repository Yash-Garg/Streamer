package dev.yashgarg.streamer.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yashgarg.streamer.data.daos.ConfigDao
import dev.yashgarg.streamer.data.models.StreamConfig
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val configDao: ConfigDao) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            runCatching {
                configDao
                    .getConfigs()
                    .catch { state = state.copy(error = it.message, isLoading = false) }
                    .collectLatest { state = state.copy(configs = it, isLoading = false) }
            }
        }
    }

    fun removeStreamConfig(config: StreamConfig) {
        viewModelScope.launch { runCatching { configDao.deleteConfigAtIndex(config.configId) } }
    }
}

data class HomeState(
    val configs: List<StreamConfig> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
