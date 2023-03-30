package dev.yashgarg.streamer.ui.config

sealed class ConfigFormValidatorResult {
    object Valid : ConfigFormValidatorResult()
    data class Invalid(val message: String) : ConfigFormValidatorResult()
}
