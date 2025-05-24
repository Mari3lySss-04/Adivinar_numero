package com.example.adivinar_numero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.adivinar_numero.ui.theme.Adivinar_numeroTheme
import com.example.adivinar_numero.ui.theme.Adivinar_numeroTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Adivinar_numeroTheme{
                GuessingGameScreen()
            }
        }
    }
}

@Composable
fun GuessingGameScreen() {
    val randomNumber = remember { (0..100).random() }
    var userGuess by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var timeLeft by remember { mutableStateOf(60) }
    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf("Sin calcular nada aún") } // Cambio #3


    // Inicia el temporizador al iniciar el Composable
    LaunchedEffect(Unit) {
        while (timeLeft > 0 && attemptsLeft > 0) {
            delay(1000)
            timeLeft--
        }
        if (timeLeft == 0) {
            message = "¡Tiempo agotado! El número era $randomNumber"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo3),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Juego de adivinar el número",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color(0xFF4CAF50), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    append("Tienes ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("$attemptsLeft")
                    }
                    append(" intentos")
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.imagen1),
                contentDescription = "Imagen Login"
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    append("Tiempo restante ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("$timeLeft")
                    }
                    append(" segundos")
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = userGuess,
                onValueChange = { userGuess = it },
                placeholder = { Text("Ingresa tu número") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color(0xFF4CAF50), // cursor verde
                    unfocusedLabelColor = Color(0xFF4CAF50),
                    focusedTextColor = Color.Black,
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {
                    val guess = userGuess.toIntOrNull()
                    if (guess == null) {
                        message = "Ingresa un número válido"
                        return@Button
                    }

                    if (guess == randomNumber) {
                        message = "¡Felicidades! Adivinaste el número"
                    } else if (guess < randomNumber) {
                        message = "El número es mayor"
                        attemptsLeft--
                    } else {
                        message = "El número es menor"
                        attemptsLeft--
                    }

                    if (attemptsLeft == 0 && guess != randomNumber) {
                        message = "Has perdido. El número era $randomNumber"
                    }

                    userGuess = ""
                },
                enabled = attemptsLeft > 0 && timeLeft > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50), // Verde
                    contentColor = Color.White           // Texto blanco
                )

            ) {
                Text("Adivinar")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = message,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp))


        }
    }
}
