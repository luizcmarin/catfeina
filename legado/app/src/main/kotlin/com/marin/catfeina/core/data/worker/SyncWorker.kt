// =============================================================================
// Arquivo: com.marin.catfeina.core.data.worker.SyncWorker.kt
// Descrição: Worker para sincronizar os dados remotos em segundo plano.
// =============================================================================
package com.marin.catfeina.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.marin.catfeina.core.data.repository.PoesiaRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface SyncWorkerEntryPoint {
        fun poesiaRepository(): PoesiaRepository
    }

    override suspend fun doWork(): Result {


        return    Result.success()


//        val entryPoint = EntryPointAccessors.fromApplication(appContext, SyncWorkerEntryPoint::class.java)
//        val poesiaRepository = entryPoint.poesiaRepository()
//
//        return try {
//            poesiaRepository.syncPoesias()
//            Result.success()
//        } catch (e: Exception) {
//            Result.failure()
//        }
    }
}
