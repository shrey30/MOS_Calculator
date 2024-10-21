package com.shrey.mos_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shrey.mos_calculator.ui.theme.MOS_CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MOS_CalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplaySection(input, result)
        ButtonSection(
            onButtonClick = { button ->
                when (button) {
                    "=" -> result = calculateResult(input)
                    "C" -> {
                        input = ""
                        result = "0"
                    }
                    else -> input += button
                }
            }
        )
    }
}

@Composable
fun DisplaySection(input: String, result: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF333333), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = input,
            color = Color(0xFFBB86FC),
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Light
        )
        Text(
            text = result,
            color = Color.White,
            fontSize = 48.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonSection(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+"),
        listOf("C")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { button ->
                    CalculatorButton(
                        label = button,
                        backgroundColor = if (button in listOf("+", "-", "*", "/")) Color(0xFFEC407A) else Color(0xFF6200EE),
                        onClick = { onButtonClick(button) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(label: String, backgroundColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier.size(80.dp)) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .clip(CircleShape)
            .shadow(8.dp, CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = CircleShape
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

fun calculateResult(input: String): String {
    return try {
        val expression = input.replace("x", "*").replace("÷", "/")
        val result = eval(expression)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun eval(expression: String): Double {
    return expression.split("+", "-", "*", "/").map { it.toDouble() }.reduce { acc, next -> acc + next }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MOS_CalculatorTheme {
        CalculatorApp()
    }
}
