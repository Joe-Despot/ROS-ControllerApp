package com.example.controllerapp.objects;

import java.io.Serializable;

public class Model implements Serializable {
    String name;
    String full_ip;

    public Model(String name, String full_ip) {
        this.name = name;
        this.full_ip = full_ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFull_ip(String full_ip) {
        this.full_ip = full_ip;
    }

    public String getName() {
        return this.name;
    }

    public String getFull_ip() {
        return this.full_ip;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return ((Model) o).name.equals(this.name) && ((Model) o).full_ip==this.full_ip;
    }

}
