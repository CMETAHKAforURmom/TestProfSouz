package com.example.testprofsouz.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testprofsouz.R
import com.example.testprofsouz.presentation.viewmodel.MainScreenViewModel
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@Composable
fun DropDownCascade(
    listsOfSelect: MutableList<Any>?,
    mainViewModel: MainScreenViewModel,
    searchText: MutableState<String>
) {
    var localList: MutableList<Any>? = null

    if (listsOfSelect is List<*>)
        localList = listsOfSelect
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
            .size(Int.MAX_VALUE.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (localList != null)
//            items(
//                count = localList,
//                key = {
//                    UUID.randomUUID()
//                },
//
//            ){
            itemsIndexed(localList) { index, listElements ->
                val expandedhelp by remember {
                    mutableStateOf(true)
                }
                ExpandableContentFirstStep(
                    visible = expandedhelp,
                    initialVisibility = expandedhelp,
                    listElements,
                    index % 2 == 1,
                    true,
                    mainViewModel,
                    searchText = searchText
                )
            }
    }
}

@Stable
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContentFirstStep(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    element: Any,
    isOdd: Boolean,
    isFirstElements: Boolean = false,
    mainViewModel: MainScreenViewModel,
    namePrevious: String = "",
    searchText: MutableState<String>? = null
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    if(element.toString().lowercase(Locale.ROOT).contains(searchText?.value.toString().lowercase(Locale.ROOT)) && searchText?.value?.isNotEmpty()!!){
        expanded = true
    }
//    expanded = (element.toString().contains(searchText.toString()) && searchText?.value?.isNotEmpty()!!)


    var itemHelp: List<Any> = listOf()

    if (element is List<*>)
        itemHelp = element as List<Any>

    val earlierRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        label = "",
        animationSpec = tween(500, easing = LinearEasing)
    )

    val smoothClipping by animateFloatAsState(
        targetValue = if (expanded) 8f else 0f,
        label = "",
        animationSpec = tween(500, easing = LinearEasing)
    )

    val EXPANSTION_TRANSITION_DURATION = 1000
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterTransition,
        exit = exitTransition
    ) {
        LazyColumn(
            Modifier
                .heightIn(50.dp, 1000.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.other_step_select_row))
        ) {
            item {
                Spacer(modifier = Modifier.height(1.dp))
                Row {
                    if (!isFirstElements && itemHelp[itemHelp.size - 1] is List<*>) {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .widthIn(15.dp)
                                .clip(
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = smoothClipping.toInt().dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(
                                    colorResource(id = R.color.first_step_select_row)
                                )
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .background(
                                colorResource(
                                    id = if (itemHelp[itemHelp.size - 1] is String) R.color.text_for_select_color
                                    else if (isOdd) R.color.second_palitre_color
                                    else R.color.first_palitre_color
                                )
                            )
                            .clickable {
                                expanded = !expanded
                                if (itemHelp[itemHelp.size - 1] is String)
                                    mainViewModel.setResponseField(
                                        itemHelp[0].toString(),
                                        namePrevious
                                    )
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = itemHelp[0].toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = colorResource(
                                        id = if (itemHelp[itemHelp.size - 1] is List<*>) R.color.white else R.color.main_text_color
                                    )
                                )
                            }
                            if (itemHelp[itemHelp.size - 1] is List<*>) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_select),
                                    tint = colorResource(id = R.color.white),
                                    contentDescription = "Button for select current element",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .rotate(earlierRotation)
                                )
                            }
                        }
                    }
                }
            }
            itemsIndexed(itemHelp) { index, item ->
                val expandedHelp by remember {
                    mutableStateOf(false)
                }
                if (item is List<*>)
                    Row {
                        if (!isFirstElements)
                            Box(
                                modifier = Modifier
                                    .widthIn(16.dp, 50.dp)
                            )
                        ExpandableContentFirstStep(
                            visible = expanded,
                            initialVisibility = expandedHelp,
                            item,
                            index % 2 == 1,
                            mainViewModel = mainViewModel,
                            namePrevious = itemHelp[0].toString(),
                            searchText = searchText
                        )
                    }
                else if (index != 0)
                    AnimatedVisibility(
                        visible = visible,
                        initiallyVisible = initialVisibility,
                        enter = enterTransition,
                        exit = exitTransition
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.text_for_select_color))
                                .clickable {
                                    mainViewModel.setResponseField(item.toString(), namePrevious)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = item.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = colorResource(
                                            id = R.color.main_text_color
                                        )
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}