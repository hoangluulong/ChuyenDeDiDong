package java.android.quanlybanhang.function.SanPham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.Adapter.AdapterDonGia;
import java.android.quanlybanhang.Data.Category;
import java.android.quanlybanhang.Data.DonGia;
import java.android.quanlybanhang.Data.Product;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class SuaSanPhamActivity extends AppCompatActivity {
    private Product product;
    private EditText textName, textChitiet, textGianhap, textSoluong, textGiaSanPham,textTenDonViTinh;
    private Spinner spnNhomsanpham, spnDonViTinh;
    private Button btnAdd, btnThemDonViTinh, btnDonViTinhSanPham;
    private ImageView btnChoose;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri ImageUri;
    private RecyclerView listView;
    private String STR_NHOMSANPHAM = "danhmucsanpham";
    private String STR_SANPHAM = "sanpham";
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_UPLOAD = "uploads";
    private String STR_DONVITINH = "donvitinh";
    private String id;
    private ArrayList<DonGia> donGias;
    private ArrayList<String> arrayListCatagoty;
    private static final int PICK_IMAGE_REQUEST = 1;
    private AdapterDonGia adapterDonGia;
    private StorageReference mStogref;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private String nhomsanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        product = (Product) getIntent().getSerializableExtra("Key_aray");
        textName = findViewById(R.id.textTensanpham);
        textChitiet = findViewById(R.id.textChitietsanpham);
        textGianhap = findViewById(R.id.textGianhap);
        textSoluong = findViewById(R.id.textSoluong);
        spnNhomsanpham = findViewById(R.id.spnNhomsanpham);
        btnChoose = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgChoose);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAddproduct);
        btnDonViTinhSanPham = findViewById(R.id.DonViTinhSanPham);
        btnThemDonViTinh = findViewById(R.id.themDonViTinh);
        listView = findViewById(R.id.listGiaSanPham);
        //
        textName.setText(product.getNameProduct());
        textChitiet.setText(product.getChitiet());
        textGianhap.setText(product.getGiaNhap()+"");
        textSoluong.setText(product.getSoluong()+"");

        Picasso.get().load(product.getImgProduct()).into(imageView);
        donGias = product.getDonGia();
        adapterDonGia = new AdapterDonGia(SuaSanPhamActivity.this,donGias);
        listView.setLayoutManager(new LinearLayoutManager(SuaSanPhamActivity.this,LinearLayoutManager.VERTICAL,false));
        listView.setAdapter(adapterDonGia);
        adapterDonGia.notifyDataSetChanged();
        //firebase
        mStogref = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_NHOMSANPHAM);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_SANPHAM);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_DONVITINH);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListCatagoty = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    String name = category.getNameCategory();
                    arrayListCatagoty.add(name);
                }
                if(arrayListCatagoty.size()!=0){
                           ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuaSanPhamActivity.this, R.layout.support_simple_spinner_dropdown_item, arrayListCatagoty);
                           adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                           spnNhomsanpham.setAdapter(adapter);
                           if(product.getNhomsanpham() != null){
                               int com = adapter.getPosition(product.getNhomsanpham());
                               spnNhomsanpham.setSelection(com);
                           }
                }
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(product.getImgProduct());
                        if (ImageUri != null) {
                            StorageReference fileRefence = mStogref.child("uploads/" + System.currentTimeMillis() + "." + getFileExtenstion(ImageUri));
                            storageReference.delete();
                            fileRefence.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setProgress(0);

                                        }
                                    }, 5000);

                                    fileRefence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Toast.makeText(SuaSanPhamActivity.this, "Upload successfull", Toast.LENGTH_SHORT).show();
                                            if (textName.getText().toString().isEmpty()){
                                                textName.setError("Hãy nhập tên sản phẩm");
                                                textName.requestFocus();
                                            }else if (textChitiet.getText().toString().isEmpty()){
                                                textChitiet.setError("Hãy nhập chi tiết sản phẩm");
                                                textChitiet.requestFocus();
                                            }else if(textGianhap.getText().toString().isEmpty()){
                                                textGianhap.setError("Hãy nhập giá nhập sản phẩm");
                                                textGianhap.requestFocus();
                                            }else if(textSoluong.getText().toString().isEmpty()){
                                                textSoluong.setError("Hãy nhập số lượng sản phẩm");
                                                textSoluong.requestFocus();
                                            }
                                            else {

                                                String name = textName.getText().toString();
                                                String chitiet = textChitiet.getText().toString();
                                                Double gianhap = Double.parseDouble(textGianhap.getText().toString());
                                                Integer soluong = Integer.parseInt(textSoluong.getText().toString());
                                                nhomsanpham = spnNhomsanpham.getSelectedItem().toString();
                                                String img = uri.toString();
                                                String status = "Còn";
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("nameProduct").setValue(name);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("chitiet").setValue(chitiet);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("imgProduct").setValue(img);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("nhomsanpham").setValue(nhomsanpham);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("soluong").setValue(soluong);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("status").setValue(status);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("giaNhap").setValue(gianhap);
                                                mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("donGia").setValue(donGias);

                                            }

                                            Intent intent = new Intent();
                                            intent = new Intent(SuaSanPhamActivity.this,ListProduct.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SuaSanPhamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    progressBar.setProgress((int) progress);
                                }
                            });
                        }
                        else {
                            String name = textName.getText().toString();
                            String chitiet = textChitiet.getText().toString();
                            Double gianhap = Double.parseDouble(textGianhap.getText().toString());
                            Integer soluong = Integer.parseInt(textSoluong.getText().toString());
                            nhomsanpham = spnNhomsanpham.getSelectedItem().toString();
                            String status = "Còn";
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("nameProduct").setValue(name);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("chitiet").setValue(chitiet);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("nhomsanpham").setValue(nhomsanpham);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("soluong").setValue(soluong);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("status").setValue(status);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("giaNhap").setValue(gianhap);
                            mDatabase1.child(product.getNhomsanpham()).child(product.getId()).child("donGia").setValue(donGias);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
    }


    private void openFileChoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK
                && data!=null && data.getData()!=null)
        {
            ImageUri=data.getData();
            imageView.setImageURI(ImageUri);
        }
    }

    private String getFileExtenstion(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}