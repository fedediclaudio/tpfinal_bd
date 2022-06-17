package com.bd.tpfinal.event;

import org.springframework.util.ReflectionUtils;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;

public class FieldCallback implements ReflectionUtils.FieldCallback {
    public boolean isIdFound() {
        return idFound;
    }

    public void setIdFound(boolean idFound) {
        this.idFound = idFound;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

        ReflectionUtils.makeAccessible(field);
        if(field.isAnnotationPresent(Id.class)) {
            setIdFound(true);
        }
    }

    private boolean idFound;
}
