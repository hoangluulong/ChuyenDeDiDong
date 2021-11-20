package java.android.quanlybanhang.function.CuaHangOnline.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Model.ChucNangThanhToan.DonGia;
import java.android.quanlybanhang.Model.SanPham.DonViTinh;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Adapter.AdapterDonGia;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.android.quanlybanhang.function.CuaHangOnline.TaoSanPhamOnlineActivity;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddQuanCaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddQuanCaoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddQuanCaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddQuanCaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddQuanCaoFragment newInstance(String param1, String param2) {
        AddQuanCaoFragment fragment = new AddQuanCaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private LinearLayout btnQuangCaoSanPham, quangCaoSanPham, layout_image1, addImage, layout_image;
    private ImageView quangCaosanPhamIMG, imgageView;
    private TextView chonSanPham, btnTaoDonMoi, lblLoai, btnHuy, taoDon, addDonViTinh, addLoai;
    private AutoCompleteTextView nhomsanpham;
    private TextInputEditText tenSanPham, soLuong, giaban, giamgia, mota, title;
    private boolean setL1 = true;
    private Uri imageUri;
    private LinearLayout.LayoutParams params1, paramsImage, paramsImage1;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String ID_CUAHANG;
    private ProgressBar progressBar;
    private String nhomSp = "";
    private ArrayAdapter<String> adapterNhomSp;
    private ArrayList<String> listNhom = new ArrayList<>();
    private ProgressBar progressBarLayout;
    private ScrollView scrollView;
    private RecyclerView listView;

    private Window window, window1;
    private Dialog dialog, dialog1;
    private EditText textGiaSanPham,textTenDonViTinh;
    private Spinner spnDonViTinh;
    private Button btnDialogHuyDVT, btnDialogThemDVT, btnDialogHuyThemDVT, btnThemDialogThemDVT;
    private ArrayAdapter<String> adapter;
    private String STR_CUAHANG = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";
    private String STR_DONVITINH = "donvitinh";
    private ArrayList<String> listDonViTinh;
    private ArrayList<DonGia> listDonGia = new ArrayList<>();
    private AdapterDonGia adapterDonGia;
    private String id;
    private DonViTinh donViTinh;
    private Product product;
    private AutoCompleteTextView spnTenDonViTinh2;
    private String nhom = "";

    private DatabaseReference mDatabase2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_quan_cao, container, false);
        thongTinCuaHangSql = new ThongTinCuaHangSql(view.getContext());
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        IDLayout(view);

        paramsImage.height = 0;
        layout_image.setLayoutParams(paramsImage);
        paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layout_image1.setLayoutParams(paramsImage1);

        progressBarLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailongdonvitinh);
        window = dialog.getWindow(); dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailongdonvitinh);
        window = dialog.getWindow();

        dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemdonvitinh);
        window1 = dialog1.getWindow();
        textTenDonViTinh = dialog1.findViewById(R.id.edtTenDonViTinh);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);

        textGiaSanPham = dialog.findViewById(R.id.tedtGiaDonVi);
        spnDonViTinh = dialog.findViewById(R.id.spnTenDonViTinh);
        spnTenDonViTinh2 = dialog.findViewById(R.id.spnTenDonViTinh2);
        btnDialogHuyDVT = dialog.findViewById(R.id.btnhuyDiaLogDVT);
        btnDialogThemDVT = dialog.findViewById(R.id.btnthemDiaLogDVT);

        getNhomSanPham();
        return view;
    }

    private void IDLayout(View view) {
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_DONVITINH);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        quangCaoSanPham = view.findViewById(R.id.quangCaoSanPham);
        btnQuangCaoSanPham = view.findViewById(R.id.btnQuangCaoSanPham);
        quangCaosanPhamIMG = view.findViewById(R.id.quangCaosanPhamIMG);
        addImage = view.findViewById(R.id.addImage);
        imgageView = view.findViewById(R.id.imgageView);
        taoDon = view.findViewById(R.id.taoDon);
        tenSanPham = view.findViewById(R.id.tenSanPham);
        soLuong = view.findViewById(R.id.soLuong);
        giaban = view.findViewById(R.id.giaban);
        giamgia = view.findViewById(R.id.giamgia);
        mota = view.findViewById(R.id.mota);
        progressBar = view.findViewById(R.id.progressBar);
        layout_image = view.findViewById(R.id.layout_image);
        layout_image1 = view.findViewById(R.id.layout_image1);
        nhomsanpham = view.findViewById(R.id.nhomsanpham);
        progressBarLayout = view.findViewById(R.id.progressBarLayout);
        scrollView = view.findViewById(R.id.scrollView4);
        addDonViTinh = view.findViewById(R.id.addDonViTinh);
        addLoai = view.findViewById(R.id.addLoai);
        listView = view.findViewById(R.id.donViTinh);
        title = view.findViewById(R.id.title);

        paramsImage = (LinearLayout.LayoutParams) layout_image.getLayoutParams();
        paramsImage1 = (LinearLayout.LayoutParams) layout_image1.getLayoutParams();

        params1 = (LinearLayout.LayoutParams) quangCaoSanPham.getLayoutParams();

        btnQuangCaoSanPham.setOnClickListener(this);
        addImage.setOnClickListener(this);
        taoDon.setOnClickListener(this);
        addDonViTinh.setOnClickListener(this);
        addLoai.setOnClickListener(this);

        taoDon.setEnabled(true);

        adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);
        nhomsanpham.setAdapter(adapterNhomSp);
        nhomsanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);

                nhomSp = parent.getItemAtPosition(position).toString();
                nhomsanpham.setText(nhomSp);
                nhomsanpham.setAdapter(adapterNhomSp);
            }
        });
        adapterNhomSp.notifyDataSetChanged();
    }

    private void getNhomSanPham() {
        mFirebaseDatabase.child("nhomSanPham").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        listNhom.add(snap.getValue().toString());
                    }

                    adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);
                    nhomsanpham.setAdapter(adapterNhomSp);
                    adapterNhomSp.notifyDataSetChanged();
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                } else {
                    progressBarLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        //sanphamQuangcao
        switch (v.getId()) {
            case R.id.btnQuangCaoSanPham:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    quangCaosanPhamIMG.setImageResource(R.drawable.down_24);
                    quangCaoSanPham.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    quangCaosanPhamIMG.setImageResource(R.drawable.up_24);
                    quangCaoSanPham.setLayoutParams(params1);
                }
                break;
            case R.id.addImage:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;

            case R.id.taoDon:
                if (imageUri != null) {
                    uploadFirebaseQuangCao(imageUri);
                } else {
                    Toast.makeText(v.getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.addLoai:
                donViTinh(Gravity.CENTER);
                break;
            case R.id.addDonViTinh:
                themDonViTinh(Gravity.CENTER);
                break;
        }
    }

    private void themDonViTinh(int gravity) {
        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        windownAttributes.gravity = gravity;
        window1.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog1.setCancelable(true);
        }
        else {
            dialog1.setCancelable(false);
        }

        btnDialogHuyThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textTenDonViTinh.getText().toString();
                String id = mDatabase2.push().getKey();
                donViTinh = new DonViTinh(name,id);
                mDatabase2.child(id).setValue(donViTinh);
                textTenDonViTinh.setText("");
                dialog1.dismiss();
            }
        });
        dialog1.show();

    }

    private void donViTinh(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }

        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDonViTinh = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DonViTinh donViTinh1 = snapshot1.getValue(DonViTinh.class);
                    String name = donViTinh1.getDonViTinh();
                    listDonViTinh.add(name);
                }

                nhom = listDonViTinh.get(0);
                adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listDonViTinh);
                spnTenDonViTinh2.setText(nhom);
                spnTenDonViTinh2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spnTenDonViTinh2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nhom = parent.getItemAtPosition(position).toString();
                spnTenDonViTinh2.setText(nhom);
                adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listDonViTinh);
                spnTenDonViTinh2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonGia donGia = new DonGia();
                donGia.setId(id);
                if (textGiaSanPham.getText().toString().isEmpty()){
                    textGiaSanPham.setError("Hãy nhập giá !!!");
                    textGiaSanPham.requestFocus();
                    getActivity().setFinishOnTouchOutside(true);
                }
                else {
                    donGia.setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                    listDonGia.add(donGia);
                    dialog.dismiss();
                }
                textGiaSanPham.setText("");
                adapterDonGia = new AdapterDonGia(getContext(),listDonGia,dialog,window,adapter,gravity);
                listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                listView.setAdapter(adapterDonGia);
                adapterDonGia.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    private String name;
    private int sLuong = 0;
    private double giamGia = 0;
    private String moTa;
    private String nameImage;

    private void uploadFirebaseQuangCao(Uri uri) {
        nameImage = System.currentTimeMillis() + "." + getFileExtension(uri);
        name = tenSanPham.getText().toString();
        String sLuongText = soLuong.getText().toString();
        String sGiaBanText = giaban.getText().toString();
        String sGiamGiaText = giamgia.getText().toString();
        moTa = mota.getText().toString();
        String titleText = title.getText().toString();

        if (!sLuongText.isEmpty()) {
            sLuong = Integer.parseInt(sLuongText);
        }
        if (!sGiamGiaText.isEmpty()) {
            giamGia = Double.parseDouble(sGiamGiaText);
        }

        if (name.isEmpty()) {
            tenSanPham.setError("Nhập tên sản phẩm");
            tenSanPham.requestFocus();
        } else if (sLuongText.isEmpty()) {
            soLuong.setError("Nhập số lượng");
            soLuong.requestFocus();
        } else if (!sLuongText.isEmpty() && !isNumeric(sLuongText)) {
            soLuong.setError("Nhập đúng định dạng");
            soLuong.requestFocus();
        } else if (sGiaBanText.isEmpty()) {
            giaban.setError("Nhập giá bán");
            giaban.requestFocus();
        } else if (!sGiaBanText.isEmpty() && !isNumeric(sGiaBanText)) {
            giaban.setError("Nhập đúng định dạng");
            giaban.requestFocus();
        } else if (sGiamGiaText.isEmpty()) {
            giamgia.setError("Nhập giảm giá");
            giamgia.requestFocus();
        } else if (!sGiamGiaText.isEmpty() && !isNumeric(sGiamGiaText)) {
            giamgia.setError("Nhập đúng định dạng");
            giamgia.requestFocus();
        } else if (nhomSp.isEmpty()) {
            nhomsanpham.setError("Chọn nhóm sản phẩm");
            nhomsanpham.requestFocus();
        } else if (sLuong < 1) {
            soLuong.setError("Số lượng phải lớn hơn 1");
            soLuong.requestFocus();
        } else if (moTa.isEmpty()) {
            mota.setError("Mô tả sản phẩm");
            mota.requestFocus();
        }else if (titleText.isEmpty()) {
            mota.setError("Mô tả sản phẩm");
            mota.requestFocus();
        } else if (listDonGia.size()<=0) {
            Toast.makeText(getContext(), "Chưa tạo đơn giá", Toast.LENGTH_SHORT).show();
        } else{
            taoDon.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);


            final StorageReference fileRef = reference.child(nameImage);
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String key = mFirebaseDatabase.push().getKey();
                            Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

                            paramsImage.height = 0;
                            layout_image.setLayoutParams(paramsImage);
                            paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            layout_image1.setLayoutParams(paramsImage1);
                            taoDon.setEnabled(true);
                            String status = "Còn";
                            String img = uri.toString();
                            product = new Product(key,name,moTa,nhomSp,0.0, sLuong, img, nameImage, giamGia, status, listDonGia, ID_CUAHANG, false, titleText);
                            DatabaseReference mFirebaseDatabase1 = mFirebaseInstance.getReference();
                            mFirebaseDatabase1.child("sanPhamQuangCao/" + ID_CUAHANG + "/sanpham/" + key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                    name = null;
                                    sLuong = 0;
                                    giamGia = 0;
                                    moTa = null;
                                    nameImage = null;
                                    tenSanPham.setText(null);
                                    soLuong.setText(null);
                                    giamgia.setText(null);
                                    giaban.setText(null);
                                    imgageView.setImageURI(null);
                                    nhomsanpham.setText(null);
                                    title.setText(null);
                                    addImage.setBackgroundResource(R.drawable.border_image_dashed);
                                    mota.setText(null);
                                    imageUri = null;
                                    listDonGia.clear();
                                    adapterDonGia.notifyDataSetChanged();
                                    progressBar.setVisibility(View.INVISIBLE);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });

        }
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            paramsImage1.height = 0;
            layout_image1.setLayoutParams(paramsImage1);

            paramsImage.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layout_image.setLayoutParams(paramsImage);

            imageUri = data.getData();
            imgageView.setImageURI(imageUri);
            addImage.setBackgroundResource(R.drawable.border_image);
        }
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}