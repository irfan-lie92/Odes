package com.example.odes.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.odes.presentation.theme.Black
import com.example.odes.presentation.theme.Label

@Composable
fun TextHeader(
    modifier: Modifier = Modifier,
    headerText: String,
    supportText: String,
    headerColor: Color = Black,
    supportColor: Color = Label,
    headerTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    supportTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    space: Dp = 10.dp
) {
    Column(modifier = modifier) {
        Text(text = headerText, style = headerTextStyle, color = headerColor)
        Spacer(modifier = Modifier.height(space))
        Text(text = supportText, style = supportTextStyle, color = supportColor)
    }
}

@Preview(showBackground = true)
@Composable
fun TextHeaderPreview() {
    TextHeader(headerText = "Welcome", supportText = "Please fill email and password to login")
}