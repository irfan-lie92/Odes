package com.example.odes.presentation.screen.register

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
import com.example.odes.core.domain.model.User
import com.example.odes.domain.model.EmptyStateModel
import com.example.odes.presentation.component.PasswordTextField
import com.example.odes.presentation.component.PlainTextField
import com.example.odes.presentation.component.TextHeader
import com.example.odes.presentation.component.TrailingTextField
import com.example.odes.presentation.theme.Black
import com.example.odes.presentation.theme.Primary

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onRegisterError: (EmptyStateModel) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    val uiState by viewModel.registerUiState.collectAsState(initial = RegisterUiState.Idle)
    val keyboardController = LocalSoftwareKeyboardController.current
    val loginText = stringResource(id = R.string.login)
    val haveAccount =
        stringResource(id = R.string.have_account) + stringResource(id = R.string.space)
    val loginString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Black)) {
            pushStringAnnotation(tag = haveAccount, annotation = haveAccount)
            append(haveAccount)
        }
        withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.SemiBold)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    val isButtonEnabled by remember {
        derivedStateOf {
            name.trim().isNotEmpty() &&
                    phone.trim().isNotEmpty() &&
                    email.trim().isNotEmpty() &&
                    password.trim().isNotEmpty() &&
                    confirmPassword == password
        }
    }


    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Loading -> {

            }

            is RegisterUiState.Success -> {
                onNavigateBack.invoke()
            }

            is RegisterUiState.Error -> {
                onRegisterError.invoke(
                    EmptyStateModel(
                        R.raw.jeky_error,
                        "Ups, something error",
                        (uiState as RegisterUiState.Error).message,
                        "Okay"
                    )
                )
            }

            else -> Unit
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TextHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp)
                .padding(horizontal = 24.dp),
            headerText = stringResource(R.string.register_title),
            supportText = stringResource(R.string.register_description)
        )
        PlainTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
                .padding(horizontal = 24.dp),
            value = name,
            label = stringResource(R.string.full_name),
            placeholder = stringResource(R.string.your_full_name),
            onValueChange = {
                name = it
            }
        )
        PlainTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            value = phone,
            label = stringResource(R.string.phone),
            placeholder = stringResource(R.string.your_phone_number),
            keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = {
                phone = it
            }
        )
        TrailingTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
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
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            value = confirmPassword,
            label = stringResource(R.string.confirm_password),
            placeholder = stringResource(R.string.input_confirm_password),
            onValueChange = {
                confirmPassword = it
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
            enabled = isButtonEnabled,
            onClick = {
                viewModel.register(
                    User(
                        email, password, name, phone
                    )
                )
                keyboardController?.hide()
            }
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 30.dp, top = 70.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            text = loginString,
            onClick = { offset ->
                loginString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.let { span ->
                        if (span.item == loginText) {
                            onNavigateBack.invoke()
                        }
                    }
            }
        )
    }
}