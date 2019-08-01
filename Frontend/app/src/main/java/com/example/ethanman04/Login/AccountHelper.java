package com.example.ethanman04.Login;

import android.view.View;
import android.widget.EditText;

import com.example.ethanman04.allone.R;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class AccountHelper {

     AccountHelper(){}

     boolean checkUsername(String username){
        if (username.matches("[0-9A-Za-z]+"))
            return true;
        return false;
    }

    boolean checkName(String name){
        if (name.matches("[A-Za-z]+"))
            return true;
        return false;
    }

    boolean checkEmail(String email){
         if (email.matches("[0-9A-Za-z]+@[A-za-z]+\\.[A-Za-z]+")){
             return true;
         }
         return false;
    }

    /**
     * Returns the users password as an MD5 hash.
     * @return
     */
    String hashPass(String pass){
        MessageDigest digest;
        if (pass.matches("[0-9A-Za-z!@#$%^&*()_+=?|:;]+")){
            try
            {
                digest = MessageDigest.getInstance("MD5");
                digest.update(pass.getBytes(Charset.forName("US-ASCII")),0,pass.length());
                byte[] magnitude = digest.digest();
                BigInteger bi = new BigInteger(1, magnitude);
                return  String.format("%0" + (magnitude.length << 1) + "x", bi);
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

}
