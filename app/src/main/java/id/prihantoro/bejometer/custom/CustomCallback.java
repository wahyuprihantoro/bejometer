package id.prihantoro.bejometer.custom;

import org.greenrobot.eventbus.EventBus;

import id.prihantoro.bejometer.api.eventresult.BasicResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
public class CustomCallback<T, E extends BasicResult> implements Callback<T> {

    E result;

    public CustomCallback(E result) {
        this.result = result;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        result.status = true;
        result.message = "ok";
        result.response = response.body();
        EventBus.getDefault().post(result);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t.getMessage() != null && t.getMessage() != null) {
            result.message = t.getMessage();
        }
        EventBus.getDefault().post(result);
    }
}
