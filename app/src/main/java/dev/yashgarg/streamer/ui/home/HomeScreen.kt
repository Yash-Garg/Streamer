package dev.yashgarg.streamer.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.data.models.StreamConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onAddClick: () -> Unit,
    onStreamClick: (StreamConfig) -> Unit
) {
    val state = viewModel.state

    Scaffold(
        modifier = modifier,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddClick, icon = {
                Icon(Icons.TwoTone.Add, contentDescription = null)
            }, text = {
                Text("ADD STREAM")
            })
        },
    ) {
        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.configs.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.TwoTone.Info, contentDescription = null)
                    Spacer(modifier = Modifier.padding(0.dp, 2.dp))
                    Text(
                        text = "No streams found",
                        fontSize = 16.sp,
                    )
                }
            }
        } else {
            LazyRow(modifier = Modifier.fillMaxSize()) {
                items(state.configs.reversed()) { config ->
                    Card(
                        modifier = Modifier.padding(24.dp, 0.dp, 0.dp, 12.dp),
                        onClick = { onStreamClick(config) },
                    ) {
                        Text(
                            modifier = Modifier.padding(24.dp),
                            text = config.streamName
                        )
                    }
                }
            }
        }
    }
}
