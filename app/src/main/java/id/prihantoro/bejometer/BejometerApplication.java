package id.prihantoro.bejometer;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;

/**
 * Created by Wahyu Prihantoro on 24-Aug-16.
 */
public class BejometerApplication extends Application {
    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());
//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(new StethoInterceptor());
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
        super.onCreate();
    }
}
