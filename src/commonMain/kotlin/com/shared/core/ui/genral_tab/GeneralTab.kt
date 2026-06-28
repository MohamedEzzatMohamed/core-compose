package com.shared.core.ui.genral_tab


import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

interface GeneralTab {
    val titleRes: String
}

@Composable
fun <T> GeneralScrollableTabRow(
    modifier: Modifier = Modifier,
    indicatorModifier: Modifier = Modifier,
    tabs: List<T>,
    selectedTab: T,
    containerColor: Color = TabRowDefaults.secondaryContainerColor,
    contentColor: Color = TabRowDefaults.secondaryContentColor,
    indicatorColor: Color = Color(0xFFE7B01F),
    selectedContentColor: Color = Color(0xFFFFFFFF),
    unselectedContentColor: Color = Color(0xFFFFFFFF),
    shape: Shape = RoundedCornerShape(size = 12.dp),
    onSelectTab: (T) -> Unit,
    divider: @Composable () -> Unit = @Composable { HorizontalDivider() },
) where T : GeneralTab {
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(minimumValue = 0)
    TabRowCard(modifier = modifier, shape = shape) {
        SecondaryTabRow(
            selectedTabIndex = selectedIndex,
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = {
                TabIndicator(
                    modifier = indicatorModifier,
                    indicatorColor = indicatorColor,
                    selectedTabIndex = selectedIndex
                )
            },
            divider = divider,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
            tabs = {
                TabsContent(
                    tabs = tabs,
                    selectedTab = selectedTab,
                    onSelectTab = onSelectTab,
                    shape = shape,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor
                )
            }
        )
    }
}


@Composable
fun <T> GeneralTabRow(
    modifier: Modifier = Modifier,
    indicatorModifier: Modifier = Modifier,
    tabs: List<T>,
    selectedTab: T?,
    containerColor: Color = TabRowDefaults.secondaryContainerColor,
    contentColor: Color = TabRowDefaults.secondaryContentColor,
    indicatorColor: Color = Color(0xFFE7B01F),
    selectedContentColor: Color = Color(0xFFFFFFFF),
    unselectedContentColor: Color = Color(0xFFFFFFFF),
    shape: Shape = RoundedCornerShape(size = 12.dp),
    divider: @Composable () -> Unit = @Composable { HorizontalDivider() },
    onSelectTab: (T) -> Unit,
) where T : GeneralTab {
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(minimumValue = 0)
    TabRowCard(modifier = modifier, shape = shape) {
        SecondaryTabRow(
            selectedTabIndex = selectedIndex,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
            containerColor = containerColor,
            contentColor = contentColor,
            indicator = {
                TabIndicator(
                    modifier = indicatorModifier,
                    indicatorColor = indicatorColor,
                    selectedTabIndex = selectedIndex,
                )
            },
            divider = divider,
            tabs = {
                TabsContent(
                    tabs = tabs,
                    selectedTab = selectedTab,
                    onSelectTab = onSelectTab,
                    shape = shape,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor
                )
            }
        )
    }
}

@Composable
private fun TabIndicatorScope.TabIndicator(
    selectedTabIndex: Int,
    indicatorColor: Color,
    modifier: Modifier = Modifier,
) {
    TabRowDefaults.SecondaryIndicator(
        color = indicatorColor,
        modifier = modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false)
    )
}

@Composable
private fun <T> TabsContent(
    tabs: List<T>,
    selectedTab: T?,
    onSelectTab: (T) -> Unit,
    shape: Shape,
    selectedContentColor: Color,
    unselectedContentColor: Color,
    style: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier
) where T : GeneralTab {
    tabs.forEach { tab ->
        val selected = selectedTab == tab
        Tab(
            selected = selected,
            onClick = { onSelectTab(tab) },
            modifier = modifier
                .height(48.dp)
                .zIndex(2f)
                .clip(shape = shape),
            selectedContentColor = selectedContentColor,
            unselectedContentColor = unselectedContentColor,
            content = {
                Text(
                    text = tab.titleRes,
                    style = style,
                    maxLines = 1,
                    fontSize = if (tabs.size > 4) 12.sp else 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize()
                )
            }
        )
    }
}

@Composable
private fun TabRowCard(
    modifier: Modifier = Modifier,
    colors: Color = Color(0xFFFFFFFF),
    shape: Shape = RoundedCornerShape(size = 12.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .heightIn(min = 50.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = shape,
        content = content
    )
}

