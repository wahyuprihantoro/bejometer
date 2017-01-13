package id.prihantoro.bejometer.fragment;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;

import id.prihantoro.bejometer.R;
import id.prihantoro.bejometer.api.BejoRequest;
import id.prihantoro.bejometer.api.Retrofit;
import id.prihantoro.bejometer.api.eventresult.BejoLaunchResult;
import id.prihantoro.bejometer.api.eventresult.KonsultasiResult;
import id.prihantoro.bejometer.api.eventresult.TebakGenderResult;
import id.prihantoro.bejometer.api.respone.BejoResponse;
import id.prihantoro.bejometer.api.respone.KonsultasiResponse;
import id.prihantoro.bejometer.api.service.BejoService;
import id.prihantoro.bejometer.custom.CustomCallback;
import id.prihantoro.bejometer.dialog.FragmentBejoDialog;
import id.prihantoro.bejometer.dialog.KonsultasiDialog;
import id.prihantoro.bejometer.prefs.UserData;
import id.prihantoro.bejometer.util.DateTimeUtils;
import id.prihantoro.bejometer.util.HashUtils;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
@EFragment(R.layout.fragment_konsultasi)
public class KonsultasiFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @FragmentArg
    String tanggalSaiki;
    @ViewById
    EditText nama1;
    @ViewById
    EditText tanggal1;
    @ViewById
    TextInputLayout nama1Layout;
    @ViewById
    TextInputLayout tanggal1Layout;
    @ViewById
    TextView hariIni;
    @ViewById
    Button hitung;
    @Bean
    UserData userData;
    @Bean
    Retrofit retrofit;
    DatePickerDialog datePickerDialog;

    String strTanggal1, strTanggal2;
    Calendar date1, date2;

    ProgressDialog progressDialog;

    @AfterViews
    void init() {
        progressDialog = new ProgressDialog(getContext());
        if (tanggalSaiki != null) {
            hariIni.setText(tanggalSaiki + "\n*Bejometer, konsultan kabegjan.");
        } else {
            hariIni.setText("*Bejometer, konsultan kabegjan.");
        }
        tanggal1.setInputType(InputType.TYPE_NULL);
        datePickerDialog = new DatePickerDialog();
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
    }

    @FocusChange
    public void tanggal1(EditText editText, boolean hasFocus) {
        if (hasFocus) {
            datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    }

    @AfterTextChange
    public void nama1() {
        if (nama1.getText().toString().length() > 0) {
            nama1Layout.setError(null);
            nama1Layout.setErrorEnabled(false);
        } else {
            nama1Layout.setErrorEnabled(true);
            nama1Layout.setError("Nama tidak boleh kosong");
        }
    }

    @Click
    public void tanggal1() {
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Click
    public void hitung() {
        String nama1 = this.nama1.getText().toString();
        String tanggal1 = this.tanggal1.getText().toString();
        if (isValid(nama1, tanggal1)) {
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            BejoRequest request = new BejoRequest();
            request.nama1 = nama1;
            request.tanggal1 = strTanggal1;
            String date = "BADAK-" + DateTimeUtils.getSimpleDateFormat(new Date(System.currentTimeMillis()));
            retrofit.getService(BejoService.class)
                    .konsultasi(HashUtils.md5(date), nama1)
                    .enqueue(new CustomCallback<KonsultasiResponse, KonsultasiResult>(new KonsultasiResult()));
            hitung.setEnabled(false);
            nama1Layout.setError(null);
            nama1Layout.setErrorEnabled(false);
            tanggal1Layout.setError(null);
            tanggal1Layout.setErrorEnabled(false);
        }
    }

    private boolean isValid(String nama1, String tanggal1) {
        boolean ret = true;
        Date now = new Date(System.currentTimeMillis());

        if (nama1 == null || nama1.length() == 0) {
            nama1Layout.setErrorEnabled(true);
            nama1Layout.setError("Nama tidak boleh kosong");
            ret = false;
        } else {
            nama1Layout.setError(null);
            nama1Layout.setErrorEnabled(false);
        }


        if (tanggal1 == null || tanggal1.length() == 0) {
            tanggal1Layout.setErrorEnabled(true);
            tanggal1Layout.setError("Tanggal tidak boleh kosong");
            ret = false;
        } else if (date1 != null && date1.getTime().after(now)) {
            tanggal1Layout.setErrorEnabled(true);
            tanggal1Layout.setError("Tanggal tidak boleh melebihi hari ini");
            ret = false;
        } else {
            tanggal1Layout.setError(null);
            tanggal1Layout.setErrorEnabled(false);
        }


        return ret;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(year, monthOfYear, dayOfMonth);
        String month = (monthOfYear + 1) + "";
        String day = dayOfMonth + "";
        if (monthOfYear < 10) {
            month = "0" + month;
        }
        if (dayOfMonth < 10) {
            day = "0" + day;
        }
        String dateStr = year + "-" + month + "-" + day;
        date1 = date;
        strTanggal1 = dateStr;
        tanggal1.setText(DateTimeUtils.getLocalDate(date.getTime()));
        tanggal1Layout.setError(null);
        tanggal1Layout.setErrorEnabled(false);
    }

    @Subscribe
    public void onKonsultasiResponse(KonsultasiResult result) {
        progressDialog.dismiss();
        hitung.setEnabled(true);
        if (result.status) {
            String tanggal1 = this.tanggal1.getText().toString();
            String tanggal2 = null;
            KonsultasiDialog.show(getFragmentManager(), result.response, tanggal1, tanggal2);
//            userData.incTebakGenderCount();
//            Log.d("wahyu", userData.hasGivingRating() + " " + userData.getTimeToAskRating() + " " + userData.hasTryBejometer() + " "
//                    + userData.hasTryTebakGender() + " " + userData.getTryBejometerCount() + " " + userData.getTryTebakGenderCount());
        } else {
            Toast.makeText(getContext(), "Koneksi ke server terganggu.\nSilahkan coba lagi", Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe
    public void onBejoResponse(BejoLaunchResult result) {
        tanggalSaiki = result.response.getHari1() + ", " + DateTimeUtils.getLocalDate(new Date(System.currentTimeMillis()));
        hariIni.setText(tanggalSaiki + "\n*Bejometer, konsultan kabegjan.");
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
