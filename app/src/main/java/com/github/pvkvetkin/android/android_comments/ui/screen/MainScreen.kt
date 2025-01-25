package com.github.pvkvetkin.android.android_comments.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.pvkvetkin.android.android_comments.R
import com.github.pvkvetkin.android.android_comments.ui.component.button.BackButton
import com.github.pvkvetkin.android.android_comments.ui.component.CommentItem
import com.github.pvkvetkin.android.android_comments.ui.component.button.GetPostsButton
import com.github.pvkvetkin.android.android_comments.ui.component.PostItem
import com.github.pvkvetkin.android.android_comments.ui.component.button.ShowCommentsButton
import com.github.pvkvetkin.android.android_comments.ui.component.button.UpdatePostsButton
import com.github.pvkvetkin.android.android_comments.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val isComments = viewModel.postIdForComments != null && viewModel.comments.isNotEmpty()
    var isFirstLoad by remember { mutableStateOf(true) } // Состояние для отслеживания первой загрузки

    Scaffold(
        floatingActionButton = {
            if (!isComments) { // Показываем кнопку только на экране постов
                if (isFirstLoad) {
                    GetPostsButton(onClick = {
                        viewModel.getPosts()
                        isFirstLoad = false // После первого нажатия меняем состояние
                    })
                } else {
                    UpdatePostsButton(onClick = { viewModel.getPosts() })
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            if (isComments) {
                BackButton(onClick = { viewModel.postIdForComments = null })
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                if (isComments) {
                    val selectedPost = viewModel.posts.find { it.id == viewModel.postIdForComments }
                    if (selectedPost != null) {
                        item {
                            PostItem(
                                post = selectedPost,
                                onClick = {}
                            )
                        }

                        item {
                            Text(
                                text = stringResource(R.string.comments),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }

                        items(viewModel.comments) { comment ->
                            CommentItem(comment = comment)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    items(viewModel.posts) { post ->
                        PostItem(
                            post = post,
                            onClick = { viewModel.getComments(post.id) }
                        )
                        ShowCommentsButton(onClick = { viewModel.getComments(post.id) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
