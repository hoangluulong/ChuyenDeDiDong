package java.android.quanlybanhang.function.Account;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.android.quanlybanhang.Common.DataAddress;
import java.android.quanlybanhang.Model.Address;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.DbBaoCao;
import java.android.quanlybanhang.database.ThongTinCuaHangSql;
import java.android.quanlybanhang.function.MainActivity;
import java.io.IOException;
import java.util.ArrayList;

public class StoreSetting extends AppCompatActivity {
    public DataAddress address;
    private ArrayList<Address> arrAddresses;

    private AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    private Button btnComplete;
    private Switch aSwitch;
    private TextInputEditText edtTenCuaHang;

    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private String tenTinh;
    private String tenHuyen;
    private String KEY_CHILD = "";
    private String ID_USER = "";
    private DbBaoCao dataSql;

    //Firebase
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;

    private final static String KEY_CHILD_STORE = "KEY_CHILD_STORE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            KEY_CHILD = bundle.getString(KEY_CHILD_STORE, "");
            ID_USER = bundle.getString("ID_USER", "");
        }else{
            Log.d("AAA", "null");
        }

        autoCompleteTextView1 = findViewById(R.id.spinner_province);
        autoCompleteTextView2 = findViewById(R.id.spinner_district);
        btnComplete = findViewById(R.id.btn_complete);
        aSwitch = findViewById(R.id.sw_sample_data);
        edtTenCuaHang = findViewById(R.id.edtTenCuaHang);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        try {
            address = new DataAddress();
            arrAddresses = address.readCompanyJSONFile(StoreSetting.this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String[] items = ArrayTinh();
        adapter1 = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, items);
        autoCompleteTextView1.setAdapter(adapter1);

//        autoCompleteTextView2.setEnabled(false);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();

                String[] arrayHuyen = ArrayHuyen(position);
                Log.d("AAA", arrayHuyen.length+"");

                adapter2 = new ArrayAdapter<String>(StoreSetting.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                autoCompleteTextView2.setText(arrAddresses.get(position).getHuyen()[0]);
                tenHuyen = arrAddresses.get(position).getHuyen()[0];
                autoCompleteTextView2.setAdapter(adapter2);
//                autoCompleteTextView2.setEnabled(true);
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();
            }
        });


        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStore = edtTenCuaHang.getText().toString();

                if (nameStore.isEmpty()) {
                    edtTenCuaHang.setError("Can not be empty");
                    edtTenCuaHang.requestFocus();
                }else if (tenTinh == null) {
                    autoCompleteTextView1.setError("Can not be empty");
                    autoCompleteTextView1.requestFocus();
                }else if (tenHuyen == null) {
                    autoCompleteTextView2.setError("Can not be empty");
                    autoCompleteTextView2.requestFocus();
                }else{
                    if (aSwitch.isChecked() == true) {
                        setUpStore();
                        Toast.makeText(StoreSetting.this,"Complete", Toast.LENGTH_SHORT).show();
                    }else{
                        mFirebaseDatabase.child("CuaHangOder/"+ID_USER+"/ThongTinCuaHang/TenCuaHang").setValue(nameStore);
                        mFirebaseDatabase.child("CuaHangOder/"+ID_USER+"/ThongTinCuaHang/DiaChi_Tinh").setValue(tenTinh);
                        mFirebaseDatabase.child("CuaHangOder/"+ID_USER+"/ThongTinCuaHang/DiaChi_Huyen").setValue(tenHuyen);
                        mFirebaseDatabase.child("CuaHangOder/"+ID_USER+"/ThongTinCuaHang/ThietLap").setValue(true);

                        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(StoreSetting.this, "app_database.sqlite", null, 2);
                        thongTinCuaHangSql.createTable();
                        Cursor cursor = thongTinCuaHangSql.selectThongTin();
                        if (cursor.getCount() > 0){
                            String IdOld = "";
                            while (cursor.moveToNext()) {
                                IdOld = cursor.getString(0);
                            }
                            thongTinCuaHangSql.UpdateCuaHang(ID_USER, IdOld, nameStore);
                        }else {
                            thongTinCuaHangSql.InsertThonTin(ID_USER, nameStore);
                        }

                        Intent intent1 = new Intent(StoreSetting.this, MainActivity.class);
                        startActivity(intent1);
                    }


                }
            }
        });
    }

    //
    private String[] ArrayTinh() {

        String[] arr = new String[arrAddresses.size()];

        for (int i = 0; i < arrAddresses.size(); i++){
            arr[i] = arrAddresses.get(i).getTinh();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[arrAddresses.get(pos).getHuyen().length];

        for (int i = 0; i < arrAddresses.get(pos).getHuyen().length; i++){
            arr[i] = arrAddresses.get(pos).getHuyen()[i];
        }

        return arr;
    }

    //sử dụng dữ liễu mẫu
    private void setUpStore () {
        Intent intent1 = new Intent(StoreSetting.this, MainActivity.class);
        startActivity(intent1);
    }

}