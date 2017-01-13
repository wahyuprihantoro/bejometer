package id.prihantoro.bejometer.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import id.prihantoro.bejometer.R;
import id.prihantoro.bejometer.adapter.KonsultasiAdapter;
import id.prihantoro.bejometer.api.respone.KonsultasiResponse;
import id.prihantoro.bejometer.prefs.UserData;

/**
 * Created by Wahyu Prihantoro on 22-Aug-16.
 */
@EFragment(R.layout.dialog_konsultasi)
public class KonsultasiDialog extends DialogFragment {
    @ViewById
    TextView nama1;
    @ViewById
    TextView tanggal1;
    @ViewById
    RecyclerView rv;
    @ViewById
    LinearLayout errorLayout;
    @ViewById
    LinearLayout successLayout;
    @ViewById
    TextView errorMessage;
    @Bean
    UserData userData;

    @FragmentArg("response")
    KonsultasiResponse response;
    @FragmentArg
    String reqTanggal1;
    @FragmentArg
    String reqTanggal2;

    char gender;

    public static void show(FragmentManager fragmentManager, KonsultasiResponse response, String reqTanggal1, String reqTanggal2) {
        KonsultasiDialog dialog = KonsultasiDialog_.builder()
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
        gender = response.getGender().get(0).charAt(0);
        if (reqTanggal2 == null) {
            errorLayout.setVisibility(View.VISIBLE);
            successLayout.setVisibility(View.GONE);
            if (gender == '?') {
                errorMessage.setText("Hmm, tidak yakin " + response.getNama()
                        + " perempuan atau laki - laki.\nMungkin bukan manusia juga sih.");
            } else {
                errorLayout.setVisibility(View.GONE);
                successLayout.setVisibility(View.VISIBLE);
                String displayName = (gender == 'L' ? "Mas " : "Mbak ") + response.getNama();
                nama1.setText(displayName);
                tanggal1.setText(reqTanggal1);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setAdapter(new KonsultasiAdapter(response.getSuggestedNames(), getContext()));
            }
        }
    }

    @Override
    public void onDestroyView() {
        AskRatingDialog.presentDialog(getContext(), userData);
        super.onDestroyView();
    }
}
