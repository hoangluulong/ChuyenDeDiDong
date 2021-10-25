package java.android.quanlybanhang.function.SanPham;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.LongDef;
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
import com.squareup.picasso.Picasso;


import java.android.quanlybanhang.Adapter.AdapterProduct;
import java.android.quanlybanhang.Data.DonGia;
import java.android.quanlybanhang.Data.Product;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListProduct  extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private FirebaseDatabase firebaseDatabase;
    private AdapterProduct adapterProduct;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Product> listProduct;
    private RecyclerView recyclerView;
    private EditText textName, textChitiet, textGianhap, textSoluong, textGiaSanPham,textTenDonViTinh;
    private Spinner spnNhomsanpham, spnDonViTinh;
    private Button btnChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        floatingActionButton = findViewById(R.id.themsanpham);
        recyclerView = findViewById(R.id.recyclerViewProduct);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanpham");
        Danhsachsanpham();
        Taosanphamoi();

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
                                intent = new Intent(ListProduct.this, AddProduct.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }
    //Hiển thị danh sách sản phẩm
    public void Danhsachsanpham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DonGia> donGias = new ArrayList();
                listProduct = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataSnapshot aaa = snapshot1;
                    for (DataSnapshot snapshot2 : aaa.getChildren()){
                        Product product = snapshot2.getValue(Product.class);
                        listProduct.add(product);
                        DataSnapshot aaa1 = snapshot2;
                    }
                    adapterProduct = new AdapterProduct(ListProduct.this,ListProduct.this, listProduct);
                    recyclerView.setAdapter(adapterProduct);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListProduct.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Xóa sản phẩm
    public void delete(final int position){
         new AlertDialog.Builder(ListProduct.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            DataSnapshot aaa = snapshot1;
                            Toast.makeText(ListProduct.this,listProduct.get(position).getId()+"",Toast.LENGTH_LONG).show();
                            mDatabase1 = firebaseDatabase.getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanpham").child(aaa.getKey());
                            mDatabase1.child(listProduct.get(position).getId()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }).setNegativeButton("No", null)
                .show();
    }
    //Sữa sàn phẩm
    public void update(final int position){
       

    }


}
