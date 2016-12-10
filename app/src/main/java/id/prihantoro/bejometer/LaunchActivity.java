package id.prihantoro.bejometer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.crash.FirebaseCrash;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import id.prihantoro.bejometer.api.BejoRequest;
import id.prihantoro.bejometer.api.Retrofit;
import id.prihantoro.bejometer.api.eventresult.BejoLaunchResult;
import id.prihantoro.bejometer.api.respone.BejoResponse;
import id.prihantoro.bejometer.api.service.BejoService;
import id.prihantoro.bejometer.custom.CustomCallback;
import id.prihantoro.bejometer.util.DateTimeUtils;
import id.prihantoro.bejometer.util.HashUtils;

/**
 * Created by Wahyu Prihantoro on 21-Aug-16.
 */
@EActivity(R.layout.activity_launch)
public class LaunchActivity extends AppCompatActivity {
    @Bean
    Retrofit retrofit;
    String tanggal;

    @AfterViews
    void init() {
        tanggal = DateTimeUtils.getLocalDate(new Date(System.currentTimeMillis()));
        BejoRequest bejoRequest = new BejoRequest();
        String date = "BADAK-" + DateTimeUtils.getSimpleDateFormat(new Date(System.currentTimeMillis()));
        retrofit.getService(BejoService.class)
                .njaluk(HashUtils.md5(date), bejoRequest.getMultipleQuery())
                .enqueue(new CustomCallback<BejoResponse, BejoLaunchResult>(new BejoLaunchResult()));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity_.intent(getApplicationContext()).extra("tanggalSaiki", tanggal).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                finish();
            }
        }, 1250);

    }

    @Subscribe
    public void onBejoResponse(BejoLaunchResult result) {
        tanggal = result.response.getHari1() + ", " + DateTimeUtils.getLocalDate(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
