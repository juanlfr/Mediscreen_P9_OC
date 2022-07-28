package com.mediscreen.mediscreenregister.repositories;

import com.mediscreen.mediscreenregister.models.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {
    PatientInfo findByFirstNameAndLastName(String firstName, String lastName);
}
