package com.cwramirezg.authentication.presentation.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.text.contains
import kotlin.text.isEmpty

@Composable
fun UsernameOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (!it.contains(" ")) {
                onValueChange(it)
            }
        },
        label = { Text("Usuario") },
        modifier = modifier,
        isError = value.isEmpty(),
        maxLines = 1,
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun UsernameOutlineTextFieldPreview() {
    UsernameOutlineTextField(
        value = "",
        onValueChange = {}
    )
}