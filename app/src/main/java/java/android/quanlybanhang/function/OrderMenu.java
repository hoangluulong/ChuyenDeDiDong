    package java.android.quanlybanhang.function;

    import android.app.Dialog;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Window;
    import android.widget.ProgressBar;

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
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            return super.onOptionsItemSelected(item);
        }
//            private void hamdatban(){
//                mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("DatBan");
//                mDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                            datBanModels = new ArrayList<>();
//                            String id = postSnapshot.getKey();
//                            Log.d("idne",id);
//                            DataSnapshot sss = postSnapshot;
//                            for (DataSnapshot aaa: sss.getChildren()) {
//    //                            ids = aaa.getKey();
//                                id_bk = aaa.child("id_bk").getValue() + "";
//                                String id_ngaydat = aaa.getKey();
//                                String giodat = aaa.child("giodat").getValue() + "";
//                                String gioketthuc = aaa.child("gioketthuc").getValue() + "";
//                                String ngaydat = aaa.child("ngaydat").getValue() + "";
//                                String ngayhientai = aaa.child("ngayhientai").getValue() + "";
//                                String sodienthoai = aaa.child("sodienthoai").getValue() + "";
//                                String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
//                                String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
//                                datBanModels.add(new DatBanModel(id_ngaydat, giodat, gioketthuc, id_bk, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang));
//    //                                if(ids.equals(Hamlaygiohientai())){
//    //                                    FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvucs).child("ban").child(id_ban).child("trangthai").setValue("3");
//    //                                }
//
//                            }
//                            ID_datban datban = new ID_datban(id,datBanModels);
//                            ID_datbans.add(datban);
//    //                        for(int y=0;y<id_datbans.size();y++) {
//    //                            for (int i = 0; i < datBanModels.size(); i++) {
//    //                                if(id_datbans.get(y).getId().equals(datBanModels.get(i).getId_bk())){
//    //                                    Log.d("1635777120000a",id_datbans.get(y).getId());
//    //                                    Log.d("1635777120000a",datBanModels.get(i).getId_bk()+"LOL");
//    //                                    Log.d("1635777120000a",datBanModels.get(i).getId_ngaydat()+"Ngaydatngoai");
//    //                                    Log.d("1635777120000a",Hamlaygiohientai()+"hamlayngayhientai");
//    //
//    //                                    if (Hamlaygiohientai().equals(datBanModels.get(i).getId_ngaydat())) {
//    //                                        Log.d("1635777120000a",datBanModels.get(i).getNgaydat()+"Ngaydattrong");
//    //
//    //                                        String[] splits = datBanModels.get(i).getId_bk().split("_", 2);
//    //                                        id_khuvucs =splits[0];
//    //                                        id_ban =splits[1];
//    //                                        FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvucs).child("ban").child(id_ban).child("trangthai").setValue("3");
//    //                                    }
//    //                                }
//    //
//    //                            }
//    //                        }
//
//                        }
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                Log.d("idnell",Hamlaygiohientai()+"kuku");
//            }
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

    }