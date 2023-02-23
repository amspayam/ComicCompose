package com.payam.comicbook.comic_list.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.payam.comicbook.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComicScreen(
    viewModel: ComicsViewModel = hiltViewModel()
) {
    val viewModelState = viewModel.comicsState.value

    val refreshing = viewModelState.isLoading
    val swipeRefreshState =
        rememberPullRefreshState(refreshing, viewModel::refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(swipeRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                viewModelState.comic?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = 10.dp
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(it.imageLink)
                                        .size(Size.ORIGINAL)
                                        .build()
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.3f)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.h3,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "# ${it.number}",
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.description,
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.date.time.toString(),
                                color = Color.Gray,
                                fontSize = 10.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                if (viewModelState.error.isNotBlank()) {
                    Text(
                        text = viewModelState.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }

            }
        }

        Controller(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        )

        PullRefreshIndicator(
            refreshing = refreshing,
            state = swipeRefreshState,
            contentColor = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }

}

@Composable
fun Controller(
    viewModel: ComicsViewModel,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

//        Button(
//            modifier = Modifier
//                .shadow(0.dp),
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 0.dp,
//                pressedElevation = 0.dp,
//                hoveredElevation = 0.dp,
//                focusedElevation = 0.dp
//            ),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color.Transparent
//            ),
//            onClick = {
//                viewModel.getComicByNumber(viewModel.firstComicNumber)
//            }
//        )
//        {
//        }
        Icon(
            painter = painterResource(R.drawable.ic_first_32dp),
            contentDescription = "First Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(8.dp)
                .clickable { viewModel.getComicByNumber(viewModel.firstComicNumber) }
        )
        Icon(
            painter = painterResource(R.drawable.ic_previous_32dp),
            contentDescription = "Previous Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    viewModel.getComicByNumber(viewModel.currentComic - 1)
                }
        )

        Icon(
            painter = painterResource(R.drawable.ic_next_32dp),
            contentDescription = "Next Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    viewModel.getComicByNumber(viewModel.currentComic + 1)
                }
        )

        Icon(
            painter = painterResource(R.drawable.ic_last_32dp),
            contentDescription = "Last Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    viewModel.getComicByNumber(viewModel.lastComicNumber)
                }
        )

    }

}