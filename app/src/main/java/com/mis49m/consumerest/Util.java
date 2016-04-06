package com.mis49m.consumerest;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

    public static String readFile(Context context, int resourceId){

        InputStream inputStream=null;
        InputStreamReader reader=null;
        StringBuilder result = new StringBuilder();

        try {
            
            reader = new InputStreamReader(context.getResources().openRawResource(resourceId));

            int data = reader.read();
            while(data!=-1){
                result.append( (char)data );
                data = reader.read();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(reader!=null) reader.close();
                if(inputStream!=null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result.toString();
    }

}
