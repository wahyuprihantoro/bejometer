package id.prihantoro.bejometer.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import id.prihantoro.bejometer.R;
import id.prihantoro.bejometer.api.respone.BejoResponse;
import id.prihantoro.bejometer.prefs.UserData;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
@EFragment(R.layout.dialog_bejometer)
public class FragmentBejoDialog extends DialogFragment {
    @ViewById
    TextView nama1;
    @ViewById
    TextView nama2;
    @ViewById
    TextView tanggal1;
    @ViewById
    TextView tanggal2;
    @ViewById
    TextView hasil;
    @ViewById
    LinearLayout errorLayout;
    @ViewById
    LinearLayout successLayout;
    @ViewById
    TextView errorMessage;
    @Bean
    UserData userData;

    @FragmentArg("response")
    BejoResponse response;
    @FragmentArg
    String reqTanggal1;
    @FragmentArg
    String reqTanggal2;

    public static void show(FragmentManager fragmentManager, BejoResponse response, String reqTanggal1, String reqTanggal2) {
        FragmentBejoDialog dialog = FragmentBejoDialog_.builder()
                .response(response)
                .arg("reqTanggal1", reqTanggal1)
                .arg("reqTanggal2", reqTanggal2)
                .build();
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomTheme);
        dialog.setCancelable(true);
        dialog.show(fragmentManager, "fragment_bejo");

    }

    @AfterViews
    void init() {
        char gender1 = response.getGender1().get(0).charAt(0);
        char gender2 = response.getGender2().get(0).charAt(0);
        if (reqTanggal2 == null) {
            errorLayout.setVisibility(View.VISIBLE);
            successLayout.setVisibility(View.GONE);
            if (gender1 == '?') {
                errorMessage.setText("Hmm, tidak yakin " + response.getNama1()
                        + " perempuan atau laki - laki.\nMungkin bukan manusia juga sih.");
            } else if (gender1 == 'L') {
                errorMessage.setText(response.getNama1() + " adalah laki - laki.\nAyo coba lagi!!");
            } else {
                errorMessage.setText(response.getNama1() + " adalah perempuan.\nAyo coba lagi!!");
            }
            return;
        }
        if (gender1 == gender2) {
            errorLayout.setVisibility(View.VISIBLE);
            successLayout.setVisibility(View.GONE);
            if (gender1 == 'L') {
                errorMessage.setText("Maaf mas, bejometer tidak mengenal hubungan sesama jenis.\nSilahkan coba lagi.");
            } else {
                errorMessage.setText("Maaf mbak, bejometer tidak mengenal hubungan sesama jenis.\nSilahkan coba lagi.");
            }
        } else {
            errorLayout.setVisibility(View.GONE);
            successLayout.setVisibility(View.VISIBLE);

            if (gender1 == 'L') {
                nama1.setText("Mas " + response.getNama1());
            } else {
                nama1.setText("Mbak " + response.getNama1());
            }
            if (gender2 == 'L') {
                nama2.setText("Mas " + response.getNama2());
            } else {
                nama2.setText("Mbak " + response.getNama2());
            }
            tanggal1.setText(response.getHari1() + ", " + reqTanggal1);
            tanggal2.setText(response.getHari2() + ", " + reqTanggal2);
            double presentase = ((int) (response.getPersentase() * 100)) / 100.0;
            this.hasil.setText(presentase + "%");
        }
    }

    @Override
    public void onDestroyView() {
        AskRatingDialog.presentDialog(getContext(), userData);
        super.onDestroyView();
    }
}
