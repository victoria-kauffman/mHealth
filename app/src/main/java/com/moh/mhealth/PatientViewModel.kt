package com.moh.mhealth

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel(private val repository: PatientRepository) : ViewModel() {
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
}

class PatientViewModelFactory(private val repository: PatientRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}