package com.mproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "DataBaseHelper";

    private static String DB_PATH="";
    private static String DB_NAME="AddressDB.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;

    public DataBaseHelper(Context context){
        super(context,DB_NAME,null,1);


        if(Build.VERSION.SDK_INT>=17){
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else{
            DB_PATH = "/data/data/" + context.getPackageName() +"/databases/";

        }
            this.mContext = context;

        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void createDataBase() throws IOException
    {
        boolean mdataBaseExist  = checkDataBase();
        if(!mdataBaseExist){
            this.getReadableDatabase();
            this.close();

           try
           {
               copyDataBase();
               Log.e(TAG,"createDatabase database created");
           }
           catch (IOException mIOException){
               throw new Error("ErrorCopyingDataBase");
           }

        }
    }

    private void copyDataBase() throws IOException
    {
        InputStream minput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;
        OutputStream moutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = minput.read(mBuffer))>0)
        {
            moutput.write(mBuffer,0,mLength);
        }
        moutput.flush();
        moutput.close();
        minput.close();
    }

    private boolean checkDataBase(){
        File dbfile = new File(DB_PATH+DB_NAME);
        return dbfile.exists();
    }

    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;

        mDataBase = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase!=null;
    }
    @Override
    public synchronized void close() {
        if (mDataBase != null){
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG,"onOpen() : DB Opening");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG,"onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG,"onUpgread() : DB Schema Modified and Excuting onCreate()");
    }


}
