package com.fasttrade.android.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(nav: NavController, vm: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()

    LaunchedEffect(state) {
        if (state is AuthState.Success) nav.navigate("home") { popUpTo("login") { inclusive = true } }
    }

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Entrar", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text("E-mail") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it },
            label = { Text("Senha") }, visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())
        if (state is AuthState.Error)
            Text((state as AuthState.Error).msg, color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { vm.login(email, password) },
               modifier = Modifier.fillMaxWidth(),
               enabled = state !is AuthState.Loading) {
            Text(if (state is AuthState.Loading) "Entrando…" else "Entrar")
        }
        TextButton(onClick = { nav.navigate("register") }) { Text("Criar conta") }
    }
}
