package com.nestifff.words.data.local.settings

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.SettingsRepository
import com.nestifff.words.domain.settings.SettingsRepository.Companion.INITIAL_IS_DARK_MODE
import com.nestifff.words.domain.settings.SettingsRepository.Companion.INITIAL_NUMBER_ON_FIRST_TRY
import com.nestifff.words.domain.settings.SettingsRepository.Companion.INITIAL_NUMBER_TO_LEARN
import com.nestifff.words.domain.settings.SettingsRepository.Companion.INITIAL_WAY_TO_LEARN
import com.nestifff.words.domain.settings.model.SettingsDomain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    app: Application,
) : SettingsRepository {

    private val dataStore = app.dataStore

    override suspend fun getSettings(): SettingsDomain {
        val settings = dataStore.data.map { prefs ->
            val numberToLearn = prefs[Key.NUMBER_TO_LEARN] ?: INITIAL_NUMBER_TO_LEARN
            val countOnFirstTry = prefs[Key.COUNT_ON_FIRST_TRY] ?: INITIAL_NUMBER_ON_FIRST_TRY
            val wayToLearn = WayToLearnDomain.valueOf(
                prefs[Key.WAY_TO_LEARN] ?: INITIAL_WAY_TO_LEARN.name
            )
            val isDarkMode = prefs[Key.IS_DARK_MODE] ?: INITIAL_IS_DARK_MODE

            SettingsDomain(
                defaultNumberToLearn = numberToLearn,
                defaultWayToLearn = wayToLearn,
                countOnFirstTryToMoveToLearned = countOnFirstTry,
                isDarkMode = isDarkMode
            )
        }.first()
        return settings
    }

    override suspend fun updateSettings(
        defaultNumberToLearn: Int?,
        defaultWayToLearn: WayToLearnDomain?,
        countOnFirstTryToMoveToLearned: Int?,
        isDarkMode: Boolean?
    ) {
        dataStore.edit { preferences ->
            if (defaultNumberToLearn != null) {
                preferences[Key.NUMBER_TO_LEARN] = defaultNumberToLearn
            }
            if (defaultWayToLearn != null) {
                preferences[Key.WAY_TO_LEARN] = defaultWayToLearn.name
            }
            if (countOnFirstTryToMoveToLearned != null) {
                preferences[Key.COUNT_ON_FIRST_TRY] = countOnFirstTryToMoveToLearned
            }
            if (isDarkMode != null) {
                preferences[Key.IS_DARK_MODE] = isDarkMode
            }
        }
    }
}


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

private object Key {
    val NUMBER_TO_LEARN = intPreferencesKey("default_number_to_learn")
    val WAY_TO_LEARN = stringPreferencesKey("default_way_to_learn")
    val COUNT_ON_FIRST_TRY = intPreferencesKey("count_on_first_try_to_move_to_learned")

    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}
