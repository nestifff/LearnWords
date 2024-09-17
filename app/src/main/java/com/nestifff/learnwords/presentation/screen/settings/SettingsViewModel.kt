package com.nestifff.learnwords.presentation.screen.settings

import androidx.lifecycle.viewModelScope
import com.nestifff.learnwords.app.core.BaseViewModel
import com.nestifff.learnwords.app.core.UiEffect
import com.nestifff.learnwords.app.core.UiState
import com.nestifff.words.domain.learn.model.WayToLearnDomain
import com.nestifff.words.domain.settings.model.SettingsDomain
import com.nestifff.words.domain.settings.usecase.GetSettingsUseCase
import com.nestifff.words.domain.settings.usecase.UpdateSettingsUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
) : BaseViewModel<SettingsViewModel.State, SettingsViewModel.Effect>() {

    data class State(
        val currentSettings: SettingsDomain? = null,

        val updatedNumberToLearn: String? = null,
        val updatedCountOnFirstTry: String? = null,
        val isChangeWayToLearnMenuVisible: Boolean = false,
        val updatedWayToLearn: WayToLearnDomain? = null,
        val updatedDarkMode: Boolean? = null,
        val isWarningCountOnFirstTryVisible: Boolean = false,

        val isSaveButtonEnabled: Boolean = false,
        val isSavingInProgress: Boolean = false,

        val isConfirmExitDialogVisible: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object SaveSuccessMessage : Effect()
        data object ErrorZeroOrEmptyValuesMessage : Effect()
    }

    init {
        viewModelScope.launch {
            val settings = getSettingsUseCase.execute()
            produceState(state.copy(currentSettings = settings))
        }
    }

    fun onNumberToLearnChanged(newValue: String) {
        produceState(
            state.copy(
                updatedNumberToLearn = newValue,
                isSaveButtonEnabled = hasUnsavedData(updatedNumberToLearn = newValue)
            )
        )
    }

    fun onCountOnFirstTryChanged(newValue: String) {
        produceState(
            state.copy(
                updatedCountOnFirstTry = newValue,
                isSaveButtonEnabled = hasUnsavedData(updatedCountOnFirstTry = newValue)
            )
        )
    }

    fun onWayToLearnSelected(newWayToLearn: WayToLearnDomain) {
        produceState(
            state.copy(
                updatedWayToLearn = newWayToLearn,
                isSaveButtonEnabled = hasUnsavedData(updatedWayToLearn = newWayToLearn),
                isChangeWayToLearnMenuVisible = false
            )
        )
    }

    fun onSelectWayToLearnClicked() {
        produceState(state.copy(isChangeWayToLearnMenuVisible = true))
    }

    fun onWayToLearnMenuDismiss() {
        produceState(state.copy(isChangeWayToLearnMenuVisible = false))
    }

    fun onDarkModeChanged(newValue: Boolean) {
        produceState(
            state.copy(
                updatedDarkMode = newValue,
                isSaveButtonEnabled = hasUnsavedData(updatedDarkMode = newValue)
            )
        )
    }

    fun onSaveClicked() {
        val newNumberToLearn = state.updatedNumberToLearn?.toIntOrNull() ?: 0
        val newCountOnFirstTry = state.updatedCountOnFirstTry?.toIntOrNull() ?: 0
        if (newNumberToLearn <= 0 || newCountOnFirstTry <= 0) {
            produceEffect(Effect.ErrorZeroOrEmptyValuesMessage)
        }
        viewModelScope.launch {
            produceState(state.copy(isSavingInProgress = true, isSaveButtonEnabled = false))
            updateSettingsUseCase.execute(
                defaultNumberToLearn = newNumberToLearn,
                defaultWayToLearn = state.updatedWayToLearn,
                countOnFirstTryToMoveToLearned = newCountOnFirstTry,
                isDarkMode = state.updatedDarkMode
            )
            produceState(
                state.copy(
                    currentSettings = getSettingsUseCase.execute(),
                    updatedNumberToLearn = null,
                    updatedWayToLearn = null,
                    updatedCountOnFirstTry = null,
                    updatedDarkMode = null,
                    isSavingInProgress = false,
                    isSaveButtonEnabled = false
                )
            )
            produceEffect(Effect.SaveSuccessMessage)
        }
    }

    fun onBackTriggered() {
        if (hasUnsavedData()) {
            produceState(state.copy(isConfirmExitDialogVisible = true))
        } else {
            produceEffect(Effect.NavigateBack)
        }
    }

    fun onDismissConfirmExitDialog() {
        produceState(state.copy(isConfirmExitDialogVisible = false))
    }

    fun onConfirmExitDialogExitClicked() {
        produceState(state.copy(isConfirmExitDialogVisible = false))
        produceEffect(Effect.NavigateBack)
    }

    private fun hasUnsavedData(
        updatedNumberToLearn: String? = null,
        updatedCountOnFirstTry: String? = null,
        updatedWayToLearn: WayToLearnDomain? = null,
        updatedDarkMode: Boolean? = null
    ): Boolean {
        try {
            val setting = state.currentSettings ?: return false

            val numberToLearnToCompare = updatedNumberToLearn ?: state.updatedNumberToLearn
            val isNumberToLearnChanged = numberToLearnToCompare?.let {
                it.toInt() != setting.defaultNumberToLearn
            } ?: false

            val countOnFirstTryToCompare = updatedCountOnFirstTry ?: state.updatedCountOnFirstTry
            val isCountOnFirstTryChanged = countOnFirstTryToCompare?.let {
                it.toInt() != setting.countOnFirstTryToMoveToLearned
            } ?: false

            val wayToLearnToCompare = updatedWayToLearn ?: state.updatedWayToLearn
            val isWayToLearnChanged = wayToLearnToCompare?.let {
                it != setting.defaultWayToLearn
            } ?: false

            val darkModeToCompare = updatedDarkMode ?: state.updatedDarkMode
            val isDarkModeChanged = darkModeToCompare?.let {
                it != setting.isDarkMode
            } ?: false

            return isNumberToLearnChanged || isCountOnFirstTryChanged || isWayToLearnChanged
                    || isDarkModeChanged
        } catch (ex: Exception) {
            return false
        }
    }

    override fun createInitialState(): State = State()
}
