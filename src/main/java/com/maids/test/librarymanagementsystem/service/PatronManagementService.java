package com.maids.test.librarymanagementsystem.service;

import com.maids.test.librarymanagementsystem.dto.patronManagement.request.AddPatronRequest;
import com.maids.test.librarymanagementsystem.dto.patronManagement.request.UpdatePatronRequest;
import com.maids.test.librarymanagementsystem.dto.patronManagement.response.AddPatronResponse;
import com.maids.test.librarymanagementsystem.dto.patronManagement.response.UpdatePatronResponse;
import com.maids.test.librarymanagementsystem.entity.PatronManagementEntity;
import com.maids.test.librarymanagementsystem.exceptions.InternalServerException;
import com.maids.test.librarymanagementsystem.exceptions.MyCustomException;
import com.maids.test.librarymanagementsystem.repository.PatronManagementReposiory;
import com.maids.test.librarymanagementsystem.util.Constant;
import com.maids.test.librarymanagementsystem.util.HelperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class PatronManagementService {

    //Constant utility class declaration for validations and regex
    private final Constant constant;

    //Helper utility class declaration
    private final HelperUtil helperUtil;

    //Patron management repo declaration
    private final PatronManagementReposiory patronManagementReposiory;

    public Object getPatronById (String id) {
        log.info("\nGETTING ALL PATRONS......");

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID PATRON ID PASSED. KINDLY INPUT A VALID PATRON ID";
            throw new MyCustomException(message);
        }

        Optional<PatronManagementEntity> patron;
        try {
            patron = patronManagementReposiory.findById(Long.parseLong(id));
            log.info("PATRONS RETURNED: " + patron);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (patron.isEmpty()) {
            log.info("Patron does not exist");
            throw new MyCustomException("PATRON WITH ID PASSED DOES NOT EXIST");
        }

        return patron;
    }

    public Object addPatron (AddPatronRequest request) {
        log.info("\nADDING PATRON......");
        log.info("REQUEST PASSED {} ", request);


        PatronManagementEntity email;
        try {
            email = patronManagementReposiory.checkEmail(request.getEmail());
            log.info("EMAIL RETURNED: " + email);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        if (email != null) {
            log.info("EMAIL exists");
            throw new MyCustomException("EMAIL PROVIDED ALREADY EXISTS.");
        }

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        //Building book entity
        PatronManagementEntity patronEntity = PatronManagementEntity.builder()
                .firstName(request.getFirstName().toUpperCase())
                .lastName(request.getLastName().toUpperCase())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateCreated(date)
                .timeCreated(time)
                .createdBy(request.getCreatedBy())
                .dateModified(date)
                .timeModified(time)
                .modifiedBy(request.getCreatedBy())
                .build();

        try {
            patronManagementReposiory.save(patronEntity);
        }catch (Exception exception) {
            log.info("UNABLE TO SAVE RECORDS TO DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        //Building response
        return AddPatronResponse.builder()
                .patronId(String.valueOf(patronEntity.getId()))
                .fullName(patronEntity.getFirstName() + " " + patronEntity.getLastName())
                .email(patronEntity.getEmail())
                .phoneNumber(patronEntity.getPhoneNumber())
                .dateCreated(patronEntity.getDateCreated())
                .dateCreated(patronEntity.getDateCreated())
                .timeCreated(patronEntity.getTimeCreated())
                .createdBy(patronEntity.getCreatedBy())
                .build();
    }

    public Object updatePatronDetails (String id, UpdatePatronRequest request) {
        log.info("\nUPDATING PATRON......");
        log.info("REQUEST PASSED {} ", request);

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID PATRON ID PASSED. KINDLY INPUT A VALID PATRON ID";
            throw new MyCustomException(message);
        }

        //Returning patron entity
        Optional<PatronManagementEntity> patron;
        try {
            patron = patronManagementReposiory.findById(Long.parseLong(id));
            log.info("PATRON RETURNED: " + patron);
        }catch (Exception exception) {
            log.info("UNABLE TO ACCESS DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        PatronManagementEntity patronRecordEntity = modifyBookRecords(request, patron);

        patronManagementReposiory.save(patronRecordEntity);

        //Building response
        return UpdatePatronResponse.builder()
                .message("PATRON UPDATED SUCCESSFULLY")
                .dateModified(patronRecordEntity.getDateModified())
                .timeModified(patronRecordEntity.getTimeModified())
                .modifiedBy(patronRecordEntity.getModifiedBy())
                .build();
    }

    private PatronManagementEntity modifyBookRecords(UpdatePatronRequest request, Optional<PatronManagementEntity> patron) {

        log.info("MODIFYING BOOK ENTITY");

        //Getting date and time
        String date = helperUtil.getDate();
        String time = helperUtil.getTime();

        if (patron.isEmpty()) {
            log.info("Patron does not exist");
            throw new MyCustomException("PATRON WITH ID PASSED DOES NOT EXIST");
        }
        //Modifying book entity returned
        PatronManagementEntity patronRecordEntity = patron.get();

        patronRecordEntity.setFirstName(request.getFirstName().toUpperCase());
        patronRecordEntity.setLastName(request.getLastName().toUpperCase());
        patronRecordEntity.setEmail(request.getEmail());
        patronRecordEntity.setPhoneNumber(request.getPhoneNumber());
        patronRecordEntity.setModifiedBy(request.getModifiedBy());
        patronRecordEntity.setDateModified(date);
        patronRecordEntity.setTimeModified(time);
        return patronRecordEntity;
    }

    public Object deletePatron (String id) {
        log.info("\nDELETING PATRON......");

        //Validating ID passed using multithreading
        CompletableFuture<Boolean> idVal = CompletableFuture.supplyAsync(() ->
                this.validateId(id));

        boolean idValRes = idVal.join();

        if (!idValRes) {
            log.info("Invalid ID passed");
            String message = "INVALID BOOK ID PATRON. KINDLY INPUT A VALID PATRON ID";
            throw new MyCustomException(message);
        }

        try {
            patronManagementReposiory.deleteById(Long.parseLong(id));
        }catch (Exception exception) {
            log.info("UNABLE TO DELETE RECORD FROM DB. ERR: " + exception.getMessage());
            throw new InternalServerException("INTERNAL SERVER ERROR. KINDLY TRY AGAIN LATER!");
        }

        return "PATRON DELETED SUCCESSFULLY";
    }

    private boolean validateId(String id){

        return constant.validateId(id);
    }
}
