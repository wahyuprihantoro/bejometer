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
import id.prihantoro.bejometer.api.eventresult.BejoResult;
import id.prihantoro.bejometer.api.respone.BejoResponse;
import id.prihantoro.bejometer.api.service.BejoService;
import id.prihantoro.bejometer.custom.CustomCallback;
import id.prihantoro.bejometer.dialog.FragmentBejoDialog;
import id.prihantoro.bejometer.prefs.UserData;
import id.prihantoro.bejometer.util.DateTimeUtils;
import id.prihantoro.bejometer.util.HashUtils;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
@EFragment(R.layout.fragment_bejometer)
public class BejometerFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @FragmentArg
    String tanggalSaiki;
    @ViewById
    TextInputLayout nama1Layout;
    @ViewById
    TextInputLayout nama2Layout;
    @ViewById
    TextInputLayout tanggal1Layout;
    @ViewById
    TextInputLayout tanggal2Layout;
    @ViewById
    EditText nama1;
    @ViewById
    EditText nama2;
    @ViewById
    EditText tanggal1;
    @ViewById
    EditText tanggal2;
    @ViewById
    TextView hariIni;
    @ViewById
    Button hitung;

    ProgressDialog progressDialog;

    boolean flagTanggal1;

    String strTanggal1, strTanggal2;
    Calendar date1, date2;
    @Bean
    UserData userData;

    @Bean
    Retrofit retrofit;

    DatePickerDialog datePickerDialog;

    @AfterViews
    void init() {
        progressDialog = new ProgressDialog(getContext());
        if (tanggalSaiki != null) {
            hariIni.setText(tanggalSaiki + "\n*Bejometer, konsultan kabegjan.");
        } else {
            hariIni.setText("*Bejometer, konsultan kabegjan.");
        }
        tanggal1.setInputType(InputType.TYPE_NULL);
        tanggal2.setInputType(InputType.TYPE_NULL);
        datePickerDialog = new DatePickerDialog();
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
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

    @AfterTextChange
    public void nama2() {
        if (nama2.getText().toString().length() > 0) {
            nama2Layout.setError(null);
            nama2Layout.setErrorEnabled(false);
        } else {
            nama2Layout.setErrorEnabled(true);
            nama2Layout.setError("Nama tidak boleh kosong");
        }
    }

    @FocusChange
    public void tanggal2(EditText editText, boolean hasFocus) {
        if (hasFocus) {
            flagTanggal1 = false;
            datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    }

    @FocusChange
    public void tanggal1(EditText editText, boolean hasFocus) {
        if (hasFocus) {
            flagTanggal1 = true;
            datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
        }
    }

    @Click
    public void tanggal1() {
        flagTanggal1 = true;
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Click
    public void tanggal2() {
        flagTanggal1 = false;
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Click
    public void hitung() {
        String nama1 = this.nama1.getText().toString();
        String nama2 = this.nama2.getText().toString();
        String tanggal1 = this.tanggal1.getText().toString();
        String tanggal2 = this.tanggal2.getText().toString();
        if (isValid(nama1, nama2, tanggal1, tanggal2)) {
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            BejoRequest request = new BejoRequest();
            request.nama1 = nama1;
            request.nama2 = nama2;
            request.tanggal1 = strTanggal1;
            request.tanggal2 = strTanggal2;
            request.disimpan = true;
            String date = "BADAK-" + DateTimeUtils.getSimpleDateFormat(new Date(System.currentTimeMillis()));
            retrofit.getService(BejoService.class)
                    .njaluk(HashUtils.md5(date), request.getMultipleQuery())
                    .enqueue(new CustomCallback<BejoResponse, BejoResult>(new BejoResult()));
            hitung.setEnabled(false);

            nama1Layout.setError(null);
            nama1Layout.setErrorEnabled(false);
            nama2Layout.setError(null);
            nama2Layout.setErrorEnabled(false);
            tanggal1Layout.setError(null);
            tanggal1Layout.setErrorEnabled(false);
            tanggal2Layout.setError(null);
            tanggal2Layout.setErrorEnabled(false);
        }
    }

    private boolean isValid(String nama1, String nama2, String tanggal1, String tanggal2) {
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

        if (nama2 == null || nama2.length() == 0) {
            nama2Layout.setErrorEnabled(true);
            nama2Layout.setError("Nama tidak boleh kosong");
            ret = false;
        } else {
            nama2Layout.setError(null);
            nama2Layout.setErrorEnabled(false);
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


        if (tanggal2 == null || tanggal2.length() == 0) {
            tanggal2Layout.setErrorEnabled(true);
            tanggal2Layout.setError("Tanggal tidak boleh kosong");
            ret = false;
        } else if (date2 != null && date2.getTime().after(now)) {
            tanggal2Layout.setErrorEnabled(true);
            tanggal2Layout.setError("Tanggal tidak boleh melebihi hari ini");
            ret = false;
        } else {
            tanggal2Layout.setError(null);
            tanggal2Layout.setErrorEnabled(false);
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
        if (flagTanggal1) {
            date1 = date;
            strTanggal1 = dateStr;
            tanggal1.setText(DateTimeUtils.getLocalDate(date.getTime()));
            tanggal1Layout.setError(null);
            tanggal1Layout.setErrorEnabled(false);
        } else {
            date2 = date;
            strTanggal2 = dateStr;
            tanggal2.setText(DateTimeUtils.getLocalDate(date.getTime()));
            tanggal2Layout.setError(null);
            tanggal2Layout.setErrorEnabled(false);
        }
    }

    @Subscribe
    public void onBejoResponse(BejoResult result) {
        progressDialog.dismiss();
        hitung.setEnabled(true);
        if (result.status) {
            char gender1 = result.response.getGender1().get(0).charAt(0);
            char gender2 = result.response.getGender2().get(0).charAt(0);
            if (gender1 == '?' || gender2 == '?') {
                if (gender1 == '?') {
                    nama1Layout.setErrorEnabled(true);
                    nama1Layout.setError("Harus nama manusia");
                }
                if (gender2 == '?') {
                    nama2Layout.setErrorEnabled(true);
                    nama2Layout.setError("Harus nama manusia");
                }
            } else {
                String tanggal1 = this.tanggal1.getText().toString();
                String tanggal2 = this.tanggal2.getText().toString();
                FragmentBejoDialog.show(getFragmentManager(), result.response, tanggal1, tanggal2);
                userData.incBejometerCount();
                Log.d("wahyu", userData.hasGivingRating() + " " + userData.getTimeToAskRating() + " " + userData.hasTryBejometer() + " "
                        + userData.hasTryTebakGender() + " " + userData.getTryBejometerCount() + " " + userData.getTryTebakGenderCount());
            }

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
