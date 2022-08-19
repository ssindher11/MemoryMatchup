package com.cutetech.memorymatchup.di

import com.cutetech.memorymatchup.domain.repository.TimestampProvider
import com.cutetech.memorymatchup.domain.repository.TimestampProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DispatchersModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class AppModule {

    @Binds
    abstract fun bindTimestampProvider(timestampProviderImpl: TimestampProviderImpl): TimestampProvider
}