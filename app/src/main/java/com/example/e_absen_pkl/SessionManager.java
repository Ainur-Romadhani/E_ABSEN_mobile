package com.example.e_absen_pkl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class SessionManager {

    static final String KEY_NIS_SEDANG_LOGIN = "Nis_logged_in";
    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setLoggedInUser(Context context, String nis){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NIS_SEDANG_LOGIN, nis);
        editor.apply();
    }
    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_NIS_SEDANG_LOGIN,"");
    }
    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
        editor.apply();
    }
    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
    }
    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_NIS_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.apply();
    }
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//
//    public SessionManager(Context context){
//        sharedPreferences = context.getSharedPreferences("AppKey",0);
//        editor = sharedPreferences.edit();
//        editor.apply();
//    }
//    public void setLogin(boolean login){
//        editor.putBoolean("KEY_LOGIN",login);
//        editor.commit();
//    }
//
//    public boolean getLogin(){
//        return sharedPreferences.getBoolean("KEY_LOGIN",false);
//    }
//
//    public  void setNis(String nis){
//        editor.putString("KEY_NIS",nis);
//        editor.commit();
//    }
//
//    public String getNis(){
//        return sharedPreferences.getString("KEY_NIS","");
//    }
}
