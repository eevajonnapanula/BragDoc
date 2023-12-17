package com.eevajonna.bragdocument.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

interface RemoteConfigService {
    suspend fun fetchConfig(): Boolean
    val summaryEnabled: Boolean
}

class RemoteConfigServiceImpl : RemoteConfigService {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    override suspend fun fetchConfig(): Boolean = remoteConfig.fetchAndActivate().await()

    override val summaryEnabled: Boolean = remoteConfig.getBoolean(SUMMARY_ENABLED_KEY)

    companion object {
        private const val SUMMARY_ENABLED_KEY = "summaryEnabled"
    }
}
