// IBook.aidl
package com.example.aidldemo;

import com.example.aidldemo.Book;
interface IBook {

           List<Book> getBookList();
           void addBook(in Book book);
}