package com.payam.comicbook.comic_list.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.payam.comicbook.R
import com.payam.comicbook.comic_list.domain.model.ComicModel

@Composable
fun ComicScreen(
    viewModel: ComicsViewModel = hiltViewModel()
) {
    val state = viewModel.comicsState.value

    TopContent(
        state = state,
        onRefresh = { viewModel.getComicByNumber(viewModel.currentComic) },
        onFirst = { viewModel.getComicByNumber(viewModel.firstComicNumber) },
        onPrevious = { viewModel.getComicByNumber(viewModel.currentComic - 1) },
        onNext = {
            viewModel.getComicByNumber(viewModel.currentComic + 1)
        }

    ) {
        viewModel.getComicByNumber(viewModel.lastComicNumber)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopContent(
    state: ComicsState,
    onRefresh: () -> Unit,
    onFirst: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onLast: () -> Unit,
) {
    val swipeRefreshState = rememberPullRefreshState(state.isLoading, onRefresh)

    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(swipeRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                state.comic?.let {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(it.imageLink).size(Size.ORIGINAL).build()
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth().fillParentMaxHeight(0.3f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
                                text = it.description, style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.date,
                                color = Color.Gray,
                                fontSize = 10.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }

            }
        }

        Controller(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            onFirst = onFirst,
            onPrevious = onPrevious,
            onNext = onNext,
            onLast = onLast
        )

        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = swipeRefreshState,
            contentColor = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }
}

@Composable
fun Controller(
    modifier: Modifier,
    onFirst: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onLast: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(painter = painterResource(R.drawable.ic_first_32dp),
            contentDescription = "First Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(8.dp).clickable {
                onFirst()
            })
        Icon(painter = painterResource(R.drawable.ic_previous_32dp),
            contentDescription = "Previous Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(8.dp).clickable {
                onPrevious()
            })

        Icon(painter = painterResource(R.drawable.ic_next_32dp),
            contentDescription = "Next Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(8.dp).clickable {
                onNext()
            })

        Icon(painter = painterResource(R.drawable.ic_last_32dp),
            contentDescription = "Last Comic",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(8.dp).clickable {
                onLast()
            })

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopContent(
        state = ComicsState(
            isLoading = false,
            comic = ComicModel(
                number = 2739,
                title = "Data Quality",
                description = "Desc",
                imageLink = "https://imgs.xkcd.com/comics/data_quality.png",
                date = "2023 - 2 - 17"
            ),
            error = ""
        ),
        onRefresh = {},
        onFirst = {},
        onPrevious = {},
        onNext = {}

    ) {}
}