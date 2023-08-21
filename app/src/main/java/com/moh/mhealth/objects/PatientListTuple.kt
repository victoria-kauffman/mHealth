package com.moh.mhealth.objects

import java.time.LocalDate

class PatientListTuple {
    @JvmField
    var pid = 0
    @JvmField
    var name: String? = null
    @JvmField
    var dob: LocalDate? = null
}