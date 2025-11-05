package com.example.compose_layouts

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_layouts.ui.theme.Compose_LayoutsTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

@Composable
fun DatePickerField(
    label: String = "Fecha",
    value: String,
    onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = remember {
        DatePickerDialog(context, { _: DatePicker, y: Int, m: Int, d: Int ->
            calendar.set(y, m, d)
            onDateSelected(calendar.timeInMillis)
        }, year, month, day)
    }

    TextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { datePicker.show() }
    )
}

@Composable
fun PhoneNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Teléfono",
    maxLength: Int? = null
) {
    TextField(
        value = value,
        onValueChange = { new ->
            val sb = StringBuilder()
            var inExt = false
            var i = 0
            while (i < new.length) {
                val c = new[i]
                if (!inExt) {
                    when {
                        c.isDigit() || c == '+' || c == '-' || c == '(' || c == ')' || c == ' ' || c == '.' || c == ',' || c == '#' -> {
                            sb.append(c)
                        }
                        c == 'x' || c == 'X' -> {
                            inExt = true
                            sb.append('x')
                        }
                        (c == 'e' || c == 'E') -> {
                            // detectar "ext" y normalizar a 'x'
                            val rest = new.substring(i).lowercase(Locale.getDefault())
                            if (rest.startsWith("ext")) {
                                inExt = true
                                sb.append('x')
                                i += 2 // saltar "xt" adicional en el bucle
                            }
                        }
                    }
                } else {
                    // dentro de la extensión: sólo dígitos y separadores simples
                    if (c.isDigit() || c == ' ' || c == '-' || c == '#') sb.append(c)
                }
                i++
            }

            var filtered = sb.toString()
            if (maxLength != null) filtered = filtered.take(maxLength)
            onValueChange(filtered)
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun FormExample(){
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    var selectedDateText by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var numberText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Formulario de ejemplo",
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            fontSize = 30.dp.value.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
//        Nombre
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
//        Número
        PhoneNumberTextField(
            value = numberText,
            onValueChange = { numberText = it },
            label = "Teléfono",
            maxLength = 25
        )
//        Fecha
        DatePickerField(
            value = selectedDateText,
            onDateSelected = { millis ->
                selectedDateMillis = millis
                selectedDateText = sdf.format(Date(millis))
            }
        )
        FormButton(text = "Enviar",
            onClick = {},
            modifier = Modifier)
    }
}

@Composable
fun FormButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth(fraction = 0.5f)
            .height(50.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, color = Color.Black, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(Color.DarkGray)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_LayoutsTheme {
        FormExample()
    }
}