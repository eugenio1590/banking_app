package com.app.banking.di

import com.app.banking.presentation.ErrorConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class PresentationModule {

    @Provides
    fun provideErrorConverter(): ErrorConverter {
        return ErrorConverter()
    }
}