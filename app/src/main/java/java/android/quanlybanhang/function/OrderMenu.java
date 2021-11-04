    package java.android.quanlybanhang.function;

    import android.app.Dialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Window;
    import android.widget.ProgressBar;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.firebase.database.ChildEventListener;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.sa90.materialarcmenu.ArcMenu;

    import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticBanModel;
    import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticRvAdapter;
    import java.android.quanlybanhang.Common.Interface_KhuVuc_ban;
    import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;

    import java.android.quanlybanhang.Model.DatBan.DatBanModel;
    import java.android.quanlybanhang.Model.DatBan.ID_datban;
    import java.android.quanlybanhang.R;
    import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticRvKhuVucAdapter;
    import java.android.quanlybanhang.function.CardProductSQL.CardProduct;
    import java.sql.Timestamp;
    import java.util.ArrayList;

    public class OrderMenu extends AppCompatActivity implements Interface_KhuVuc_ban {
    private RecyclerView recyclerView,recyclerView2;//rv khu vuc ban
    private StaticRvKhuVucAdapter staticRvKhuVucAdapter;//adapter khu vuc
     ArrayList<StaticBanModel> items= new ArrayList<>();//araylist ban
     StaticRvAdapter staticRvAdapter;//adapter ban
     private DatabaseReference mDatabase;//khai bao database
     Interface_KhuVuc_ban interfaceKhuVucBan ; //ham get back
     private ArcMenu arcMenu;//arc menu material
        ArrayList<StaticModelKhuVuc> item;
       private StaticModelKhuVuc product ;
     private Toolbar toolbar;//tool bar khai bao id
        ProgressBar progressBar;
        private Dialog dialogban;
        Window window;
        String id_ban;
        String id_khuvucs;
        String ids;
        ArrayList<DatBanModel> datBanModels ;
        ArrayList<ID_datban> ID_datbans;
        String id_bk;
        private String trangthaine ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order_menu);
//menu toolbar
             toolbar = findViewById(R.id.toolbars);
             setSupportActionBar(toolbar);
//             viet su kien cho toolbar
            ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
            actionBar.setTitle("");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            dialogban = new Dialog(OrderMenu.this);
            dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogban.setContentView(R.layout.dailongban);
            window = dialogban.getWindow();

            ID_datbans = new ArrayList<>();
            trangthaine= "0";
            mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("MangDi");
            mDatabase.child("trangthai").setValue(trangthaine);

//            hamdatban();
             mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc");
//            Log.d("ccc",FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").getKey());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     item = new ArrayList<>();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        ArrayList<StaticBanModel> mm= new ArrayList<>();
                        String trangthai= postSnapshot.child("trangthai").getValue()+"";
                        String tenkhuvuc=postSnapshot.child("tenkhuvuc").getValue()+"";
                        String id_khuvuc = postSnapshot.getKey();
                        DataSnapshot sss = postSnapshot.child("ban");
                        for (DataSnapshot aaa: sss.getChildren()){
                            String tenban= aaa.child("tenban").getValue()+"";
                            String trangthai1=aaa.child("trangthai").getValue()+"";
                            String tennhanvien = aaa.child("tenNhanVien").getValue()+"";
                            String gioDaorder = aaa.child("gioDaOder").getValue()+"";
                            String id_ban = aaa.getKey();
//                            Log.d("TENBAN",aaa.child("tenban").getValue()+"");

                            mm.add(new StaticBanModel(id_ban,tenban,trangthai1,tennhanvien,gioDaorder));
//                            Log.d("keyabc",aaa.getKey()+"abc");
                        }
                        StaticModelKhuVuc product = new StaticModelKhuVuc(tenkhuvuc,trangthai,id_khuvuc,mm);
                        item.add(product);

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView = findViewById(R.id.rv_1);
                    staticRvKhuVucAdapter = new StaticRvKhuVucAdapter(item,OrderMenu.this,OrderMenu.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(OrderMenu.this,LinearLayoutManager.HORIZONTAL,false));
                    recyclerView.setAdapter(staticRvKhuVucAdapter);
                    staticRvKhuVucAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            callback();

            items = new ArrayList<>();
            recyclerView2 =findViewById(R.id.rv_2);
            staticRvAdapter = new StaticRvAdapter(items,OrderMenu.this,item,"",window,dialogban);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
            recyclerView2.setLayoutManager(gridLayoutManager);
            recyclerView2.setAdapter(staticRvAdapter);
            staticRvAdapter.notifyDataSetChanged();


        }

        @Override
        public void GetBack(int position, ArrayList<StaticBanModel> items,String id_khuvuc) {
            id_khuvuc = item.get(position).getId_khuvuc();
         staticRvAdapter = new StaticRvAdapter(items,OrderMenu.this,item,id_khuvuc,window,dialogban);
         staticRvAdapter.notifyDataSetChanged();
         recyclerView2.setAdapter(staticRvAdapter);

        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_main3, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int item_id =item.getItemId();
            if(item_id==R.id.mangdi){
                trangthaine= "1";
                mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("MangDi");
                mDatabase.child("trangthai").setValue(trangthaine);
                int code = (int) Math.floor(((Math.random() * 899999) + 100000));
                Toast.makeText(this,"order nè",Toast.LENGTH_LONG).show();
                Log.d("codetruong",code+"");

                Intent intent = new Intent(OrderMenu.this, MonOrder.class);
                intent.putExtra("id_datban",code+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            return true;
        }
        public String Hamlaygiohientai(){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Log.d("datenowww",timestamp.getTime()+"");
            return  timestamp.getTime()+"";
        }
        private void callback(){
            ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("onChildAdded", "onChildAdded:" + dataSnapshot.getKey()+"longac1");
                Log.d("onChildAdded", "onChildAdded:" + dataSnapshot.getValue()+"long");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("ccc", "onChildChanged:" + dataSnapshot.getKey());
                Log.d("onChildChanged", "onChildChanged:" + dataSnapshot.getValue()+"longac1");
//

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
            mDatabase.addChildEventListener(childEventListener);}

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent intent = new Intent(OrderMenu.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


    }
