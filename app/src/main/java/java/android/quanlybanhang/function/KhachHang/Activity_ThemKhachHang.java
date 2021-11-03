package java.android.quanlybanhang.function.KhachHang;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Model.AddressVN.DiaChi;
import java.android.quanlybanhang.Model.AddressVN.Huyen;
import java.android.quanlybanhang.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Activity_ThemKhachHang extends AppCompatActivity {
    private EditText editHoTen,editSDT,editDiaChi,editNgaySinh,editEmail,editGhiChu,soNha;
    private Spinner spnNhomKhachHang;
    private RadioButton radioNam,radioNu,radioKhongCo;
    private AutoCompleteTextView spnTinh,spnHuyen,spnXa;
    private Button btnTaoDiaChi,btnHuyDiaChi;
    private ArrayAdapter<String> adapterTinh,adapterHuyen,adapterXa;
    private ArrayList<String> Tinh,Huyen,Xa;
    private Dialog dialog;
    private Window window;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkhachhang);
        editHoTen = findViewById(R.id.edtTenKhachHang);
        editSDT = findViewById(R.id.edtPhoneKhachHang);
        editDiaChi = findViewById(R.id.edtDiaChiKhachHang);
        editEmail = findViewById(R.id.edtEmailKhachHang);
        editGhiChu = findViewById(R.id.edtghichuKhachHang);
        spnNhomKhachHang = findViewById(R.id.spnNhomKhachHang);
        radioNu = findViewById(R.id.gtNu);
        radioNam = findViewById(R.id.gtNam);
        radioKhongCo = findViewById(R.id.gtkhongco);
        //Dialog
        dialog = new Dialog(Activity_ThemKhachHang.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemdiachikhachhang);
        window = dialog.getWindow();
        spnTinh = dialog.findViewById(R.id.spinner_tinh);
        spnHuyen = dialog.findViewById(R.id.spinner_huyen);
        spnXa = dialog.findViewById(R.id.spinner_xa);
        btnHuyDiaChi = dialog.findViewById(R.id.btnhuyTaoDiaChiKhachHang);
        btnTaoDiaChi = dialog.findViewById(R.id.btnTaoDiaChiKhachhang);
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });
       editDiaChi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TaoDiaChiKhachHang(Gravity.CENTER);
           }
       });

    }

    public void TaoDiaChiKhachHang(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        progressBar.setVisibility(View.INVISIBLE);

        Log.d("diachi",listDiaChi.size()+"");
        for (int i = 0; i< listDiaChi.size();i++){
                  String tinh = listDiaChi.get(i).getTenTinhTP();
                  Tinh.add(tinh);
               }
        adapterTinh = new ArrayAdapter<>(this, R.layout.item_spinner1_setup_store, Tinh);


        dialog.show();
    }

    class docJSon extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject khuVuc = root.getJSONObject(i);
                    String tinhTP = khuVuc.getString("name");
                    JSONArray arrHuyen = khuVuc.getJSONArray("districts");
                    ArrayList<Huyen> huyens = new ArrayList<>();
                    for (int j = 0; j < arrHuyen.length(); j++) {
                        JSONObject khuVucHuyen = arrHuyen.getJSONObject(j);
                        String tenHuyen = khuVucHuyen.getString("name");
                        JSONArray arrXa = khuVucHuyen.getJSONArray("wards");
                        ArrayList<String> xas = new ArrayList<>();
                        for (int k = 0; k < arrXa.length(); k++) {
                            JSONObject khuVucXa = arrXa.getJSONObject(k);
                            String xa = khuVucXa.getString("name");
                            xas.add(xa);
                        }
                        Huyen huyen = new Huyen(tenHuyen, xas);
                        huyens.add(huyen);
                    }

                    DiaChi diaChi = new DiaChi(tinhTP, huyens);
                    listDiaChi.add(diaChi);
                }

//               for (int i = 0; i< listDiaChi.size();i++){
//                   for (int y =0; y< listDiaChi.get(i).getHuyens().size();y++){
//                       Log.d("qq",listDiaChi.get(i).getHuyens().get(y).getXa()+"");
//                   }
//               }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            //Create a url object
            URL url = new URL(theUrl);

            //create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            //read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
