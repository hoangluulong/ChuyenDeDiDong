package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.HelperClasses.Package_AdapterDatBan.RvDatBanAdapter;
import java.android.quanlybanhang.HelperClasses.Package_ThanhToanAdapter.ThanhToanAdapter;
import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticBanModel;
import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.Model.DatBan.DatBanModel;
import java.android.quanlybanhang.Model.DatBan.ID_datban;
import java.android.quanlybanhang.Model.KhuyenMaiOffModel;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.DatBan.DanhSachDatBan;
import java.android.quanlybanhang.function.KhuyenMaiOffLine.KhuyenMaiOff;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private String id_ban, id_khuvuc;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1,mFirebaseDatabase;
    private DatabaseReference mDatabasea;
    private ThanhToanAdapter thanhToanAdapter;
    private RecyclerView recyclerView;
    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";
    String id;
    private int kt = -1;
    Double giaTong = 0.0;
    private Button bnt_thanhtoan;
    private TextView totalTxt;
    private String trangthai;
    private String id_datban;
    private ArrayList<ProuductPushFB1> listmon;
    private Toolbar toolbar;
    private ArrayList<ProductPushFB> ListDate_yc;
    private Dialog dialogban;
    Window window;
    ImageButton bnt_threedot;
    TextView gopban, chuyenban, tachban, thoat, tvhuydon;
    TextView title,sotiendadattruoc;
    Long date;
    String code1;
    private Window window1;
    private Dialog dialog, dialog1;
    ArrayList<DatBanModel> datBanModels ;
    ArrayList<ID_datban> ID_datbans;
    String id_bk,id_ne;
    String abc,tenban;
    String id_ngaydat;
    String id_CuaHang;
    double tongTienCuaHang;
    ThongTinCuaHangSql thongTinCuaHangSql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        getDatasql();
        hamlaydate();
        Hamlaygiohientai();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thanh Toán");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getFirebase ();
        totalTxt = findViewById(R.id.totalTxt);
        sotiendadattruoc = findViewById(R.id.sotiendadattruoc);
        dailongsucces();
        bnt_threedot = findViewById(R.id.bnt_threedot);
        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        id_datban = intent1.getStringExtra("id_datban");

        OnclickMenuchucnang();
        bnt_thanhtoan = findViewById(R.id.bnt_thanhtoan);
        id = id_ban + "_" + id_khuvuc;
        abc = id_ban+"_"+id_khuvuc;
        list = new ArrayList<>();
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        code1 = code + "";
        mDatabase1 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("MangDi");
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    trangthai = snapshot1.getValue() + "";
                    Log.d("TrangThaima", trangthai + "chitiet");
                    getData();
                    if (trangthai.equals("1")) {
                        bnt_threedot.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        hamdatban();
        OnclickThanhtoan();
//        kiemtra();


    }

    public void getData() {
        if (trangthai.equals("0")) {
            if (id_ban + "_" + id_khuvuc != null) {
                mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc);
            }
        }
        if (trangthai.equals("1")) {
            if (id_datban != null) {
                mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id_datban);
            }
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ListDate_yc = new ArrayList<>();
                    Log.d("kssj", "ssks");
                    listmon = new ArrayList<ProuductPushFB1>();
                    ArrayList<ProuductPushFB1> mm = new ArrayList<>();
                    Long date1 = Long.parseLong(snapshot.child("date").getValue() + "");
                    Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue() + "");
                    int trangThais = Integer.parseInt(snapshot.child("trangThai").getValue() + "");
                    DataSnapshot sss = snapshot.child("sanpham");
                    for (DataSnapshot postSnapshot : sss.getChildren()) {
                        String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                        int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                        String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                        Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
                        String Loai = postSnapshot.child("loai").getValue() + "";
                        String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
                        listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                        mm.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                    }
                    ProductPushFB product = new ProductPushFB(date1, flag, trangThais, mm);
                    ListDate_yc.add(product);
                    for (int i = 0; i < listmon.size(); i++) {
                        giaTong += listmon.get(i).getGiaProudct() * listmon.get(i).getSoluong();
                    }
                    totalTxt.setText(giaTong + "");
                    recyclerView = findViewById(R.id.rv_3);
                    //đô dữ liệu vào Thanh Toám
                    thanhToanAdapter = new ThanhToanAdapter(listmon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setFocusable(false);
                    recyclerView.setAdapter(thanhToanAdapter);
                    thanhToanAdapter.notifyDataSetChanged();
                    if (listmon.size() > 0) {
                        bnt_thanhtoan.setEnabled(true);
                    }

                } else {
                    bnt_thanhtoan.setEnabled(false);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void OnclickThanhtoan() {

        bnt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

    }

    public String hamlaydate() {
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        Log.d("datenowww", date + "");
        return date;
    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    private void getDatasql() {

        database_order = new Database_order(this, "app_database.sqlite", null, 2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
    }

    private void XoaSpkhiOrder() {
        if (trangthai.equals("0")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
        } else if (trangthai.equals("1")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id_datban + "'");
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.order) {
            Toast.makeText(this, "order thêm", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ThanhToanActivity.this, MonOrder.class);
            intent.putExtra("id_ban", id_ban);
            intent.putExtra("id_khuvuc", id_khuvuc);
            intent.putExtra("id_datban", id_datban);
            startActivity(intent);
        }
        if (item_id == R.id.bacham) {
            Toast.makeText(this, "Chọn Danh sách Khuyến Mãi", Toast.LENGTH_LONG).show();
            return true;
        }
        if (item_id == R.id.bachams) {
            Toast.makeText(this, "Chọn Danh sách Đặt Bàn", Toast.LENGTH_LONG).show();
            return true;
        }
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

    private void HamTaodialog(int gravity) {

        dialogban = new Dialog(ThanhToanActivity.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dailongthanhtoan);

        window = dialogban.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialogban.setCancelable(true);
        } else {
            dialogban.setCancelable(false);
        }
        gopban = dialogban.findViewById(R.id.tvgopban);
        chuyenban = dialogban.findViewById(R.id.tvchuyenban);
//        chuyenban.setEnabled(false);
        tachban = dialogban.findViewById(R.id.tvtachban);
        thoat = dialogban.findViewById(R.id.thoat);
        tvhuydon = dialogban.findViewById(R.id.tvhuydon);
        OnclickChucnang(gopban, chuyenban, tachban, thoat, tvhuydon);
        dialogban.show();
    }

    public void OnclickMenuchucnang() {
        bnt_threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HamTaodialog(Gravity.BOTTOM);
            }
        });
    }

    public void OnclickChucnang(TextView gopban, TextView chuyenban, TextView tachban, TextView thoat, TextView tvhuydon) {
        gopban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);

                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code1).child("trangthai").setValue("1");
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai", code1);
                Gson gson = new Gson();
                String a = gson.toJson(listmon);;
                String c = gson.toJson(datBanModels);
                intent.putExtra("list_as_string2", c);
                intent.putExtra("list_as_string", a);

                startActivity(intent);
            }
        });
        chuyenban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dialogban.dismiss();
                    Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code1).child("trangthai").setValue("2");
                    intent.putExtra("id_ban", id_ban);
                    intent.putExtra("id_khuvuc", id_khuvuc);
                    intent.putExtra("id_datban", id_datban);
                    intent.putExtra("id_trangthai", code1);
                    intent.putExtra("date", date);
                    Gson gson = new Gson();
                    String a = gson.toJson(listmon);
                    String b = gson.toJson(ListDate_yc);
                    String c = gson.toJson(datBanModels);
                    intent.putExtra("list_as_string", a);
                    intent.putExtra("list_as_string1", b);
                    intent.putExtra("list_as_string2", c);
                    startActivity(intent);
            }
        });
        tachban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, TachBanActivity.class);

                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai", code1);
                Gson gson = new Gson();
                String b = gson.toJson(ListDate_yc);
                String a = gson.toJson(listmon);
                String c = gson.toJson(datBanModels);
                intent.putExtra("list_as_string", a);
                intent.putExtra("list_as_string1", b);
                intent.putExtra("list_as_string2", c);
                startActivity(intent);
            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThanhToanActivity.this, "Đóng", Toast.LENGTH_LONG).show();
                dialogban.dismiss();
            }
        });
        tvhuydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailonghuydonhang();
                Toast.makeText(ThanhToanActivity.this, "Hủy Thanh Toán", Toast.LENGTH_LONG).show();
                title.setText("Bạn Chắc Chắn Muốn Hủy Đơn Hàng");
                dialogban.dismiss();
                dialog1.show();
            }
        });
    }

    private void dailongsucces() {
        dialog = new Dialog(ThanhToanActivity.this);
        dialog.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennhanvien= thongTinCuaHangSql.selectUser().getUsername();
                if (trangthai.equals("0")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_ban").setValue(id_ban);
                    databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue(tennhanvien);
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").setValue(tongTienCuaHang+giaTong);
                            XoaSpkhiOrder();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    if(id_ngaydat!=null) {
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(id_ban + "_" + id_khuvuc).child(id_ngaydat).removeValue();
                    }
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc).removeValue();
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);

                } else if (trangthai.equals("1")) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_datban").setValue(id_datban);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue(tennhanvien);
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").setValue(tongTienCuaHang+giaTong);
                            XoaSpkhiOrder();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_datban).removeValue();
                }
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void dailonghuydonhang() {
        dialog1 = new Dialog(ThanhToanActivity.this);
        dialog1.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        title = dialog1.findViewById(R.id.title);
        Button Okay = dialog1.findViewById(R.id.btn_okay);
        Button Cancel = dialog1.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trangthai.equals("0")) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_ban").setValue(id_ban);
                    databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(0);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue("aaa");
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            XoaSpkhiOrder();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    if(id_ngaydat!=null) {
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(id_ban + "_" + id_khuvuc).child(id_ngaydat).removeValue();
                    }
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc).removeValue();
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);

                }
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//                dialog1.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }
    private void hamdatban(){
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID_datbans = new ArrayList<>();
                datBanModels = new ArrayList<>();
                if(snapshot.getValue() != null){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        id_ne = postSnapshot.getKey();
                        DataSnapshot sss = postSnapshot;
                        for (DataSnapshot aaa: sss.getChildren()) {
                            id_bk = aaa.child("id_bk").getValue() + "";
                            if(id_bk.equals(abc)){
                                if((aaa.child("trangthai").getValue()+"").equals("1")){
                                 id_ngaydat = aaa.getKey();
                                String giodat = aaa.child("giodat").getValue() + "";
                                String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                String ngaydat = aaa.child("ngaydat").getValue() + "";
                                String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                String tenban = aaa.child("tenban").getValue() + "";
                                String trangthaidat = aaa.child("trangthai").getValue()+"";
                                datBanModels.add(new DatBanModel(tenban,id_ngaydat,giodat,gioketthuc,id_bk,ngaydat,ngayhientai,sodienthoai,sotiendattruoc,tenkhachhang,trangthaidat));
                                    Log.d("datBanModelskkka",datBanModels.size()+"ThanhToan");
                                    sotiendadattruoc.setText("$"+ sotiendattruoc);
                                }
                            }
                        }
                    }
//                    if(datBanModels.size()>0){
//                        chuyenban.setEnabled(true);
//                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getFirebase () {
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Double tien = snapshot.getValue(Double.class);
                    tongTienCuaHang = tien;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}