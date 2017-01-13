package id.prihantoro.bejometer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import id.prihantoro.bejometer.R;

/**
 * Created by wahyu on 13 Januari 2017.
 */

public class KonsultasiAdapter extends RecyclerView.Adapter<KonsultasiAdapter.KonsultasiViewHolder> {
    List<List<String>> results;
    Context context;

    public KonsultasiAdapter(List<List<String>> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public KonsultasiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_konsultasi_item, parent, false);
        return new KonsultasiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final KonsultasiViewHolder holder, int position) {
        final List<String> result = results.get(position);
        int persent = (int) (Double.parseDouble(result.get(1)) * 100);
        String persentStr = ((persent - 10) < 0 ? 0 : (persent - 10)) + "~" + persent + "%";
        holder.nama.setText(result.get(0));
        holder.persentase.setText(persentStr);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLString = "https://www.google.com/search?hl=en&site=imghp&tbm=isch&source=hp&q=" + result.get(0);
                new FinestWebView.Builder(context).show(URLString);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class KonsultasiViewHolder extends RecyclerView.ViewHolder {

        TextView nama;
        TextView persentase;
        RelativeLayout layout;

        public KonsultasiViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            persentase = (TextView) itemView.findViewById(R.id.persentase);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }
}
