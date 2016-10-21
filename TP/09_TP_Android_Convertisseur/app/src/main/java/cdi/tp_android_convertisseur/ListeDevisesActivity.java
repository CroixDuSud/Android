package cdi.tp_android_convertisseur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListeDevisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_devises);

        ListView lvListeDevise = (ListView)findViewById(R.id.lvDevises);

        ArrayList<HashMap<String, String>> list = buildData();

        String[] from = { "nomDevise", "tauxDevise" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
        lvListeDevise.setAdapter(adapter);

    }

    private ArrayList<HashMap<String, String>> buildData() {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        Map<String, Double> conversionTable = Convert.getConversionTable();

        for (Iterator<Map.Entry<String, Double>> it = conversionTable.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Double> dev = it.next();

            HashMap<String, String> item = new HashMap<String, String>();
            item.put("nomDevise", dev.getKey());
            item.put("tauxDevise", String.valueOf(dev.getValue()));
            list.add(item);
        }


        return list;
    }

    }
