package com.easyliu.demo.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty1

/**
 * @author easyliu
 * @date 2023/8/8 20:32
 */

@Composable
fun <VM : BaseMviViewModel<S, I>, S : MviUiState, I : MviUiIntent> VM.collectAsState(): State<S> {
    return uiStateFlow.collectAsState(initial = state)
}

@Composable
fun <VM : BaseMviViewModel<S, I>, S : MviUiState, I : MviUiIntent, A> VM.collectAsState(prop1: KProperty1<S, A>): State<A> {
    val mappedFlow = remember(prop1) { uiStateFlow.map { prop1.get(state) }.distinctUntilChanged() }
    return mappedFlow.collectAsState(initial = prop1.get(state))
}