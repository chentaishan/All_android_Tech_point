package com.example.test_xmlpullparse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            InputStream inputStream = getAssets().open("activity_main.xml");
            parser.setInput(inputStream, null);

            int type =0;
            //next() 游标指向下一个对象，同时获取类型
            while ((type = parser.next())!=XmlPullParser.END_DOCUMENT){


                switch (type){
                    case XmlPullParser.START_TAG:
                        int depth = parser.getDepth();
                        Log.d(TAG, "START_TAG: "+depth);
                        String name = parser.getName();
                        Log.d(TAG, "控件名称: "+name);
                        int nscount = parser.getNamespaceCount(1);
                        for (int i = 0; i < nscount; i++) {
                            //获取命名前缀 通过前缀获取值
                            String nspace = parser.getNamespacePrefix(i)+ "="+parser.getNamespace(parser.getNamespacePrefix(i));
                            Log.d(TAG, "命名空间: "+nspace);
                        }
                        //通过属性数量，获取每个属性
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String s =parser.getAttributePrefix(i)+":"+ parser.getAttributeName(i)+"="+parser.getAttributeValue(i);
                            Log.d(TAG, "属性:"+s);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "END_TAG: ");
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(TAG, "START_DOCUMENT: ");
                        break;
                    case XmlPullParser.TEXT:
                        Log.d(TAG, "TEXT: ");
                        break;
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
