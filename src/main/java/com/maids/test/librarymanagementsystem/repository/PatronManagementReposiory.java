package com.maids.test.librarymanagementsystem.repository;

import com.maids.test.librarymanagementsystem.entity.PatronManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatronManagementReposiory extends JpaRepository<PatronManagementEntity, Long> {

    @Query(value= "SELECT * FROM PATRON_MANAGEMENT_TABLE WHERE EMAIL = ?1", nativeQuery = true)
    PatronManagementEntity checkEmail(String email);
}
