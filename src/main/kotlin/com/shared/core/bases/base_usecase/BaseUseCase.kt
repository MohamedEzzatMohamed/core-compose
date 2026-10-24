package com.shared.core.bases.base_usecase

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}