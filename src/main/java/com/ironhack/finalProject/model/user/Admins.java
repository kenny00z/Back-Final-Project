package com.ironhack.finalProject.model.user;

import jakarta.persistence.Entity;

@Entity
public class Admins extends User{
    public Admins() {
    }

    public Admins(String name, String authPass) {
        super(name, authPass);
    }
}
