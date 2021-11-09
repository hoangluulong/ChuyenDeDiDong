package java.android.quanlybanhang.function.CuaHangOnline.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Data.SanPhamQuangCao;
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
    private TextView chonSanPham, btnTaoDonMoi, lblLoai, btnHuy, taoDon;
    private AutoCompleteTextView nhomsanpham;
    private TextInputEditText tenSanPham, soLuong, giaban, giamgia, mota;
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

        getNhomSanPham();
        return view;
    }

    private void IDLayout(View view) {
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

        paramsImage = (LinearLayout.LayoutParams) layout_image.getLayoutParams();
        paramsImage1 = (LinearLayout.LayoutParams) layout_image1.getLayoutParams();

        params1 = (LinearLayout.LayoutParams) quangCaoSanPham.getLayoutParams();

        btnQuangCaoSanPham.setOnClickListener(this);
        addImage.setOnClickListener(this);
        taoDon.setOnClickListener(this);
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
        }
    }

    private String name;
    private int sLuong = 0;
    private long gia = 0;
    private long giamGia = 0;
    private String moTa;
    private String nameImage;

    private void uploadFirebaseQuangCao(Uri uri) {
        nameImage = System.currentTimeMillis() + "." + getFileExtension(uri);
        name = tenSanPham.getText().toString();
        String sLuongText = soLuong.getText().toString();
        String sGiaBanText = giaban.getText().toString();
        String sGiamGiaText = giamgia.getText().toString();
        moTa = mota.getText().toString();

        if (!sLuongText.isEmpty() && isNumeric(sLuongText)) {
            sLuong = Integer.parseInt(sLuongText);
        }
        if (!sGiaBanText.isEmpty() && isNumeric(sGiaBanText)) {
            gia = Long.parseLong(sGiaBanText);
        }
        if (!sGiamGiaText.isEmpty() && isNumeric(sGiamGiaText)) {
            giamGia = Long.parseLong(sGiamGiaText);
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
        } else if (gia < 1000) {
            giaban.setError("Sản phẩm phải > 1000");
            giaban.requestFocus();
        } else if (giamGia > gia) {
            giamgia.setError("giảm giá lớn hơn đơn giá");
            giamgia.requestFocus();
        } else {
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
                            progressBar.setVisibility(View.INVISIBLE);
                            tenSanPham.setText(null);
                            soLuong.setText(null);
                            giamgia.setText(null);
                            giaban.setText(null);
                            imgageView.setImageURI(null);
                            nhomsanpham.setText(null);
                            addImage.setBackgroundResource(R.drawable.border_image_dashed);
                            mota.setText(null);
                            imageUri = null;

                            paramsImage.height = 0;
                            layout_image.setLayoutParams(paramsImage);
                            paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            layout_image1.setLayoutParams(paramsImage1);
                            taoDon.setEnabled(true);
                            SanPhamQuangCao sanPhamQuangCao = new SanPhamQuangCao(ID_CUAHANG, key, name, sLuong, gia, giamGia, nhomSp, moTa, nameImage, uri.toString(), false, "20-11-2021", "22-11-2021");
                            Log.d("qqq", sanPhamQuangCao.getImageUrl());
                            DatabaseReference mFirebaseDatabase1 = mFirebaseInstance.getReference();
                            mFirebaseDatabase1.child("sanPhamQuangCao/" + ID_CUAHANG + "/sanpham/" + key).setValue(sanPhamQuangCao).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                    name = null;
                                    sLuong = 0;
                                    gia = 0;
                                    giamGia = 0;
                                    moTa = null;
                                    nameImage = null;
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