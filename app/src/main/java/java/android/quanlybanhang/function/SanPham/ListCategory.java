package java.android.quanlybanhang.function.SanPham;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


import java.android.quanlybanhang.HelperClasses.Package_AdapterSanPham.AdapterCategory;
import java.android.quanlybanhang.HelperClasses.Package_AdapterSanPham.AdapterProduct;
import java.android.quanlybanhang.Model.SanPham.Category;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ListCategory extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private ArrayList<Category> listCategory;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private AdapterCategory adapterCategory;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_NHOMSANPHAM = "danhmucsanpham";
    private EditText searchView;
    private ArrayList<Category> listSearch;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        recyclerView = findViewById(R.id.rv_2);
        searchView = findViewById(R.id.btn_searchnsp);
        floatingActionButton = findViewById(R.id.themnhomsanpham);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(STR_NHOMSANPHAM);
        Danhsachnhomsanpham();
        Taonhomsanpham();
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

    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            adapterCategory = new AdapterCategory(ListCategory.this,listCategory);
            recyclerView.setAdapter(adapterCategory);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < listCategory.size();i++)
        {
            if(listCategory.get(i).getNameCategory().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(listCategory.get(i));
            }
        }
        adapterCategory = new AdapterCategory(ListCategory.this,listSearch);
        recyclerView.setAdapter(adapterCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    //Button tạo nhóm sản phẩm
    public void Taonhomsanpham(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm nhóm sản phẩm  mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListCategory.this, AddCategory.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }
    //Danh sách nhóm sản phẩm
    public void Danhsachnhomsanpham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCategory = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategory.add(category);
                }
                adapterCategory = new AdapterCategory(ListCategory.this,listCategory);
                recyclerView.setAdapter(adapterCategory);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Xóa nhóm sản phẩm
    public void delete( int position){

        new AlertDialog.Builder(ListCategory.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDatabase.child(listCategory.get(position).getId()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();
    }
    //Sửa sản phẩm
    public void update( int position){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View customLayout = inflater.inflate(R.layout.dailong_updatecategory, null);
        EditText editText = customLayout.findViewById(R.id.editUpdateCategory);
        builder.setView(customLayout);
        builder.setTitle("Sửa nhóm sản phẩm");
        editText.setText(listCategory.get(position).getNameCategory());

        builder.setNegativeButton("Thoat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });  builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.child(listCategory.get(position).getId()).child("nameCategory").setValue(editText.getText().toString());
            }

        });

        builder.create().show();
    }
}
