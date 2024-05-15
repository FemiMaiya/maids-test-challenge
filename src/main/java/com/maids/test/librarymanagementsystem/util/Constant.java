package com.maids.test.librarymanagementsystem.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Constant {

    public boolean validateId(String id) {

        log.info("Running...... ID regex");
        log.info("ID passed: " + id);
        String cifRegex = "[0-9]";
        boolean valResult = true;

        try{
            if (((id == null)) || id.equalsIgnoreCase("null")
                    || id.trim().isEmpty()
                    || (!id.matches(cifRegex))) {
                valResult = false;
            }
        }

        catch(Exception exception){
            log.info("error running ID validation "+ exception.getMessage());
        }
        return valResult;
    }

}
