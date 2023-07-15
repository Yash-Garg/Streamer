package dev.yashgarg.streamer.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.GridView
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import dev.yashgarg.streamer.R
import dev.yashgarg.streamer.data.models.StreamConfig

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    viewModel: HomeViewModel = viewModel(),
    onAddClick: () -> Unit,
    onStreamClick: (StreamConfig) -> Unit,
    onGridClick: () -> Unit
) {
    val state = viewModel.state
    val showRemoveDialog = remember { mutableStateOf(false) }
    val selectedConfig = remember { mutableStateOf<StreamConfig?>(null) }
    val pullRefreshState = rememberPullRefreshState(state.isLoading, { viewModel.refresh() })

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
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.TwoTone.Add, contentDescription = null) },
                text = { Text("ADD STREAM") }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).pullRefresh(pullRefreshState)) {
            if (state.configs.isEmpty() && !state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
            }

            TvLazyVerticalGrid(
                columns = TvGridCells.Adaptive(200.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 0.dp),
            ) {
                items(state.configs) { config ->
                    Card(
                        modifier =
                            Modifier.size(width = 250.dp, height = 150.dp)
                                .padding(vertical = 12.dp, horizontal = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .combinedClickable(
                                    enabled = true,
                                    onClick = { onStreamClick(config) },
                                    onLongClick = {
                                        selectedConfig.value = config
                                        showRemoveDialog.value = true
                                    }
                                ),
                    ) {
                        Column(
                            Modifier.fillMaxSize().padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = config.streamName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                softWrap = true,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 1.dp),
                                text = "${config.ip}:${config.port}",
                                fontSize = 18.sp,
                                softWrap = true,
                            )
                            if (config.forceRtpTcp) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    modifier = Modifier.padding(horizontal = 1.dp),
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

            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
            )

            if (showRemoveDialog.value) {
                RemoveStreamDialog(
                    title = "Remove ${selectedConfig.value?.streamName} stream?",
                    onDismiss = {
                        selectedConfig.value = null
                        showRemoveDialog.value = false
                    },
                    onConfirm = { selectedConfig.value?.let { viewModel.removeStreamConfig(it) } }
                )
            }
        }
    }
}

@Composable
fun RemoveStreamDialog(title: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text("Are you sure you want to remove this stream?") },
        confirmButton = { TextButton(onClick = onConfirm) { Text("Remove") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
