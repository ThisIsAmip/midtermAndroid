package com.example.btquatrinh_4.DTO;

import java.io.Serializable;

public class Social implements Serializable {
    private String type;
    private String id;

    public Social() {
    }

    public Social(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
