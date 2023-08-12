package com.easyliu.demo.mvi

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty1

/**
 * MVI架构下View需要实现的接口
 *
 * @author easyliu
 * @date 2023/8/7 19:41
 */
interface MviView {

    /**
     * 收集状态的协程，子类可以自定义
     */
    val subscriptionScope: CoroutineScope?
        get() = try {
            val lifecycleOwner = (this as? Fragment)?.viewLifecycleOwner ?: this as? LifecycleOwner
            lifecycleOwner?.lifecycleScope
        } catch (e: IllegalStateException) {
            null
        }

    fun <S : MviUiState, I : MviUiIntent, T> BaseMviViewModel<S, I>.onEach(
        block: (state: S) -> T,
        action: suspend (T) -> Unit) {
        uiStateFlow.map { Tuple1(block(it)) }.distinctUntilChanged()
            .resolveSubscription(subscriptionScope) {
                action(it.a)
            }
    }
    fun <S : MviUiState, I : MviUiIntent, T> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, T>,
        action: suspend (T) -> Unit) {
        uiStateFlow.map { Tuple1(prop1.get(it)) }.distinctUntilChanged()
            .resolveSubscription(subscriptionScope) {
                action(it.a)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        action: suspend (A, B) -> Unit) {
        uiStateFlow.map { Tuple2(prop1.get(it), prop2.get(it)) }
            .distinctUntilChanged().resolveSubscription(subscriptionScope) {
                action(it.a, it.b)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B, C> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        action: suspend (A, B, C) -> Unit) {
        uiStateFlow.map { Tuple3(prop1.get(it), prop2.get(it), prop3.get(it)) }
            .distinctUntilChanged().resolveSubscription(subscriptionScope) {
                action(it.a, it.b, it.c)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B, C, D> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        prop4: KProperty1<S, D>,
        action: suspend (A, B, C, D) -> Unit) {
        uiStateFlow.map { Tuple4(prop1.get(it), prop2.get(it), prop3.get(it), prop4.get(it)) }
            .distinctUntilChanged().resolveSubscription(subscriptionScope) {
                action(it.a, it.b, it.c, it.d)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B, C, D, E> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        prop4: KProperty1<S, D>,
        prop5: KProperty1<S, E>,
        action: suspend (A, B, C, D, E) -> Unit) {
        uiStateFlow.map { Tuple5(prop1.get(it), prop2.get(it), prop3.get(it), prop4.get(it), prop5.get(it)) }
            .distinctUntilChanged().resolveSubscription(subscriptionScope) {
                action(it.a, it.b, it.c, it.d, it.e)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B, C, D, E, F> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        prop4: KProperty1<S, D>,
        prop5: KProperty1<S, E>,
        prop6: KProperty1<S, F>,
        action: suspend (A, B, C, D, E, F) -> Unit) {
        uiStateFlow.map {
            Tuple6(
                prop1.get(it),
                prop2.get(it),
                prop3.get(it),
                prop4.get(it),
                prop5.get(it),
                prop6.get(it))
        }
            .distinctUntilChanged().resolveSubscription(subscriptionScope) {
                action(it.a, it.b, it.c, it.d, it.e, it.f)
            }
    }

    fun <S : MviUiState, I : MviUiIntent, A, B, C, D, E, F, G> BaseMviViewModel<S, I>.onEach(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        prop3: KProperty1<S, C>,
        prop4: KProperty1<S, D>,
        prop5: KProperty1<S, E>,
        prop6: KProperty1<S, F>,
        prop7: KProperty1<S, G>,
        action: suspend (A, B, C, D, E, F, G) -> Unit) {
        uiStateFlow.map {
            Tuple7(
                prop1.get(it),
                prop2.get(it),
                prop3.get(it),
                prop4.get(it),
                prop5.get(it),
                prop6.get(it),
                prop7.get(it))
        }.distinctUntilChanged().resolveSubscription(subscriptionScope) {
            action(it.a, it.b, it.c, it.d, it.e, it.f, it.g)
        }
    }
}

