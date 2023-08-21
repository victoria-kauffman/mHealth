package com.moh.mhealth.patientdatabase

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.moh.mhealth.objects.Patient
import com.moh.mhealth.objects.PatientListTuple
import kotlinx.coroutines.launch

class PatientViewModel(
    private val repository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val allPatients : LiveData<List<Patient>> = repository.allPatients.asLiveData()
    val allPatientsIntro : LiveData<List<PatientListTuple>> = repository.allPatientIntro.asLiveData()
    val loadedPatients = MutableLiveData<List<PatientListTuple>>()
    val patientById = MutableLiveData<List<Patient>>()
    // loadPatients(name: String)
    // getPatient(pid: Int)

    fun insert(patient: Patient) = viewModelScope.launch {
        repository.insert(patient)
    }

    fun updatePatient(patient: Patient) = viewModelScope.launch {
        repository.updatePatient(patient)
    }

    fun loadPatients(name: String) = viewModelScope.launch {
        loadedPatients.value = repository.loadPatients(name)
    }

    fun getPatient(pid: Int) = viewModelScope.launch {
        patientById.value = repository.getPatient(pid)
    }
    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return PatientViewModel(
                    (application as PatientApplication).repository,
                    savedStateHandle
                ) as T
            }
        }
    }
}