package com.shared.core.ui.main_top_bar


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.shared.core.ui.app_top_bar.AppTopAppBar
import com.shared.core.ui.icon_wrapper.IconWrapper
import com.shared.core.ui.input_fields.clearFocusOnTouch
import com.shared.core.ui.pull_to_refresh_indicator.PullToRefreshIndicator
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainTopBar(
    isRefreshing: Boolean = false,
    isPullRefresh: Boolean = true,
    title: StringResource,
    titleText: String? = null,
    leftIcon: DrawableResource? = null,
    rightIcon: DrawableResource? = null,
    contentAlignment: Alignment = Alignment.TopStart,
    onRefresh: () -> Unit = {},
    onRightIconClicked: () -> Unit = {},
    onLeftIconClicked: () -> Unit = {},
    content: @Composable () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val refreshState = rememberPullToRefreshState()


    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clearFocusOnTouch(),
        topBar = {
            AppTopAppBar(
                title = if (titleText.isNullOrEmpty()) {
                    stringResource(resource = title)
                } else {
                    titleText
                }, navigationIcon = {
                    leftIcon?.let { leftIcon ->
                        IconButton(onClick = { onLeftIconClicked() }) {
                            IconWrapper(size = 24.dp, iconContent = {
                                Icon(
                                    painter = painterResource(resource = leftIcon),
                                    contentDescription = "Menu icon",
                                )
                            })
                        }
                    }
                }, actions = {
                    rightIcon?.let { rightIcon ->
                        IconButton(onClick = dropUnlessResumed(block = onRightIconClicked)) {
                            IconWrapper(size = 24.dp, iconContent = {
                                Icon(
                                    painter = painterResource(resource = rightIcon),
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(20.dp)
                                )
                            })
                        }
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .pullToRefresh(
                    state = refreshState, isRefreshing = isRefreshing, onRefresh = onRefresh
                ), contentAlignment = contentAlignment
        ) {

            content()

            if (isPullRefresh)
                PullToRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    state = refreshState
                )
        }
    }
}
