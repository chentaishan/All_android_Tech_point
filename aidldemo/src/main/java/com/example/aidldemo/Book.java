package com.example.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Book.java是一个表示图书信息的类，它实现了Parcelable接口。
 * Book.aidl是Book类在AIDL中的声明。
 * IBookManager.aidl是我们定义的一个接
 * 口，里面有两个方法：getBookList和addBook，
 */
public class Book implements Parcelable {

    public int bookId;
    public String bookName;


    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }



}
