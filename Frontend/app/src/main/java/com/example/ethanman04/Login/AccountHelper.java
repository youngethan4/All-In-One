package com.example.ethanman04.Login;

import android.database.CursorJoiner;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class AccountHelper {

     AccountHelper(){}

    boolean nameSupportedChars(String name){
        if (name.matches("[A-Za-z]+"))
            return true;
        return false;
    }

    boolean usernameSupportedChars(String username){
        if (username.matches("[0-9A-Za-z]+"))
            return true;
        return false;
    }

    boolean emailSupportedFormat(String email){
         if (email.matches("[0-9A-Za-z]+@[A-za-z]+\\.[A-Za-z]+")){
             return true;
         }
         return false;
    }

    boolean passSupportedChars(String pass){
        if (pass.matches("[0-9A-Za-z!@#$%^&*()_+=?|:;]+"))
            return true;
        return false;
    }

    boolean passLongEnough(String pass){
        if (pass.length() >= 8)
            return true;
        return false;
    }

    boolean passContain2CharSets(String pass){
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i  < pass.length(); i++){
            char c = pass.charAt(i);
            if(Character.isDigit(c))
                hasNumber = true;
            if (Character.isLowerCase(c))
                hasLowerCase = true;
            if (Character.isUpperCase(c))
                hasUpperCase = true;
            if (!Character.isLetterOrDigit(c))
                hasSpecial = true;
        }

        if (hasLowerCase && hasUpperCase)
            return true;
        else if (hasLowerCase && hasNumber)
            return true;
        else if (hasLowerCase && hasSpecial)
            return true;
        else if (hasUpperCase && hasNumber)
            return true;
        else if (hasUpperCase && hasSpecial)
            return true;
        else if (hasNumber && hasSpecial)
            return true;
        return false;
    }

    /**
     * Returns the users password as an SHA-512 hash.
     * @return
     */
    String hashPass(String pass){
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return  hashtext;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
