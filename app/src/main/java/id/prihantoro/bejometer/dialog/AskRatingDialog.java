package id.prihantoro.bejometer.dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import id.prihantoro.bejometer.prefs.UserData;
import id.prihantoro.bejometer.util.DateTimeUtils;

/**
 * Created by Wahyu Prihantoro on 24-Aug-16.
 */
public class AskRatingDialog {
    public static void presentDialog(final Context context, final UserData userData) {
        if (context == null || userData == null || !userData.canGiveRating()) {
            return;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Selamat!")
                .setCancelable(false)
                .setMessage("Anda berhasil menggunakan bejometer dan tebakgender\nMau ceritakan keseruanmu di playsore?")
                .setPositiveButton("Ya, tentu saja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = context.getPackageName();
                        userData.setHasGivingRating(true);
                        userData.setTimeToAskRating(Long.MAX_VALUE);
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userData.setTimeToAskRating(System.currentTimeMillis() + DateTimeUtils.A_DAY);
                    }
                })
                .show();
    }
}
