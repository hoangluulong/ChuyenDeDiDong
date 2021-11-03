package java.android.quanlybanhang.function.CuaHangOnline.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Data.SanPhamQuangCao;

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


    private LinearLayout btnQuangCaoSanPham, quangCaoSanPham, layou_timgagem, layou_timgage1, addImage;
    private ImageView quangCaosanPhamIMG, imgageView;
    private TextView chonSanPham, btnTaoDonMoi, lblLoai, btnHuy, taoDon;
    private TextInputEditText tenSanPham, soLuong, giaban, giamgia, mota;
    private boolean setL1 = true;
    private Uri imageUri;
    private LinearLayout.LayoutParams params1, params2;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_quan_cao, container, false);

        IDLayout(view);
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


        params1 = (LinearLayout.LayoutParams) quangCaoSanPham.getLayoutParams();

        btnQuangCaoSanPham.setOnClickListener(this);
        addImage.setOnClickListener(this);
        taoDon.setOnClickListener(this);
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

    private void uploadFirebaseQuangCao(Uri uri) {

        String name = tenSanPham.getText().toString();
        String sLuongText = soLuong.getText().toString();
        int sLuong = Integer.parseInt(soLuong.getText().toString());
        String sGiaBanText = giaban.getText().toString();
        long gia = Long.parseLong(giaban.getText().toString());
        long giamGia = Long.parseLong(giamgia.getText().toString());
        String moTa = mota.getText().toString();

        if (name.isEmpty()) {
            tenSanPham.setError("Nhập tên sản phẩm");
            tenSanPham.requestFocus();
        } else if (sLuongText.isEmpty()) {
            soLuong.setError("Nhập số lượng");
            soLuong.requestFocus();
        } else if (sLuong > 0) {
            soLuong.setError("Số lượng > 0");
            soLuong.requestFocus();
        } else if (sGiaBanText.isEmpty()) {
            giaban.setError("Nhập giá bán");
            giaban.requestFocus();
        }else {
            SanPhamQuangCao sanPhamQuangCao = new SanPhamQuangCao(name,sLuong, gia, giamGia, moTa, imageUri);

            mFirebaseDatabase.child("sanphamQuangcao/sanpham").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            uploadToFirebase(uri);
        }
    }

    private void uploadToFirebase(Uri uri) {
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(uri.toString());

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

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            imgageView.setImageURI(imageUri);
            addImage.setBackgroundResource(R.drawable.border_image);
        }
    }

}