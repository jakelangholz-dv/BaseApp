package model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by langholz jacob on 5/23/16.
 */

public class Entry {

    private UUID entryID;
    private Date date;
    private UUID userID;

    public Entry(final UUID userID, final Date date) {
        this.entryID = UUID.randomUUID();
        this.userID = userID;
        this.date = date;
    }

    public UUID getEntryID() {return entryID;}
    public Date getDate() {return date;}
    public UUID getUserID() {return userID;}
}