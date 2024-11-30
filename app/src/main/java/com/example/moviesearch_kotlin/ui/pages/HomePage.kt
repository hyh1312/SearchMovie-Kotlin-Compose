package com.example.moviesearch_kotlin.ui.pages

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.moviesearch_kotlin.ui.AppBar


@ExperimentalMaterial3Api
@Composable
fun HomePage(
    searchMovie: (String) -> Unit,
    history: () -> Unit,
) {
    AppBar("小 H 的电影搜索") { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding) // ?
                .padding(vertical = 20.dp)
                .padding(20.dp)

        ) {
            var query by remember { mutableStateOf("") }
            SearchTextField(Modifier.fillMaxWidth(), query, searchMovie) { newText ->
                query = newText
            }
            SearchButton(
                { searchMovie(query) },
                Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Button(
                onClick = history,
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Start)
                    .padding(20.dp)
            ) {
                Text(text = "历史\n记录", fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun SearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "搜索", fontSize = 20.sp)
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier,
    text: String,
    searchMovies: (String) -> Unit,
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        placeholder = { Text("请输入电影名称") },
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search // 设置键盘动作为搜索
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                searchMovies(text) // 回车键触发搜索
            }
        )
    )
}
