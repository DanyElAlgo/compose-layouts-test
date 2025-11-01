package com.example.compose_layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_layouts.ui.theme.Compose_LayoutsTheme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_LayoutsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MDExample(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            // Handle FAB click
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MDExample(name: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column{
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Day", "Month", "Week")

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    label = { Text(label) }
                )
            }
        }
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Option 1") },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Option 2") },
                    onClick = { /* Do something... */ }
                )
            }
        }
        FloatingActionButton(
            onClick = { onClick() },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@Composable
fun StaticVerticalList() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        repeat(50) { index ->
            Text(text = "Item $index")
        }
    }
}
@Composable
fun DynamicLazyColumn() {
    val itemsList = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape")
    LazyColumn {
        items(itemsList){ item ->
            Text(text = item)
        }
    }
}

@Composable
fun ImageExample(){
    Image(
        painter = painterResource(id = R.drawable.pvz_logo),
        contentDescription = "Sample Image",
        modifier = Modifier.width(270.dp).height(150.dp).padding(top = 2.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_LayoutsTheme {
        Column{
            MDExample("Android", onClick = {})
//            ImageExample()
//            StaticVerticalList()
//            DynamicLazyColumn()
        }
    }
}