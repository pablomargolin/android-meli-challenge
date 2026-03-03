package com.margo.shared_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.margo.shared_ui.ThemeScope.baseColors
import com.margo.shared_ui.ThemeScope.baseSizes
import com.margo.shared_ui.ThemeScope.baseTypographies

import androidx.compose.ui.tooling.preview.Preview
import com.margo.shared_ui.SpaceFlightTheme

@Composable
fun DesignSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String = "Search..."
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = query,
        onValueChange = onQueryChange,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = baseColors.primaryColor.color,
            fontFamily = baseTypographies.textBaseNormal.fontFamily,
            fontSize = baseTypographies.textBaseNormal.fontSize
        ),
        placeholder = {
            DesignText(
                text = placeholderText,
                typography = baseTypographies.textBaseNormalSmall,
                textColor = baseColors.primaryColor
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = baseColors.primaryColor.color
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(baseSizes.size10.dimension),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = baseColors.actionColor.color,
            unfocusedBorderColor = baseColors.surfaceLightColor.color,
            cursorColor = baseColors.actionColor.color
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DesignSearchBarPreview() {
    SpaceFlightTheme {
        DesignSearchBar(
            modifier = Modifier.padding(baseSizes.size10.dimension),
            query = "",
            onQueryChange = {}
        )
    }
}
