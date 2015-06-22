package cs246.ironmanapp;

import java.sql.Date;

/**
 * Created by Robbie on 6/22/2015.
 */
public class Structs {
    static class Contestant{
        String pk_contestants_id;
        String u_name;
        double percentage;
        // register_date
    }

    static class Entry{
        int pk_entries_id;
        Date entry_date;
        double distance;
        //fk_event int
        // fk_contestants string
        // fk_mode
        //pk_contestants
        // register_date
        String u_name;
        //pk_events_id
        String semester;
        // start / end date
        // pk_mode_id
        String mode;
        String units;
    }

    static class Progress{
        String user;
        String mode;
        double distance;
    }

    static class NewEntryMessage{
        int code;
        String message;

    }

    static class NewUserMessage{
        int code;
        String message;

    }
}


