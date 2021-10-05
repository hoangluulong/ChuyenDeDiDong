package java.android.quanlybanhang.function;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.ChiTietSanPham.Card_San_Pham;
import java.android.quanlybanhang.CategoryMon.StaticCategoryAdapter;
import java.android.quanlybanhang.CategoryMon.StaticCategoryMonModel;
import java.android.quanlybanhang.ChiTietSanPham.ChiTietSanPham;
import java.android.quanlybanhang.ChiTietSanPham.Interface_CategorySp_Sp;
import java.android.quanlybanhang.OrderMon.Product;
import java.android.quanlybanhang.OrderMon.StaticMonRvAdapter;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.ChiTietSanPham.StaticCardAdapter;
import java.util.ArrayList;

public class MonOrder extends AppCompatActivity implements Interface_CategorySp_Sp {
    public ArrayList<MonOrder> listmon = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Bundle bundle1= intent.getExtras();
                    if(bundle1.getSerializable("mon")!= null){
                        Log.d("aaaaa","bbbb");
                    }
                    MonOrder monOrder = (MonOrder) bundle1.getSerializable("mon");

                    listmon.add(monOrder);
                }
            });
            //
    private RecyclerView recyclerView,recyclerView2;//rv category mon, mon
    private StaticCategoryAdapter staticCategoryAdapter;
    ArrayList<Product> items= new ArrayList<>();//araylist mon
    StaticMonRvAdapter staticMonRvAdapter;//adapter ban
    private DatabaseReference mDatabase;//khai bao database
    private Toolbar toolbar;//tool bar khai bao id
    ArrayList<StaticCategoryMonModel> item;
    Product staticMonOrderModel;
    String tenban;
    StaticCardAdapter  staticCardAdapter ;
    ArrayList<Product> listcard= new ArrayList<>();//araylist mon
    Button bnt_card ;
    Interface_CategorySp_Sp interface_categorySp_sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_order);
        Intent intent = getIntent();
        tenban = intent.getStringExtra("tenban");
        Log.d("aaa",tenban+"vvv");
        //menu toolbar
        listcard = new ArrayList<>();
        bnt_card = findViewById(R.id.bnt_card);
        bnt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonOrder.this, Card_San_Pham.class);
//             Bundle  bundle = new Bundle();
//             bundle.putSerializable("list",listcard);
////             intent.putExtras(bundle);
//             if(bundle!= null){
//                 Log.d("aaa","bbbb");
//
//                 Log.d("aaa", listcard.size()+"");
//             }
               startActivityForResult(intent,RESULT_OK);

            }
        });
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
//viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanpham");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 item = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ArrayList<Product> mm= new ArrayList<>();
                    String tencategory= snapshot1.getKey()+"";
                    Log.d("aoihayate",snapshot1.getKey()+"");
                    DataSnapshot aaa = snapshot1;
                    for (DataSnapshot snapshot2 : aaa.getChildren()){
                        staticMonOrderModel=snapshot2.getValue(Product.class);
                        Log.d("baldum" ,staticMonOrderModel.getImgProduct()+"abc");
                        Log.d("baldum" ,staticMonOrderModel.getNameProduct()+"abc");
                        mm.add(staticMonOrderModel);
                    }
                    StaticCategoryMonModel product = new StaticCategoryMonModel(tencategory,mm);
                    item.add(product);
                    Log.d("cccc",item.size()+"");
                }
                recyclerView = findViewById(R.id.rv_1);
                staticCategoryAdapter = new StaticCategoryAdapter(item,MonOrder.this,MonOrder.this,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(MonOrder.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setAdapter(staticCategoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        items = new ArrayList<>();

        recyclerView2 =findViewById(R.id.rv_2);

        staticMonRvAdapter = new StaticMonRvAdapter(items,MonOrder.this,item,0,tenban);
//        staticMonRvAdapter.setData(new StaticMonRvAdapter.IclickGetMon() {
//            @Override
//            public void clickItent(Product product) {
//                Intent intent1 = new Intent(MonOrder.this, ChiTietSanPham.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("sp",product);
//                intent1.putExtras(bundle);
////                Log.d("aaa",tenban+"baba");
////                intent1.putExtra("tenban",tenban);
//                launcher.launch(intent1);
//            }
//        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(gridLayoutManager);
        recyclerView2.setAdapter(staticMonRvAdapter);


    }

    @Override
    public void GetBack1(int pos, ArrayList<Product> items) {
        staticMonRvAdapter = new StaticMonRvAdapter(items,MonOrder.this,item,pos,tenban);
        staticMonRvAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(staticMonRvAdapter);
    }


}