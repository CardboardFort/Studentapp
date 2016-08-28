package uws.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "School";
    public static final String DB_TABLE = "student";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (studentNo INTEGER, fName TEXT, lName TEXT, gender TEXT, course TEXT, age int, address TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(Integer c, String n, Integer p) {
        synchronized(this.db) {

            ContentValues newStudent = new ContentValues();
            newStudent.put("studentNo", c);
            newStudent.put("fName", n);
            newStudent.put("lName", n);
            newStudent.put("gender", n);
            newStudent.put("course", n);
            newStudent.put("age", p);
            newStudent.put("address", n);
            try {
                db.insertOrThrow(DB_TABLE, null, newStudent);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retrieveRows() {
        ArrayList<String> studentRows = new ArrayList<String>();
        String[] columns = new String[] {"studentNo" , "fName" , "lName" , "gender" , "course" , "age" , "address"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            StudentRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", " + Float.toString(cursor.getFloat(2)));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return StudentRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Student table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TALBE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
