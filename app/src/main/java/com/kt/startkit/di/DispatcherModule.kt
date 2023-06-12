package com.kt.startkit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDispatchers(val dispatcher: AppCoroutineDispatchers)

enum class AppCoroutineDispatchers {
    IO,
    MAIN
}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @AppDispatchers(AppCoroutineDispatchers.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @AppDispatchers(AppCoroutineDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}