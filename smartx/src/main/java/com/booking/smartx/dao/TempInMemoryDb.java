package com.booking.smartx.dao;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Async
@Component
public class TempInMemoryDb {
    private HashMap<String, Object> tempRegistrationHolder;

    public TempInMemoryDb(){
        this.tempRegistrationHolder = new HashMap<>();
    }

    public void saveData(String key, Object obj){
        this.tempRegistrationHolder.put(key,obj);
    }

    public void deleteData(String key){
        this.tempRegistrationHolder.remove(key);
    }

    public Object getData(String key){
        return this.tempRegistrationHolder.get(key);
    }

}
