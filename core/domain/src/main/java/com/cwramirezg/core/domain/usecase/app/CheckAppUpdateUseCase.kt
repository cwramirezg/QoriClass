package com.cwramirezg.core.domain.usecase.app

import javax.inject.Inject

class CheckAppUpdateUseCase @Inject constructor() {
    suspend operator fun invoke(): AppUpdateStatus {
        return AppUpdateStatus.UpToDate
    }
}

sealed class AppUpdateStatus {
    object UpToDate : AppUpdateStatus()
    object MandatoryUpdate : AppUpdateStatus()
}
