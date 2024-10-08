package com.example.odes.presentation.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.odes.R
import com.example.odes.domain.model.EmptyStateModel
import com.example.odes.presentation.component.PasswordTextField
import com.example.odes.presentation.component.TextHeader
import com.example.odes.presentation.component.TrailingTextField
import com.example.odes.presentation.theme.Black
import com.example.odes.presentation.theme.Primary

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onLoginError: (EmptyStateModel) -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val uiState by viewModel.loginUiState.collectAsState(initial = LoginUiState.Idle)
    val keyboardController = LocalSoftwareKeyboardController.current
    val registerText = stringResource(id = R.string.register)
    val notHaveAccount = stringResource(id = R.string.not_have_account) + stringResource(id = R.string.space)
    val registerString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Black)) {
            pushStringAnnotation(tag = notHaveAccount, annotation = notHaveAccount)
            append(notHaveAccount)
        }
        withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = registerText, annotation = registerText)
            append(registerText)
        }
    }

    val isButtonEnable by remember {
        derivedStateOf {
            email.trim().isNotEmpty() && password.trim().isNotEmpty()
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Loading -> {

            }
            is LoginUiState.Success -> {
                viewModel.storeEmail((uiState as LoginUiState.Success).data.email)
                onNavigateToHome.invoke()
            }
            is LoginUiState.Error -> {
                onLoginError.invoke(
                    EmptyStateModel(
                        R.raw.jeky_error,
                        "Ups, something error",
                        (uiState as LoginUiState.Error).message,
                        "Okay"
                    )
                )
            }
            else -> Unit
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        TextHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .padding(horizontal = 24.dp),
            headerText = stringResource(R.string.login_title),
            supportText = stringResource(R.string.login_description)
        )
        TrailingTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
                .padding(horizontal = 24.dp),
            value = email,
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.input_email),
            trailingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_mail), contentDescription = "")
            },
            keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                email = it
            }
        )
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            value = password,
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.input_password),
            onValueChange = {
                password = it
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Primary
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            enabled = isButtonEnable,
            onClick = {
                viewModel.login(email, password)
                keyboardController?.hide()
            }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 30.dp, top = 70.dp)
            ,
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            text = registerString,
            onClick = { offset ->
                registerString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { span ->
                        if (span.item == registerText) {
                            onNavigateToRegister.invoke()
                        }
                    }
            }
        )
    }
}