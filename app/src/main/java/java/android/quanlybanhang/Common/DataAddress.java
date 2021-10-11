package java.android.quanlybanhang.Common;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Model.Address;
import java.android.quanlybanhang.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DataAddress {
    // Read the company.json file and convert it to a java object.
    private int stt = 70;

    public ArrayList<Address> readCompanyJSONFile(Context context) throws IOException, JSONException {

        String jsonText = readText(context, R.raw.vnaddress);

        ArrayList<Address> address = new ArrayList<>();
        JSONObject jsonRootObject = new JSONObject(jsonText);
        for(int i = 1; i < 64; i++) {
            JSONObject jsonObject = jsonRootObject.getJSONObject(i+"");//lấy từng đối tượng ra
            String name = jsonObject.optString("name").toString();
            JSONObject jsonObjectHuyen = jsonObject.getJSONObject("districts");

            String[] huyen = new String[jsonObjectHuyen.length()];
            for (int j = 0; j < jsonObjectHuyen.length(); j++){
//                Log.d("AAA", i + " - " + name +" - "+ jsonObjectHuyen.optString(""+stt));
                huyen[j] = jsonObjectHuyen.optString(""+stt);
                stt++;
            }

            address.add(new Address(i - 1,name,huyen));

        }

        return address;
    }

    private String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
