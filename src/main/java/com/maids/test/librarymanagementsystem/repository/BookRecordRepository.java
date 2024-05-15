package com.maids.test.librarymanagementsystem.repository;

import com.maids.test.librarymanagementsystem.entity.BookRecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRecordRepository extends JpaRepository<BookRecordsEntity, Long> {

    @Query(value= "SELECT * FROM BOOK_MANAGEMENT_TABLE WHERE ISBN = ?1", nativeQuery = true)
    BookRecordsEntity checkIsbn(String isbn);
}
