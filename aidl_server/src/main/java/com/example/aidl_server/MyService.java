package com.example.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.example.aidl_server.Book;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("myService--->","onCreate");
    }

   IBinder iBinder =  new Book.Stub(){

       @Override
       public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

       }

       @Override
       public void xxx() throws RemoteException {

       }
   };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
