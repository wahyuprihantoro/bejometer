package id.prihantoro.bejometer.api.eventresult;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
public class BasicResult<T> {
    public boolean status;
    public String message;
    public T response;
}
