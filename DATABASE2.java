public class DBManager {

///////////////////////////////////////////////////////////////////////
//  Data
//////////////////////////////////////////////////////////////////////

  private static final String TAG = "DBManager";

// Students Fields

  public static final String KEY_STUDENTNO = "_id";
  public static final int COL_ROWID = 0;
  public static final String KEY_FNAME = "fname";
  public static final String KEY_LNAME = "lname";
  public static final String KEY_GENDER = "gender";
	public static final String KEY_COURSE = "course";
	public static final int KEY_AGE = "age";
  public static final String KEY_ADDRESS = "address";

  public static final int COL_FNAME = 1;
  public static final int COL_LNAME = 2;
  public static final int COL_GENDER = 3;
  public static final int COL_COURSE = 4;
  public static final int COL_AGE = 5;
  public static final int COL_ADDRESS = 6;


public static final String[] STU_KEYS = new String[] {KEY_STUDENTNO, KEY_FNAME, KEY_LNAME, KEY_GENDER, KEY_COURSE, KEY_AGE, KEY_ADDRESS};

// Exam Fields

  public static final String KEY_EXAMNO = "_id";
  //public static final int COL_ROWID = 0;
  public static final String KEY_EXAMNAME = "examName";
  public static final String KEY_DATE = "date";
  public static final String KEY_TIME = "time";
	public static final String KEY_LOCATION = "location";

  public static final int COL_NAME = 1;
  public static final int COL_DATE = 2;
  public static final int COL_TIME = 3;
  public static final int COL_LOCATION = 4;


public static final String[] EXAM_KEYS = new String[] {KEY_EXAMNO, KEY_EXAMNAME, KEY_DATE, KEY_TIME, KEY_LOCATION};


// DB info: Names DB and Table
public static final String DATABASE_NAME = "studentApp";
public static final String DATABASE_STUDENTTABLE = "studentTable";
public static final String DATABASE_EXAMTABLE = "examTable";

//version DB
public static final int DATABASE_VERSION = 1;

//copy for second db table with new defined values
private static final String DATABASE_CREATE_SQL =
			"create table " + DATABASE_STUDENTTABLE
			+ " (" + KEY_STUDENTNO + " integer primary key autoincrement, "
      + KEY_FNAME + " text not null, "
      + KEY_LNAME + " text not null, "
      + KEY_GENDER + " text not null, "
      + KEY_COURSE + " text not null, "
			+ KEY_AGE + " integer not null, "
			+ KEY_ADDRESS + " string not null"
      + ");";

//VALUES FOR EXAM TABLE

  private static final String DATABASE_CREATE_SQL =
    "create table " + DATABASE_EXAMTABLE
    + " (" + KEY_EXAMNO + " integer primary key autoincrement, "
    + KEY_EXAMNAME + " text not null, "
    + KEY_DATE + " text not null, "
    + KEY_TIME + " text not null, "
    + KEY_LOCATION + " string not null"
    + ");";

// DEFINE
  private final Context context;
  private DatabaseHelper myDBHelper;
  private SQLiteDatabase db;

/////////////////////////////////////////////////////////////////////
//	Methods:
/////////////////////////////////////////////////////////////////////


  public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}


// Open the database connection.

  public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

// Close the database connection.
  public void close() {
    myDBHelper.close();
  }

// Add a new set of values to the database.
	public long insertRowStudent(String fname, String lname, String gender, String course, int age, String address) {

// Create Students row's data:

  		ContentValues initialValues = new ContentValues();
  		initialValues.put(KEY_FNAME, fname);
      initialValues.put(KEY_LNAME, lname);
      initialValues.put(KEY_GENDER, gender);
      initialValues.put(KEY_COURSE, course);
  		initialValues.put(KEY_AGE, age);
  		initialValues.put(KEY_ADDRESS, address);


// Insert it into the database.
  		return db.insert(DATABASE_STUDENTTABLE, null, initialValues);
  	}


// Add a new set of values to the database.
    	public long insertRowExam(String examName, String date, String time, String location) {

    // Create Students row's data:

      		ContentValues initialValues = new ContentValues();
      		initialValues.put(KEY_EXAMNAME, examName);
          initialValues.put(KEY_DATE, date);
          initialValues.put(KEY_TIME, time);
          initialValues.put(KEY_LOCATION, location);



      		// Insert it into the database.
      		return db.insert(DATABASE_EXAMTABLE, null, initialValues);
      	}

