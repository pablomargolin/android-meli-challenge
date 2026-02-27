package com.margo.shared_ui.themes

import androidx.compose.ui.unit.dp
import com.margo.shared_ui.theme.SizeTheme

val SpaceFlightSizeTheme: SizeTheme = object: SizeTheme() {
    override val size00: BaseSize = BaseSize(dimension = 0.dp)
    override val size02: BaseSize = BaseSize(dimension = 2.dp)
    override val size04: BaseSize = BaseSize(dimension = 4.dp)
    override val size05: BaseSize = BaseSize(dimension = 5.dp)
    override val size10: BaseSize = BaseSize(dimension = 10.dp)
    override val size20: BaseSize = BaseSize(dimension = 20.dp)
}