package ommrepublic.com.ledieo;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by admin on 9/23/15.
 */
public class PhoneInfo  {
    public static String getMyPhoneNumber(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getLine1Number();
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String getMyIMEI(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getDeviceId();
        }
        catch (Exception e) {
            return "";
        }
    }
    public static String getMyNetworkOperator(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getNetworkOperatorName();
        }
        catch (Exception e) {
            return "";
        }
    }

    public static String getMySIMSerial(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getSimSerialNumber();
        }
        catch (Exception e) {
            return "";
        }
    }
    public static String getMyIMSI(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getSubscriberId();
        }
        catch (Exception e) {
            return "";
        }
    }
    public static String getMyVoiceMailNumberI(Context a){
        try {
            TelephonyManager mTelephonyMgr;
            mTelephonyMgr = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getVoiceMailNumber();
        }
        catch (Exception e) {
            return "";
        }
    }
}