// Delete a row from the database

      public boolean deleteRow(long studentNo) {
    	String where = KEY_STUDENTNO + "=" + studentNo;
    	return db.delete(DATABASE_STUDENTTABLE, where, null) != 0;
    	}

    	public void deleteAll() {
    		Cursor c = getAllRows();
    		long studentNo = c.getColumnIndexOrThrow(KEY_STUDENTNO);
    		if (c.moveToFirst()) {
    			do {
    				deleteRow(c.getLong((int) studentNo));
    			  }
            while (c.moveToNext());
    		}
    		c.close();
    	}

//////////////////////////////////////////////////////////////////////////////////

// Delete a row from the database

  public boolean deleteRowExam(long examNo) {
    String where = KEY_EXAMNO + "=" + examNo;
    return db.delete(DATABASE_EXAMTABLE, where, null) != 0;
  }

  public void deleteAllExam() {
  	Cursor c = getAllRows();
  	long examNo = c.getColumnIndexOrThrow(KEY_EXAMNO);
  	if (c.moveToFirst()) {
  		do {
  			deleteRow(c.getLong((int) examNo));
  	  }
      while (c.moveToNext());
  		}
    		c.close();
    	}

// Return all data in the database.
      public Cursor getAllRowsExam() {
      	String where = null;
      	Cursor c = 	db.query(true, DATABASE_EXAMTABLE, EXAM_KEYS, where, null, null, null, null, null);
      		if (c != null) {
      			c.moveToFirst();
      		}
      		return c;
      }

      public Cursor getAllRowsStudent() {
      	String where = null;
      	Cursor c = 	db.query(true, DATABASE_STUDENTTABLE, STU_KEYS, where, null, null, null, null, null);
      		if (c != null) {
      			c.moveToFirst();
      		}
      		return c;
      }

// Get a specific row (by studentNo)
    public Cursor getRowStudent(long studentNo) {
      String where = KEY_STUDENTNO + "=" + studentNo;
      Cursor c = 	db.query(true, DATABASE_STUDENTTABLE, STU_KEYS, where, null, null, null, null, null);
      if (c != null) {
      	c.moveToFirst();
      }
      return c;
    }


// Get a specific row (by examNo)
  public Cursor getRowExam(long examNo) {
    String where = KEY_EXAMNO + "=" + examNo;
    Cursor c = 	db.query(true, DATABASE_EXAMTABLE, EXAM_KEYS, where, null, null, null, null, null);
    if (c != null) {
    	c.moveToFirst();
    }
    return c;
  }

// Edit
  public boolean updateRowStudent(String fname, String lname, String gender, String course, int age, String address) {
    String where = KEY_STUDENTNO + "=" + studentNo;

    // update row's data:
    		newValues newValues = new ContentValues();
        newValues.put(KEY_FNAME, fname);
        newValues.put(KEY_LNAME, lname);
        newValues.put(KEY_GENDER, gender);
        newValues.put(KEY_COURSE, course);
    		newValues.put(KEY_AGE, age);
    		newValues.put(KEY_ADDRESS, address);

    		// Insert it into the database.
    		return db.update(DATABASE_STUDENTTABLE, newValues, where, null) != 0;
    	}


// Edit
  public boolean updateRowExam(String examName, String date, String time, String location) {
    String where = KEY_EXAMNO + "=" + examNo;
// update row's data:
		newValues newValues = new ContentValues();
    newValues.put(KEY_EXAMNAME, examName);
    newValues.put(KEY_DATE, date);
    newValues.put(KEY_TIME, time);
    newValues.put(KEY_LOCATION, location);

// Insert it into the database.
	 return db.update(DATABASE_EXAMTABLE, newValues, where, null) != 0;
  }


/////////////////////////////////////////////////////////////////////
//	Private Helper
/////////////////////////////////////////////////////////////////////

  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
     super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

  @Override
  public void onCreate(SQLiteDatabase _db) {
    _db.execSQL(DATABASE_CREATE_SQL);
  }

  @Override
  public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
    Log.w(TAG, "Upgrading application's database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data!");

// Destroy old database:
    _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_STUDENTTABLE + DATABASE_EXAMTABLE);

// Recreate new database:
    onCreate(_db);
    }
	}
}
