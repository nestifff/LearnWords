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

        val isUpdateLearnProcessEnabled: Boolean = false,

        val isConfirmExitDialogVisible: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object UpdateSuccessMessage : Effect()
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
                isUpdateLearnProcessEnabled = checkIfDataCanBeUpdated(updatedNumberToLearn = newValue)
            )
        )
    }

    fun onCountOnFirstTryChanged(newValue: String) {
        produceState(
            state.copy(
                updatedCountOnFirstTry = newValue,
                isUpdateLearnProcessEnabled = checkIfDataCanBeUpdated(updatedCountOnFirstTry = newValue)
            )
        )
    }

    fun onWayToLearnSelected(newWayToLearn: WayToLearnDomain) {
        produceState(
            state.copy(
                updatedWayToLearn = newWayToLearn,
                isUpdateLearnProcessEnabled = checkIfDataCanBeUpdated(updatedWayToLearn = newWayToLearn),
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
        viewModelScope.launch {
            updateSettingsUseCase.execute(isDarkMode = newValue)
            produceState(state.copy(currentSettings = getSettingsUseCase.execute()))
        }
    }

    fun onUpdateLearnProcessClicked() {
        val currentSettings = state.currentSettings ?: return
        val newNumberToLearn = if (state.updatedNumberToLearn == null) {
            currentSettings.defaultNumberToLearn
        } else {
            state.updatedNumberToLearn!!.toInt()
        }
        val newCountOnFirstTry = if (state.updatedCountOnFirstTry == null) {
            currentSettings.countOnFirstTryToMoveToLearned
        } else {
            state.updatedCountOnFirstTry!!.toInt()
        }
        viewModelScope.launch {
            produceState(state.copy(isUpdateLearnProcessEnabled = false))
            updateSettingsUseCase.execute(
                defaultNumberToLearn = newNumberToLearn,
                defaultWayToLearn = state.updatedWayToLearn,
                countOnFirstTryToMoveToLearned = newCountOnFirstTry,
            )
            produceState(
                state.copy(
                    currentSettings = getSettingsUseCase.execute(),
                    updatedNumberToLearn = null,
                    updatedWayToLearn = null,
                    updatedCountOnFirstTry = null,
                    isUpdateLearnProcessEnabled = false
                )
            )
            produceEffect(Effect.UpdateSuccessMessage)
        }
    }

    fun onBackTriggered() {
        if (checkIfDataCanBeUpdated()) {
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

    private fun checkIfDataCanBeUpdated(
        updatedNumberToLearn: String? = null,
        updatedCountOnFirstTry: String? = null,
        updatedWayToLearn: WayToLearnDomain? = null
    ): Boolean {
        try {
            val settings = state.currentSettings ?: return false

            val numberToLearnToCompare = updatedNumberToLearn ?: state.updatedNumberToLearn
            val isNumberToLearnChangedAndLegal = numberToLearnToCompare?.let {
                it.toInt() != settings.defaultNumberToLearn && it.toInt() > 0
            } ?: false

            val countOnFirstTryToCompare = updatedCountOnFirstTry ?: state.updatedCountOnFirstTry
            val isCountOnFirstTryChangedAndLegal = countOnFirstTryToCompare?.let {
                it.toInt() != settings.countOnFirstTryToMoveToLearned && it.toInt() > 0
            } ?: false

            val wayToLearnToCompare = updatedWayToLearn ?: state.updatedWayToLearn
            val isWayToLearnChanged = wayToLearnToCompare?.let {
                it != settings.defaultWayToLearn
            } ?: false

            return isNumberToLearnChangedAndLegal || isCountOnFirstTryChangedAndLegal
                    || isWayToLearnChanged
        } catch (ex: Exception) {
            return false
        }
    }

    override fun createInitialState(): State = State()
}
