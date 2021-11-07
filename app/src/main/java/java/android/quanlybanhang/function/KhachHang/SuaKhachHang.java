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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Model.AddressVN.DiaChi;
import java.android.quanlybanhang.Model.AddressVN.Huyen;
import java.android.quanlybanhang.Model.KhachHang.KhachHang;
import java.android.quanlybanhang.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class SuaKhachHang extends AppCompatActivity {
    private EditText editHoTen,editSDT,editNgaySinh,editEmail,editGhiChu,soNha;
    private TextView editDiaChi;
    private Spinner spnNhomKhachHang;
    private RadioButton radioNam,radioNu,radioKhongCo;
    private AutoCompleteTextView spnTinh,spnHuyen,spnXa;
    private Button btnTaoDiaChi,btnHuyDiaChi,btnTaoKhachHang,btnhuyTaoKhachHang;
    private ImageButton btnLich;
    private ArrayAdapter<String> adapterTinh,adapterHuyen,adapterXa;
    private String[] tinh;
    private String[] huyen;
    private String[] xa;
    private int ViTri;
    private String tenTinh;
    private String tenHuyen;
    private String tenXa;
    private Dialog dialog;
    private Window window;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_KHACHHANG = "khachhang";
    private String STR_NHOMKH = "nhomkhachhang";
    private ArrayList<String> arrayListNhomKH;
    private String gioiTinh;
    private RadioGroup radioGroup;
    private KhachHang khachhang;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        khachhang = (KhachHang) getIntent().getSerializableExtra("Key_arrKH");
        setContentView(R.layout.activity_addkhachhang);
        editHoTen = findViewById(R.id.edtTenKhachHang);
        editSDT = findViewById(R.id.edtPhoneKhachHang);
        editDiaChi = findViewById(R.id.edtDiaChiKhachHang);
        editEmail = findViewById(R.id.edtEmailKhachHang);
        editGhiChu = findViewById(R.id.edtghichuKhachHang);
        editNgaySinh = findViewById(R.id.edtNgaySinh);
        spnNhomKhachHang = findViewById(R.id.spnNhomKhachHang);
        btnLich = findViewById(R.id.imgLich);
        btnTaoKhachHang = findViewById(R.id.btnTaoKhachhang);
        btnhuyTaoKhachHang = findViewById(R.id.btnhuyTaoKhachHang);

        //dialog
        dialog = new Dialog(SuaKhachHang.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemdiachikhachhang);
        window = dialog.getWindow();
        spnTinh = dialog.findViewById(R.id.spinner_tinh);
        spnHuyen = dialog.findViewById(R.id.spinner_huyen);
        spnXa = dialog.findViewById(R.id.spinner_xa);
        soNha = dialog.findViewById(R.id.edtSoNha);
        btnHuyDiaChi = dialog.findViewById(R.id.btnhuyTaoDiaChiKhachHang);
        btnTaoDiaChi = dialog.findViewById(R.id.btnTaoDiaChiKhachhang);

        //firebase
        mDatabase =  FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_KHACHHANG);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_NHOMKH);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });

        editHoTen.setText(khachhang.getHoTenKhachHang());
        editDiaChi.setText(khachhang.getSoNha()+","+khachhang.getDiaChiXa()+","+khachhang.getDiaChiHuyen()+","+khachhang.getDiaChiTinh());
        editEmail.setText(khachhang.getEmail());
        editNgaySinh.setText(khachhang.getNgaySinh());
        editGhiChu.setText(khachhang.getGhiChu());
        editSDT.setText(khachhang.getSoDT());
        editDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDiaChiKhachHang(Gravity.CENTER);

            }
        });
    }

    public void updateDiaChiKhachHang(int gravity){
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
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tinh);
        spnTinh.setAdapter(adapterTinh);
        if(khachhang.getDiaChiTinh() != null){
            int com = adapterTinh.getPosition(khachhang.getDiaChiTinh());
          spnTinh.setDropDownAnchor(86);
        }
        adapterTinh.notifyDataSetChanged();
        spnTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(SuaKhachHang.this,R.layout.support_simple_spinner_dropdown_item,arrayHuyen);
                spnHuyen.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                spnHuyen.setAdapter(adapterHuyen);
//                if(khachhang.getDiaChiHuyen() != null){
//                    int com = adapterTinh.getPosition(khachhang.getDiaChiHuyen());
//                    spnHuyen.setSelection(com);
//                }

                adapterXa = new ArrayAdapter<String>(SuaKhachHang.this,R.layout.support_simple_spinner_dropdown_item,listDiaChi.get(position).getHuyens().get(0).getXa());
                spnXa.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                spnXa.setAdapter(adapterXa);
//                if(khachhang.getDiaChiXa() != null){
//                    int com = adapterTinh.getPosition(khachhang.getDiaChiXa());
//                    spnXa.setSelection(com);
//                }
            }
        });

        spnHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                spnXa.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                spnXa.setAdapter(adapterXa);
//                if(khachhang.getDiaChiXa() != null){
//                    int com = adapterTinh.getPosition(khachhang.getDiaChiXa());
//                    spnXa.setSelection(com);
//                }
            }
        });
        spnXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
        btnHuyDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDiaChi.setText(soNha.getText()+","+tenXa+","+tenHuyen+","+tenTinh);
                dialog.dismiss();
            }
        });


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


    private String[] ArrayTinh() {

        String[] arr = new String[listDiaChi.size()];

        for (int i = 0; i < listDiaChi.size(); i++) {
            arr[i] = listDiaChi.get(i).getTenTinhTP();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[listDiaChi.get(pos).getHuyens().size()];

        for (int i = 0; i < listDiaChi.get(pos).getHuyens().size(); i++) {
            arr[i] = listDiaChi.get(pos).getHuyens().get(i).getTenHuyen();
        }

        return arr;
    }
}
