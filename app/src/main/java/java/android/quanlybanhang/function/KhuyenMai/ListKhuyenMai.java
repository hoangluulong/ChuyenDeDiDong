package java.android.quanlybanhang.function.KhuyenMai;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.HelperClasses.Package_AdapterKhuyenMai.ApdapterKhuyenMai;
import java.android.quanlybanhang.Model.KhuyenMai.KhuyenMai;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ListKhuyenMai  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private EditText searchView;
    private DatabaseReference mDatabase;
    private String STR_CH = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_KM = "khuyenmai";
    private ArrayList<KhuyenMai> arrayList;
    private ArrayList<KhuyenMai> listSearch;
    private ApdapterKhuyenMai apdapterKhuyenMai;
    private KhuyenMai khuyenMai;
    private String key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listkhuyenmai);
        recyclerView = findViewById(R.id.recyclerViewKhuyenMai);
        floatingActionButton = findViewById(R.id.themkhuyenmai);
        searchView = findViewById(R.id.btn_searchkm);
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_KM).child(STR_CH);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = searchView.getText().toString();
                getListSearch(key);
            }
        });
        DanhSachKhuyenMai();
        Taosanphamoi();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            apdapterKhuyenMai = new ApdapterKhuyenMai(ListKhuyenMai.this,arrayList);
            recyclerView.setAdapter(apdapterKhuyenMai);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuyenMai.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < arrayList.size();i++)
        {
            if(arrayList.get(i).getGiaDeDuocKhuyenMai().toString().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(arrayList.get(i));
            }
        }
        apdapterKhuyenMai = new ApdapterKhuyenMai(ListKhuyenMai.this,listSearch);
        recyclerView.setAdapter(apdapterKhuyenMai);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuyenMai.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //Button taọ sản phẩm mới
    public void Taosanphamoi(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm sản phẩm  mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListKhuyenMai.this, ThemKhuyenMai.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }

    public void DanhSachKhuyenMai(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    khuyenMai = snapshot1.getValue(KhuyenMai.class);
                    Double gia = Double.parseDouble(khuyenMai.getGiaDeDuocKhuyenMai().toString());
                    int loai = Integer.parseInt(khuyenMai.getLoaiKhuyenmai()+"");
                    arrayList.add(new KhuyenMai(gia,loai));
                    }
                apdapterKhuyenMai = new ApdapterKhuyenMai(ListKhuyenMai.this,arrayList);
                recyclerView.setAdapter(apdapterKhuyenMai);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuyenMai.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
