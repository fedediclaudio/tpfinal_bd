package com.bd.tpfinal.helpers;

import com.bd.tpfinal.exceptions.parameters.IdConversionException;

public class IdConvertionHelper {

    public static Long convert(String id) throws IdConversionException {
        if (id == null)
            throw new IdConversionException();
        return Long.parseLong(id);
    }
}
