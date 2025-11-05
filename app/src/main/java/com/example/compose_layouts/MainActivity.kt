package com.example.compose_layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_layouts.ui.theme.Compose_LayoutsTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_LayoutsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MDExample(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MDExample(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(16.dp)
    )
}

val RoundedShape = RoundedCornerShape(12.dp)

@Composable
fun ImageExample(
    modifier: Modifier = Modifier,
    sizeDp: Int = 200,
    borderWidthDp: Int = 4
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(elevation = 6.dp, shape = RoundedShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .clip(shape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pvz_logo),
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(borderWidthDp.dp, Color.Black, shape)
            )
        }
    }
}

@Composable
fun GridExample(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(8.dp)
    ) {
        items(20) { index ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column{
                    ImageExample()
                    Text(text = "Grid Item $index", modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(), fontSize = 18.dp.value.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_LayoutsTheme {
        Column{
            GridExample()
        }
    }
}