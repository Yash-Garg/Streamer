package dev.yashgarg.streamer.ui.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Password
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.SettingsEthernet
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.ui.ErrorTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfigViewModel = viewModel(),
    onBackNavigate: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(context) {
        viewModel.validationEvents.collect {
            if (it is ConfigViewModel.ValidationEvent.Success) {
                viewModel.saveConfigToDb()
                onBackNavigate()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Stream",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackNavigate() }) {
                        Icon(Icons.TwoTone.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.submitData() },
                icon = { Icon(Icons.TwoTone.Check, contentDescription = null) },
                text = { Text("SAVE CONFIG") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp, 0.dp),
        ) {
            ErrorTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.streamName,
                isError = state.streamNameError != null,
                leadingIcon = { Icon(imageVector = Icons.TwoTone.Tag, contentDescription = null) },
                onValueChange = { viewModel.onEvent(ConfigFormEvent.StreamNameChanged(it)) },
                label = "Stream Name",
                errorMessage = state.streamNameError,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ErrorTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.ip,
                isError = state.ipError != null,
                leadingIcon = {
                    Icon(imageVector = Icons.TwoTone.SettingsEthernet, contentDescription = null)
                },
                onValueChange = { viewModel.onEvent(ConfigFormEvent.IpChanged(it)) },
                label = "IP / URL",
                errorMessage = state.ipError,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ErrorTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.path,
                leadingIcon = { Text("/") },
                isError = state.pathError != null,
                onValueChange = { viewModel.onEvent(ConfigFormEvent.PathChanged(it)) },
                label = "Path",
                errorMessage = state.pathError,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ErrorTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.port,
                leadingIcon = { Text(":") },
                isError = state.portError != null,
                onValueChange = { viewModel.onEvent(ConfigFormEvent.PortChanged(it)) },
                label = "Port",
                errorMessage = state.portError,
                keyboardType = KeyboardType.Number,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ErrorTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username ?: "",
                leadingIcon = {
                    Icon(imageVector = Icons.TwoTone.Person, contentDescription = null)
                },
                isError = state.usernameError != null,
                onValueChange = { viewModel.onEvent(ConfigFormEvent.UsernameChanged(it)) },
                label = "Username",
                errorMessage = state.usernameError,
            )
            ErrorTextField(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                value = state.password ?: "",
                leadingIcon = {
                    Icon(imageVector = Icons.TwoTone.Password, contentDescription = null)
                },
                isError = state.passwordError != null,
                onValueChange = { viewModel.onEvent(ConfigFormEvent.PasswordChanged(it)) },
                label = "Password",
                errorMessage = state.passwordError,
                keyboardType = KeyboardType.Password,
                passwordHidden = passwordHidden,
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) Icons.TwoTone.Visibility
                            else Icons.TwoTone.VisibilityOff
                        val description = if (passwordHidden) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Force RTP TCP connection?")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = state.forceRtpTcp,
                    onCheckedChange = { viewModel.onEvent(ConfigFormEvent.ForceRtpTcpChanged(it)) },
                )
            }
        }
    }
}
