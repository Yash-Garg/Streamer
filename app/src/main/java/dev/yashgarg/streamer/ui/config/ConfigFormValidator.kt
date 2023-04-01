package dev.yashgarg.streamer.ui.config

object ConfigFormValidator {
    fun validateStreamName(name: String): ConfigFormValidatorResult {
        return if (name.isEmpty()) {
            ConfigFormValidatorResult.Invalid("Stream name cannot be empty")
        } else {
            ConfigFormValidatorResult.Valid
        }
    }

    fun validateIp(ip: String): ConfigFormValidatorResult {
        return if (ip.isEmpty()) {
            ConfigFormValidatorResult.Invalid("IP cannot be empty")
        } else {
            ConfigFormValidatorResult.Valid
        }
    }

    fun validatePath(path: String): ConfigFormValidatorResult {
        return if (path.isEmpty()) {
            ConfigFormValidatorResult.Invalid("Path cannot be empty")
        } else {
            ConfigFormValidatorResult.Valid
        }
    }

    fun validatePort(port: String): ConfigFormValidatorResult {
        return if (port.isEmpty()) {
            ConfigFormValidatorResult.Invalid("Port cannot be empty")
        } else {
            ConfigFormValidatorResult.Valid
        }
    }
}
