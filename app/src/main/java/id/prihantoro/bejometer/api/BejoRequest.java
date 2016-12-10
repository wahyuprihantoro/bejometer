package id.prihantoro.bejometer.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wahyu Prihantoro on 21-Aug-16.
 */
public class BejoRequest {
    public String nama1 = "";
    public String nama2 = "";
    public String tanggal1 = "";
    public String tanggal2 = "";
    public boolean disimpan = false;

    public Map<String, String> getMultipleQuery() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("nama1", nama1);
        map.put("tanggal1", tanggal1);
        map.put("nama2", nama2);
        map.put("tanggal2", tanggal2);
        if (disimpan){
            map.put("disimpen", "1");
        }
        return map;
    }

}
