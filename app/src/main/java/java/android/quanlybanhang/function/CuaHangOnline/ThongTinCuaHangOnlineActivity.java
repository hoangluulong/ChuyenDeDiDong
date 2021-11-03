package java.android.quanlybanhang.function.CuaHangOnline;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.AddressVN.DiaChi;
import java.android.quanlybanhang.Model.AddressVN.Huyen;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.ImageAdapter;
import java.android.quanlybanhang.function.CuaHangOnline.Data.DiaChiCuaHang;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Image;
import java.android.quanlybanhang.function.CuaHangOnline.Data.ThongTinCuaHang;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ThongTinCuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout btnDowUpThongtin, thongtinchung, btnDiaChi, diachi, hinhanh, btnHinhAnh, addImage, layoutLoadImage;
    private LinearLayout.LayoutParams params1, params2, params3, params4;
    private ImageView thongTinIMG, diaChiIMG, hinhanhIMG;
    private boolean setL1 = true;
    private boolean setL2 = true;
    private boolean setL3 = true;
    private TextView luu1, luu2;
    private RecyclerView recycleview;
    private ImageAdapter imageAdapter;
    private ArrayList<String> listImage;
    private StorageReference mStogref;
    private DatabaseReference mReference;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();
    private String[] tinh;
    private String[] huyen;
    private String[] xa;

    private String tenTinh;
    private String tenHuyen;
    private String tenXa;

    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private Uri imageUri;
    private ProgressBar progressBar;
    private AutoCompleteTextView phuongxaAuto, thanhphoAuto, quan_huyenAuto;
    private TextInputEditText sonha_soduong, edt_tenCuaHang, edt_phone, edt_mota;

    private ArrayAdapter<String> adapterTinh;
    private ArrayAdapter<String> adapterHuyen;
    private ArrayAdapter<String> adapterXa;
    private ArrayList<Image> images = new ArrayList<>();

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    private int ViTri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_cua_hang_online);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        getThongTinChung();
//        getImageFireBase();
        movedFirebase();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });
        displayItem();
    }

    private void displayItem() {
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));
        imageAdapter = new ImageAdapter(this, images);
        recycleview.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }


    private void IDLayout() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        thongTinIMG = findViewById(R.id.thongTinIMG);
        btnDiaChi = findViewById(R.id.btnDiaChi);
        diachi = findViewById(R.id.diachi);
        btnDowUpThongtin = findViewById(R.id.btnDowUpThongtin);
        thongtinchung = findViewById(R.id.thongtinchung);
        diaChiIMG = findViewById(R.id.diaChiIMG);
        btnHinhAnh = findViewById(R.id.btnHinhAnh);
        hinhanh = findViewById(R.id.hinhanh);
        hinhanhIMG = findViewById(R.id.hinhanhIMG);
        luu1 = findViewById(R.id.luu1);
        luu2 = findViewById(R.id.luu2);
        recycleview = findViewById(R.id.recycleview);
        addImage = findViewById(R.id.addImage);
        progressBar = findViewById(R.id.progressBar);
        layoutLoadImage = findViewById(R.id.layoutLoadImage);
        phuongxaAuto = findViewById(R.id.phuongxa);
        thanhphoAuto = findViewById(R.id.thanhpho);
        quan_huyenAuto = findViewById(R.id.quan_huyen);

        sonha_soduong = findViewById(R.id.sonha_soduong);
        edt_tenCuaHang = findViewById(R.id.edt_tenCuaHang);
        edt_phone = findViewById(R.id.edt_phone);
        edt_mota = findViewById(R.id.edt_mota);

        params1 = (LinearLayout.LayoutParams) thongtinchung.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) diachi.getLayoutParams();
        params3 = (LinearLayout.LayoutParams) hinhanh.getLayoutParams();
        params4 = (LinearLayout.LayoutParams) layoutLoadImage.getLayoutParams();

        params4.height = 0;
        layoutLoadImage.setLayoutParams(params4);

        btnDowUpThongtin.setOnClickListener(this);
        btnDiaChi.setOnClickListener(this);
        btnHinhAnh.setOnClickListener(this);
        addImage.setOnClickListener(this);
        luu1.setOnClickListener(this);
        luu2.setOnClickListener(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        progressBar.setVisibility(View.INVISIBLE);
        playFire();
    }

    private void getThongTinChung() {
        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG + "/thongtin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ThongTinCuaHang thongTinCuaHang = snapshot.getValue(ThongTinCuaHang.class);
                    DiaChiCuaHang diaChiCuaHang = snapshot.getValue(DiaChiCuaHang.class);
                    edt_tenCuaHang.setText(thongTinCuaHang.getName());
                    edt_phone.setText(thongTinCuaHang.getSoDienThoai());
                    edt_mota.setText(thongTinCuaHang.getMoTa());
                    sonha_soduong.setText(diaChiCuaHang.getSoNha());
                    phuongxaAuto.setText(diaChiCuaHang.getPhuongXa());
                    quan_huyenAuto.setText(diaChiCuaHang.getQuanHuyen());
                    thanhphoAuto.setText(diaChiCuaHang.getTinhThanhPho());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void movedFirebase(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                images.add(dataSnapshot.getValue(Image.class));
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Test", "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();

                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).getKey().equals(key)) {
                        images.remove(i);
                        imageAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("Test", "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Test", "postComments:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cuaHang").child(ID_CUAHANG).child("thongtin/image");
        databaseReference.addChildEventListener(childEventListener);
    }

    private void setDataText() {
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, tinh);
        thanhphoAuto.setAdapter(adapterTinh);
        adapterTinh.notifyDataSetChanged();

        thanhphoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                quan_huyenAuto.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                quan_huyenAuto.setAdapter(adapterHuyen);

                adapterXa = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(position).getHuyens().get(0).getXa());
                phuongxaAuto.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        quan_huyenAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                phuongxaAuto.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        phuongxaAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDowUpThongtin:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    thongTinIMG.setImageResource(R.drawable.down_24);
                    thongtinchung.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    thongTinIMG.setImageResource(R.drawable.up_24);
                    thongtinchung.setLayoutParams(params1);
                }
                break;
            case R.id.btnDiaChi:
                if (setL2) {
                    params2.height = 0;
                    setL2 = false;
                    diaChiIMG.setImageResource(R.drawable.down_24);
                    diachi.setLayoutParams(params2);
                } else {
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL2 = true;
                    diaChiIMG.setImageResource(R.drawable.up_24);
                    diachi.setLayoutParams(params2);

                }
                break;
            case R.id.btnHinhAnh:
                if (setL3) {
                    params3.height = 0;
                    setL3 = false;
                    hinhanhIMG.setImageResource(R.drawable.down_24);
                    hinhanh.setLayoutParams(params3);
                } else {
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL3 = true;
                    hinhanhIMG.setImageResource(R.drawable.up_24);
                    hinhanh.setLayoutParams(params3);
                }
                break;
            case R.id.addImage:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;
            case R.id.luu1:
                saveCuaHang();
                break;
            case R.id.luu2:
                saveDiaChi();
                break;
        }
    }

    private void playFire() {
        if (setL1) {
            params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            thongTinIMG.setImageResource(R.drawable.up_24);
            thongtinchung.setLayoutParams(params1);
        } else {
            params1.height = 0;
            thongTinIMG.setImageResource(R.drawable.down_24);
            thongtinchung.setLayoutParams(params1);
        }

        if (setL2) {
            params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            diaChiIMG.setImageResource(R.drawable.up_24);
            diachi.setLayoutParams(params2);
        } else {
            params2.height = 0;
            diaChiIMG.setImageResource(R.drawable.down_24);
            diachi.setLayoutParams(params2);
        }
        if (setL3) {
            params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            hinhanhIMG.setImageResource(R.drawable.up_24);
            hinhanh.setLayoutParams(params3);
        } else {
            params3.height = 0;
            hinhanhIMG.setImageResource(R.drawable.down_24);
            hinhanh.setLayoutParams(params3);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sanpham:
                Toast.makeText(this, "San pham", Toast.LENGTH_LONG).show();
                break;
            case R.id.quangcao:
                intent = new Intent(this, QuangCaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.thongtin:
                break;
            case R.id.giolamviec:
                Toast.makeText(this, "gio làm việc", Toast.LENGTH_LONG).show();
                break;
            case R.id.vanchuyen:
                intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void uploadToFirebase(Uri uri) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                params4.height = 0;
                                layoutLoadImage.setLayoutParams(params4);
                                layoutLoadImage.setEnabled(false);
                                progressBar.setProgress(0);
                            }
                        }, 1000);
                        String key = mFirebaseDatabase.push().getKey();
                        Image image = new Image(key, uri.toString());
                        mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin/image/"+key).setValue(image);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                params4.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutLoadImage.setLayoutParams(params4);
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                params4.height = 0;
                layoutLoadImage.setLayoutParams(params4);
            }
        });
    }

    private String getFileExtension(Uri mUri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void saveCuaHang() {
        String nameCH = edt_tenCuaHang.getText().toString();
        String phone = edt_phone.getText().toString();
        String moTa = edt_mota.getText().toString();

        if (nameCH.isEmpty()) {
            edt_tenCuaHang.setError("Nhập tên cửa hàng");
            edt_tenCuaHang.requestFocus();
        }else if (phone.isEmpty()) {
            edt_phone.setError("Nhập số điện thoại");
            edt_phone.requestFocus();
        }else{
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("moTa").setValue(moTa);
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("name").setValue(nameCH);
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("soDienThoai").setValue(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void saveDiaChi() {
        String soNhaDuong = sonha_soduong.getText().toString();

        if (soNhaDuong.isEmpty()) {
            sonha_soduong.setError("Nhập số nhà/ đường");
            sonha_soduong.requestFocus();
        } else if (tenTinh.isEmpty()) {
            thanhphoAuto.setError("Chọn tỉnh/ Thành phố");
            thanhphoAuto.requestFocus();
        } else if (tenHuyen.isEmpty()) {
            quan_huyenAuto.setError("Chọn quận/huyện");
            quan_huyenAuto.requestFocus();
        } else if (tenXa.isEmpty()){
            phuongxaAuto.setError("Chọn phường xã");
            phuongxaAuto.requestFocus();
        }else {
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("phuongXa").setValue(tenXa);
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("quanHuyen").setValue(tenHuyen);
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("tinhThanhPho").setValue(tenTinh);
            mFirebaseDatabase.child("cuaHang/"+ID_CUAHANG).child("thongtin").child("soNha").setValue(soNhaDuong).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (imageUri != null) {
                uploadToFirebase(imageUri);
            }
        }
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
                setDataText();
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

        for (int i = 0; i < listDiaChi.size(); i++){
            arr[i] = listDiaChi.get(i).getTenTinhTP();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[listDiaChi.get(pos).getHuyens().size()];

        for (int i = 0; i < listDiaChi.get(pos).getHuyens().size(); i++){
            arr[i] = listDiaChi.get(pos).getHuyens().get(i).getTenHuyen();
        }

        return arr;
    }
}