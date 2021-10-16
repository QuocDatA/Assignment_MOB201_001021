package com.quocdat.assignment_mob201.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.quocdat.assignment_mob201.utilities.Constants.*;

import androidx.annotation.Nullable;

public class AsmDB  extends SQLiteOpenHelper {

    //design pattern
    //singleton
    private static AsmDB dbInstance;
    public static synchronized AsmDB getDbInstance(Context context){
        if (dbInstance == null) dbInstance = new AsmDB(context);
        return dbInstance;
    }

    private AsmDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, PASSWORD TEXT, NAME TEXT, PHONE TEXT, DOB TEXT, ROLE INTEGER)";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS COURSES (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, CODE TEXT, TEACHER TEXT)";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATE TEXT, TIME TEXT, ADDRESS TEXT, MEET TEXT, TYPE INTEGER, " +
                "COURSE_ID INTEGER, FOREIGN KEY(COURSE_ID) REFERENCES COURSES(ID))";
        db.execSQL(q);

        q = "CREATE TABLE IF NOT EXISTS ENROLLS (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATE TEXT, USER_ID INTEGER, COURSE_ID INTEGER, " +
                "FOREIGN KEY(USER_ID) REFERENCES USERS(ID), FOREIGN KEY(COURSE_ID) REFERENCES COURSES(ID))";
        db.execSQL(q);

        q = "INSERT INTO USERS(USERNAME, PASSWORD, NAME, PHONE, DOB, ROLE) VALUES " +
                "('1', '1', 'Nguyen Van Thu', '0324555781', '08/08/1999', 1)," +
                "('2', '2', 'Tran Van Ba', '0346998742', '07/05/2000', 0)," +
                "('3', '3', 'Le Van Si', '0345228711', '07/07/2001', 0)," +
                "('4', '4', 'Nguyen Thi Thao', '0346923564', '10/05/2000', 0)," +
                "('5', '5', 'Tran Nghia', '0346469842', '07/12/2001', 0)," +
                "('6', '6', 'Vo Van Bao', '0356298742', '20/07/2000', 0)," +
                "('7', '7', 'Nguyen Ba Hoc', '0346752042', '09/09/2000', 0)," +
                "('8', '8', 'Nguyen Hao', '0346999984', '12/07/2000', 0)," +
                "('9', '9', 'Tran Huy', '0355620742', '03/12/1998', 0)," +
                "('10', '10', 'Nguyen Quoc Huy', '0346562742', '04/04/2000', 0)";
        db.execSQL(q);

        q = "INSERT INTO COURSES(NAME, CODE, TEACHER) VALUES " +
                "('Java1', 'JV01', 'Nguyen Van Huy')," +
                "('Java2', 'JV02', 'Nguyen Tan Luc')," +
                "('Java3', 'JV03', 'Tran Van Loc')," +
                "('C#', 'C01', 'Le Hong Quang')," +
                "('Android co ban', 'AD01', 'Vo Luan')," +
                "('Android nang cao', 'AD02', 'Le Nguyen Quoc Huy')," +
                "('Python', 'P01', 'Cao Thai Son')," +
                "('IOS', 'I01', 'Nguyen Ba Hoc')," +
                "('Unity', 'U01', 'Nguyen Vo Hong Toan')," +
                "('HTML&CSS', 'HC01', 'Le Tran Hoa')";
        db.execSQL(q);

        q = "INSERT INTO SCHEDULES(DATE, TIME, ADDRESS, MEET, TYPE, COURSE_ID) VALUES " +
                "('01-09-2021', '7:00 - 9:00', 'P305', 'http://meet.google.com/top-ynme-hka', 1, 1)," +
                "('01-09-2021', '9:00 - 11:00', 'P405', 'http://meet.google.com/top-ynme-hka', 1, 2)," +
                "('01-09-2021', '13:00 - 15:00', 'P307', 'http://meet.google.com/top-ynme-hka', 1, 4)," +
                "('01-09-2021', '15:00 - 17:00', 'P505', 'http://meet.google.com/top-ynme-hka', 1, 8)," +
                "('01-10-2021', '7:00 - 9:00', 'P403', 'http://meet.google.com/top-ynme-hka', 1, 3)," +
                "('01-10-2021', '9:00 - 11:00', 'P306', 'http://meet.google.com/top-ynme-hka', 1, 5)," +
                "('01-10-2021', '13:00 - 15:00', 'P304', 'http://meet.google.com/top-ynme-hka', 1, 7)," +
                "('01-10-2021', '15:00 - 17:00', 'P303', 'http://meet.google.com/top-ynme-hka', 1, 9)," +
                "('01-11-2021', '7:00 - 9:00', 'P305', 'http://meet.google.com/top-ynme-hka', 2, 1)," +
                "('01-11-2021', '9:00 - 11:00', 'P405', 'http://meet.google.com/top-ynme-hka', 2, 2)";
        db.execSQL(q);

        q = "INSERT INTO ENROLLS(USER_ID, COURSE_ID) VALUES" +
                "(2, 1)," +
                "(2, 6)," +
                "(5, 3)," +
                "(6, 2)," +
                "(8, 2)," +
                "(6, 5)," +
                "(8, 3)," +
                "(4, 7)," +
                "(2, 2)," +
                "(7, 5)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS SCHEDULES");
            db.execSQL("DROP TABLE IF EXISTS ENROLLS");
            db.execSQL("DROP TABLE IF EXISTS USERS");
            db.execSQL("DROP TABLE IF EXISTS COURSES");
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
