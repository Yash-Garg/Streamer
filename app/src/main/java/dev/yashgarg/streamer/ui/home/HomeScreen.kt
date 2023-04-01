package dev.yashgarg.streamer.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.GridView
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.yashgarg.streamer.R
import dev.yashgarg.streamer.data.models.StreamConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onAddClick: () -> Unit,
    onStreamClick: (StreamConfig) -> Unit,
    onGridClick: () -> Unit
) {
    val state = viewModel.state

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                },
                actions = {
                    IconButton(onClick = { onGridClick() }) {
                        Icon(Icons.TwoTone.GridView, contentDescription = null)
                    }
                },
            )
        },
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 12.dp, vertical = 0.dp)
            ) {
                items(state.configs) { config ->
                    Card(
                        modifier = Modifier
                            .size(width = 250.dp, height = 150.dp)
                            .padding(vertical = 12.dp, horizontal = 12.dp),
                        onClick = { onStreamClick(config) },
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                text = config.streamName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                softWrap = true,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 1.dp),
                                text = "${config.ip}:${config.port}",
                                fontSize = 18.sp,
                                softWrap = true,
                            )
                            if (config.forceRtpTcp) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 1.dp),
                                    text = "RTP TCP",
                                    fontSize = 18.sp,
                                    softWrap = true,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
