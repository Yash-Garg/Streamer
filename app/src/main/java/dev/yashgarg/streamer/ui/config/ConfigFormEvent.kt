package dev.yashgarg.streamer.ui.config

sealed class ConfigFormEvent {
    data class StreamNameChanged(val name: String) : ConfigFormEvent()

    data class IpChanged(val ip: String) : ConfigFormEvent()

    data class PortChanged(val port: String) : ConfigFormEvent()

    data class PathChanged(val path: String) : ConfigFormEvent()

    data class UsernameChanged(val username: String) : ConfigFormEvent()

    data class PasswordChanged(val password: String) : ConfigFormEvent()

    data class ForceRtpTcpChanged(val forceRtpTcp: Boolean) : ConfigFormEvent()

    object Save : ConfigFormEvent()
}
