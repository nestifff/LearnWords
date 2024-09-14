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

        val updatedNumberToLearn: Int? = null,
        val updatedCountOnFirstTry: Int? = null,
        val isChangeWayToLearnDialogVisible: Boolean = false,
        val updatedWayToLearn: WayToLearnDomain? = null,
        val updatedDarkMode: Boolean? = null,

        val isSaveButtonEnabled: Boolean = false,
        val isSavingInProgress: Boolean = false,
        val isConfirmExitDialogVisible: Boolean = false,

        val isSaveSuccessMessageVisible: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
    }

    init {
        viewModelScope.launch {
            val settings = getSettingsUseCase.execute()
            produceState(state.copy(currentSettings = settings))
        }
    }

    fun onNumberToLearnChanged(newValue: Int) {
        produceState(
            state.copy(
                updatedNumberToLearn = newValue,
                isSaveButtonEnabled = hasUnsavedData(updatedNumberToLearn = newValue)
            )
        )
    }

    fun onCountOnFirstTryChanged(newValue: Int) {
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
                isSaveButtonEnabled = hasUnsavedData(updatedWayToLearn = newWayToLearn)
            )
        )
    }

    fun onSelectWayToLearnClicked() {
        produceState(state.copy(isChangeWayToLearnDialogVisible = true))
    }

    fun onWayToLearnDialogDismiss() {
        produceState(state.copy(isChangeWayToLearnDialogVisible = false))
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
        produceState(state.copy(isSavingInProgress = true, isSaveButtonEnabled = false))
        viewModelScope.launch {
            updateSettingsUseCase.execute(
                defaultNumberToLearn = state.updatedNumberToLearn,
                defaultWayToLearn = state.updatedWayToLearn,
                countOnFirstTryToMoveToLearned = state.updatedCountOnFirstTry,
                isDarkMode = state.updatedDarkMode
            )
            produceState(
                state.copy(
                    updatedNumberToLearn = null,
                    updatedWayToLearn = null,
                    updatedCountOnFirstTry = null,
                    updatedDarkMode = null,
                    isSavingInProgress = false,
                    isSaveButtonEnabled = false
                )
            )
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
        updatedNumberToLearn: Int? = null,
        updatedCountOnFirstTry: Int? = null,
        updatedWayToLearn: WayToLearnDomain? = null,
        updatedDarkMode: Boolean? = null
    ): Boolean {
        val setting = state.currentSettings ?: return false
        val isNumberToLearnChanged = if (updatedNumberToLearn == null) {
            setting.defaultNumberToLearn == state.updatedNumberToLearn
        } else {
            setting.defaultNumberToLearn == updatedNumberToLearn
        }
        val isCountOnFirstTryChanged = if (updatedCountOnFirstTry == null) {
            setting.countOnFirstTryToMoveToLearned == state.updatedCountOnFirstTry
        } else {
            setting.countOnFirstTryToMoveToLearned == updatedCountOnFirstTry
        }
        val isWayToLearnChanged = if (updatedWayToLearn == null) {
            setting.defaultWayToLearn == state.updatedWayToLearn
        } else {
            setting.defaultWayToLearn == updatedWayToLearn
        }
        val isDarkModeChangedChanged = if (updatedDarkMode == null) {
            setting.isDarkMode == state.updatedDarkMode
        } else {
            setting.defaultWayToLearn == updatedWayToLearn
        }
        return isNumberToLearnChanged || isCountOnFirstTryChanged || isWayToLearnChanged
                || isDarkModeChangedChanged
    }

    override fun createInitialState(): State = State()
}
