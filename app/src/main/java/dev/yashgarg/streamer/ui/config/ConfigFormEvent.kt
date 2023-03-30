package dev.yashgarg.streamer.ui.config

sealed class ConfigFormEvent {
    data class StreamNameChanged(val name: String) : ConfigFormEvent()
    data class ipChanged(val ip: String) : ConfigFormEvent()
    data class portChanged(val port: String) : ConfigFormEvent()
    data class pathChanged(val path: String) : ConfigFormEvent()
    data class usernameChanged(val username: String) : ConfigFormEvent()
    data class passwordChanged(val password: String) : ConfigFormEvent()
    data class forceRtpTcpChanged(val forceRtpTcp: Boolean) : ConfigFormEvent()
    object Save : ConfigFormEvent()
}
