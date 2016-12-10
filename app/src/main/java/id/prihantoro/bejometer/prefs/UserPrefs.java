package id.prihantoro.bejometer.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
@SharedPref
public interface UserPrefs {
    @DefaultLong(0)
    long timeToAskRating();

    @DefaultBoolean(false)
    boolean hasGiveRating();

    @DefaultBoolean(false)
    boolean hasTryBejometer();

    @DefaultBoolean(false)
    boolean hasTryTebakGender();

    @DefaultString("")
    String arrBejoResponse();

    @DefaultInt(0)
    int tryBejometerCount();

    @DefaultInt(0)
    int tryTebagGenderCount();
}
