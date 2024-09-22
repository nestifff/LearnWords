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
                isUpdateLearnProcessEnabled = hasUnsavedData(updatedNumberToLearn = newValue)
            )
        )
    }

    fun onCountOnFirstTryChanged(newValue: String) {
        produceState(
            state.copy(
                updatedCountOnFirstTry = newValue,
                isUpdateLearnProcessEnabled = hasUnsavedData(updatedCountOnFirstTry = newValue)
            )
        )
    }

    fun onWayToLearnSelected(newWayToLearn: WayToLearnDomain) {
        produceState(
            state.copy(
                updatedWayToLearn = newWayToLearn,
                isUpdateLearnProcessEnabled = hasUnsavedData(updatedWayToLearn = newWayToLearn),
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
        val newNumberToLearn = state.updatedNumberToLearn?.toIntOrNull() ?: 0
        val newCountOnFirstTry = state.updatedCountOnFirstTry?.toIntOrNull() ?: 0
        if (newNumberToLearn <= 0 || newCountOnFirstTry <= 0) {
            produceEffect(Effect.ErrorZeroOrEmptyValuesMessage)
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
        updatedWayToLearn: WayToLearnDomain? = null
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

            return isNumberToLearnChanged || isCountOnFirstTryChanged || isWayToLearnChanged
        } catch (ex: Exception) {
            return false
        }
    }

    override fun createInitialState(): State = State()
}
