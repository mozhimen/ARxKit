package com.mozhimen.taskk.autodispose.utils

import com.uber.autodispose.CompletableSubscribeProxy
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.MaybeSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins

/**
 * @ClassName AutoDisposeUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/10
 * @Version 1.0
 */
private val onNextStub: (Any) -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = { RxJavaPlugins.onError(OnErrorNotImplementedException(it)) }
private val onCompleteStub: () -> Unit = {}

////////////////////////////////////////////////////////////////////////////

fun <T : Any> ObservableSubscribeProxy<T>.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub,
    onNext: (T) -> Unit = onNextStub
): Disposable =
    AutoDisposeUtil.subscribeBy(this, onError, onComplete, onNext)

fun <T : Any> FlowableSubscribeProxy<T>.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub,
    onNext: (T) -> Unit = onNextStub
): Disposable =
    AutoDisposeUtil.subscribeBy(this, onError, onComplete, onNext)

fun <T : Any> SingleSubscribeProxy<T>.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onSuccess: (T) -> Unit = onNextStub
): Disposable =
    AutoDisposeUtil.subscribeBy(this, onError, onSuccess)

fun <T : Any> MaybeSubscribeProxy<T>.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub,
    onSuccess: (T) -> Unit = onNextStub
): Disposable =
    AutoDisposeUtil.subscribeBy(this, onError, onComplete, onSuccess)

fun CompletableSubscribeProxy.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub
): Disposable =
    AutoDisposeUtil.subscribeBy(this, onError, onComplete)

////////////////////////////////////////////////////////////////////////////

object AutoDisposeUtil {


    /**
     * Overloaded subscribe function that allows passing named parameters
     */
    @JvmStatic
    fun <T : Any> subscribeBy(
        proxy: ObservableSubscribeProxy<T>,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ): Disposable {
        return if (onError === onErrorStub && onComplete === onCompleteStub) {
            proxy.subscribe(onNext)
        } else {
            proxy.subscribe(onNext, onError, onComplete)
        }
    }

    /**
     * Overloaded subscribe function that allows passing named parameters
     */
    @JvmStatic
    fun <T : Any> subscribeBy(
        proxy: FlowableSubscribeProxy<T>,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ): Disposable {
        return if (onError === onErrorStub && onComplete === onCompleteStub) {
            proxy.subscribe(onNext)
        } else {
            proxy.subscribe(onNext, onError, onComplete)
        }
    }

    /**
     * Overloaded subscribe function that allows passing named parameters
     */
    @JvmStatic
    fun <T : Any> subscribeBy(
        proxy: SingleSubscribeProxy<T>,
        onError: (Throwable) -> Unit = onErrorStub,
        onSuccess: (T) -> Unit = onNextStub
    ): Disposable {
        return if (onError === onErrorStub) {
            proxy.subscribe(onSuccess)
        } else {
            proxy.subscribe(onSuccess, onError)
        }
    }

    /**
     * Overloaded subscribe function that allows passing named parameters
     */
    @JvmStatic
    fun <T : Any> subscribeBy(
        proxy: MaybeSubscribeProxy<T>,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub,
        onSuccess: (T) -> Unit = onNextStub
    ): Disposable {
        return if (onError === onErrorStub && onComplete === onCompleteStub) {
            proxy.subscribe(onSuccess)
        } else {
            proxy.subscribe(onSuccess, onError, onComplete)
        }
    }

    /**
     * Overloaded subscribe function that allows passing named parameters
     */
    @JvmStatic
    fun subscribeBy(
        proxy: CompletableSubscribeProxy,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub
    ): Disposable {
        return if (onError === onErrorStub) {
            proxy.subscribe(onComplete)
        } else {
            proxy.subscribe(onComplete, onError)
        }
    }
}