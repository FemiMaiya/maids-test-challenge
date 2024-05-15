package com.maids.test.librarymanagementsystem.repository;

import com.maids.test.librarymanagementsystem.entity.BorrowRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecordEntity, Long> {

    @Query(value= "SELECT * FROM BORROWING_RECORDS_TABLE WHERE BORROW_ID = ?1", nativeQuery = true)
    BorrowRecordEntity getBorrowDetails(String borrowId);
}
