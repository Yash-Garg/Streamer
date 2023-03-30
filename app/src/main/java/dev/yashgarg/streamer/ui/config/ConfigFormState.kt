package dev.yashgarg.streamer.ui.config

data class ConfigFormState(
    val streamName: String = "",
    val streamNameError: String? = null,
    val ip: String = "",
    val ipError: String? = null,
    val port: String = "",
    val portError: String? = null,
    val path: String = "",
    val pathError: String? = null,
    val username: String? = null,
    val usernameError: String? = null,
    val password: String? = null,
    val passwordError: String? = null,
    val forceRtpTcp: Boolean = false,
)
