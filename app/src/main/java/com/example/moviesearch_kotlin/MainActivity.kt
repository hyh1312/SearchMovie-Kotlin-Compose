package com.example.moviesearch_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUi()
        }
    }

    @ExperimentalMaterial3Api
    @Preview
    @Composable
    fun MainUi() {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ),
                    title = { Text("小 H 的电影搜索") }
                )
            },
            bottomBar = {
                // 之后在写导航吧。。。
            }
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // ?
                    .padding(20.dp)

            ) {
                var query by remember { mutableStateOf("") }
                val context = LocalContext.current
                SearchTextField(Modifier.fillMaxWidth(), query, { newText -> query = newText })
                SearchButton(
                    { SearchMovie(query, context) },
                    Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                )
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
    fun SearchTextField(modifier: Modifier, text: String, onValueChange: (String) -> Unit) {
        val context = LocalContext.current
        TextField(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            label = { Text("请输入电影名称") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search // 设置键盘动作为搜索
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    SearchMovie(text, context) // 回车键触发搜索
                }
            )
        )
    }

    fun SearchMovie(query: String, context: Context) {
        val intent = Intent(context, SearchActivity::class.java).putExtra("query", query)
        context.startActivity(intent)
    }

}
