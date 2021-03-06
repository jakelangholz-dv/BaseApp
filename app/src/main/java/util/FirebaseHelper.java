package util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Entry;
import model.User;

/**
 * Created by langholz jacob on 5/23/16.
 */

public class FirebaseHelper {

    //TABLE NAMES
    public static String ENTRY = "entry";
    public static String USER = "user";

    private FirebaseDatabase m_fdb;

    public FirebaseHelper() {
        m_fdb = FirebaseDatabase.getInstance();
    }

    public void write(final Object object, final String tableRef) {
        DatabaseReference ref = m_fdb.getReference(tableRef);
        ref.push().setValue(object);
    }

}
