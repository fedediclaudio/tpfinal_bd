package com.bd.tpfinal.event;

import com.bd.tpfinal.annotation.CascadePersist;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class CascadeCallback implements ReflectionUtils.FieldCallback {

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public CascadeCallback(Object source, MongoOperations mongoOperations) {
        this.source = source;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);
        if(field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadePersist.class)) {
            final Object fieldValue = field.get(getSource());
            if(fieldValue != null) {
                final FieldCallback fieldCallback = new FieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), fieldCallback);
            getMongoOperations().save(fieldValue);
            }
        }
    }
    private Object source;
    private MongoOperations mongoOperations;
}


