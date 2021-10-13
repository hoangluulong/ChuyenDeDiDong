package java.android.quanlybanhang.function.SanPham;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import java.android.quanlybanhang.Adapter.AdapterDonGia;
import java.android.quanlybanhang.Data.Category;
import java.android.quanlybanhang.Data.DonGia;
import java.android.quanlybanhang.Data.DonViTinh;
import java.android.quanlybanhang.Data.NhanVien;
import java.android.quanlybanhang.Data.Product;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.NhanVien.AddNhanVien;
import java.util.ArrayList;
import java.util.List;

public class AddProduct extends AppCompatActivity {
    private EditText textName, textChitiet, textGianhap, textSoluong, textGiaSanPham,textTenDonViTinh;
    private Spinner spnNhomsanpham, spnDonViTinh;
    private Button btnAdd, btnChoose, btnThemDonViTinh, btnDonViTinhSanPham;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Product product;
    private ArrayList<String> arrayList;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri ImageUri;
    private StorageReference mStogref;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private View customLayout;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private LayoutInflater inflater1;
    private View customLayout1;
    private AlertDialog.Builder builder1;
    private ArrayList<String> listDonViTinh;
    private ArrayList<DonGia> listDonGia = new ArrayList<>();
    private AdapterDonGia adapterDonGia;
    private RecyclerView listView;
    private DonViTinh donViTinh;

    private String STR_NHOMSANPHAM = "danhmucsanpham";
    private String STR_SANPHAM = "sanpham";
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_UPLOAD = "uploads";
    private String STR_DONVITINH = "donvitinh";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        //
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

       //firebase
        mStogref = FirebaseStorage.getInstance().getReference(STR_UPLOAD);
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_NHOMSANPHAM);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_SANPHAM);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_DONVITINH);
        //dailong
        builder = new android.app.AlertDialog.Builder(AddProduct.this);
        inflater = AddProduct.this.getLayoutInflater();
        customLayout = inflater.inflate(R.layout.activity_dailongthemdonvitinh, null);
        builder.setView(customLayout);
        builder1 = new android.app.AlertDialog.Builder(AddProduct.this);
        inflater1 = AddProduct.this.getLayoutInflater();
        customLayout1 = inflater.inflate(R.layout.activity_dailongdonvitinh, null);
        builder1.setView(customLayout1);
        //

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
       uploadFile();
       dailongDonViTinhSanPham();
       dailongThemDonViTinh();
    }

    private String getFileExtenstion(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    String name = category.getNameCategory();
                    arrayList.add(name);
                }
                if (arrayList.size() != 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProduct.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spnNhomsanpham.setAdapter(adapter);
                }
              btnAdd.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (ImageUri != null) {
                          StorageReference fileRefence = mStogref.child("uploads/" + System.currentTimeMillis() + "." + getFileExtenstion(ImageUri));
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
                                          Toast.makeText(AddProduct.this, "Upload successfull", Toast.LENGTH_SHORT).show();
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
                                          }else {
                                              String id = mDatabase1.push().getKey();
                                              String name = textName.getText().toString();
                                              String chitiet = textChitiet.getText().toString();
                                              Double gianhap = Double.parseDouble(textGianhap.getText().toString());
                                              Integer soluong = Integer.parseInt(textSoluong.getText().toString());
                                              String nhomsanpham = spnNhomsanpham.getSelectedItem().toString();
                                              String img = uri.toString();
                                              String status = "Còn";
                                              Boolean addTocard = false;
                                              product = new Product(id,name,chitiet,nhomsanpham,gianhap,listDonGia,soluong,img,addTocard,status);
                                              mDatabase1.child(nhomsanpham).child(id).setValue(product);
                                          }

                                      }
                                  });


                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
                          Toast.makeText(AddProduct.this, "No file upload", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void openFileChoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private void dailongDonViTinhSanPham(){
        btnDonViTinhSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spnDonViTinh = customLayout.findViewById(R.id.spnTenDonViTinh);
                textGiaSanPham = customLayout.findViewById(R.id.tedtGiaDonVi);
                builder.setTitle("Đơn vị tính");
                if(customLayout.getParent() != null){
                    ((ViewGroup)customLayout.getParent()).removeView(customLayout);
                }
                mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listDonViTinh = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            DonViTinh donViTinh1 = snapshot1.getValue(DonViTinh.class);
                            String name = donViTinh1.getDonViTinh();
                            listDonViTinh.add(name);
                        }
                        if (listDonViTinh.size() != 0) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProduct.this, R.layout.support_simple_spinner_dropdown_item, listDonViTinh);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                            spnDonViTinh.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DonGia donGia = new DonGia();
                        donGia.setTenDonGia(spnDonViTinh.getSelectedItem().toString());
                        if (textGiaSanPham.getText().toString().isEmpty()){
                           textGiaSanPham.setError("Hãy nhập giá !!!");
                           textGiaSanPham.requestFocus();
                            builder.setCancelable(false);
                            setFinishOnTouchOutside(true);
                        }
                        else {
                            donGia.setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                            listDonGia.add(donGia);
                        }
                        textGiaSanPham.setText("");
                        adapterDonGia = new AdapterDonGia(listDonGia);
                        listView.setLayoutManager(new LinearLayoutManager(AddProduct.this,LinearLayoutManager.VERTICAL,false));
                        listView.setAdapter(adapterDonGia);
                        adapterDonGia.notifyDataSetChanged();
//                        adapterDonGia = new AdapterDonGia(AddProduct.this,R.layout.activity_itemlistdongia,listDonGia);
//                        listView.setAdapter(adapterDonGia);
                    }

                });
                builder.show();
            }
        });
    }

    private void dailongThemDonViTinh(){
       btnThemDonViTinh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(customLayout1.getParent() != null){
                   ((ViewGroup)customLayout1.getParent()).removeView(customLayout1);
               }
               builder1.setTitle("Thêm đơn vị tính");
               textTenDonViTinh = customLayout1.findViewById(R.id.edtTenDonViTinh);
               builder1.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               builder1.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String name = textTenDonViTinh.getText().toString();
                       String id = mDatabase2.push().getKey();
                       donViTinh = new DonViTinh(name,id);
                       mDatabase2.child(id).setValue(donViTinh);
                       textChitiet.setText("");
                   }

               });
               builder1.show();
           }
       });


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
}

