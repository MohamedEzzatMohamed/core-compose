package com.shared.core.ui.navigation_drawer_wrapper

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color



@Composable
fun NavigationDrawerWrapper(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    drawerContainerColor: Color = DrawerDefaults.standardContainerColor,
    modalDrawerSheetContent: @Composable ColumnScope.() -> Unit,
    contentScreens: @Composable () -> Unit
) {

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet(
                modifier = modifier,
                drawerContainerColor = drawerContainerColor,
            ) {
                modalDrawerSheetContent()

            }
        }, content = { contentScreens() }
    )
}