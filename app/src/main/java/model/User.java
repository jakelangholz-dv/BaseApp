package model;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

/**
 * Created by langholz jacob on 5/23/16.
 */

public class User {

    private String email;
    private UUID uuid;

    public User(final FirebaseUser firebaseUser) {

    }

    public String getEmail() {return email;}
    protected UUID getUUID() {return uuid;}

}
