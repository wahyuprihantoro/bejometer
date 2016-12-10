package id.prihantoro.bejometer.prefs;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Wahyu Prihantoro on 24-Aug-16.
 */
@EBean
public class UserData {
    public static final String ARRBEJOMETERSPLIT = "!@#$%.&*(";
    @Pref
    UserPrefs_ userPrefs;

    public void setHasGivingRating(Boolean hasGivingRating) {
        userPrefs.edit()
                .hasGiveRating()
                .put(hasGivingRating)
                .apply();
    }

    public boolean hasGivingRating() {
        return userPrefs.hasGiveRating().get();
    }

    public void setHasTryBejometer(boolean hasTryBejometer) {
        userPrefs.edit()
                .hasTryBejometer()
                .put(hasTryBejometer)
                .apply();
    }

    public boolean hasTryBejometer() {
        return userPrefs.hasTryBejometer().get();
    }

    public void setHasTryTebakGender(boolean hasTryTebakGender) {
        userPrefs.edit()
                .hasTryTebakGender()
                .put(hasTryTebakGender)
                .apply();
    }

    public boolean hasTryTebakGender() {
        return userPrefs.hasTryTebakGender().get();
    }

    public String getArrBejoResponse() {
        return userPrefs.arrBejoResponse().get();
    }

    public void setArrBejoResponse(String arrBejoResponse) {
        userPrefs.edit()
                .arrBejoResponse()
                .put(arrBejoResponse)
                .apply();
    }

    public void addBejoResponse(String bejoResponse) {
        userPrefs.edit()
                .arrBejoResponse()
                .put(bejoResponse + ARRBEJOMETERSPLIT + getArrBejoResponse())
                .apply();
    }

    public long getTimeToAskRating() {
        return userPrefs.timeToAskRating().get();
    }

    public void setTimeToAskRating(long timeToAskRating) {
        userPrefs.edit().timeToAskRating()
                .put(timeToAskRating)
                .apply();
    }

    public int getTryBejometerCount() {
        return userPrefs.tryBejometerCount().get();
    }

    public int getTryTebakGenderCount() {
        return userPrefs.tryTebagGenderCount().get();
    }

    public void incTebakGenderCount() {
        userPrefs.edit()
                .tryTebagGenderCount()
                .put(userPrefs.tryTebagGenderCount().get() + 1).apply();
        if (userPrefs.tryTebagGenderCount().get() > 1) {
            setHasTryTebakGender(true);
        }
    }

    public void incBejometerCount() {
        userPrefs.edit()
                .tryBejometerCount()
                .put(userPrefs.tryBejometerCount().get() + 1).apply();
        if (userPrefs.tryBejometerCount().get() > 1) {
            setHasTryBejometer(true);
        }
    }

    public boolean canGiveRating() {
        return !hasGivingRating() && hasTryTebakGender() && hasTryBejometer() && getTimeToAskRating() <= System.currentTimeMillis();
    }
}
