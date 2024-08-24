package com.hosp.med.voll.util;

import com.hosp.med.voll.domain.model.exception.UnactiveException;

public class ServiceUtils {

    public static void isUnactive(boolean recordStatus) throws UnactiveException {

        if (!recordStatus) {
            throw new UnactiveException();
        }
    }

}
