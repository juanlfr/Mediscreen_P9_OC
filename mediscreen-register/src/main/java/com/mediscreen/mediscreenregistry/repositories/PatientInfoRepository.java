package com.mediscreen.mediscreenregistry.repositories;

import com.mediscreen.mediscreenregistry.models.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {
    PatientInfo findByFirstNameAndLastName(String firstName, String lastName);
}
