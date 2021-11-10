package fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shipper.R;
import com.example.shipper.Shipper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CaNhanFragment extends Fragment {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private TextView tv_name,tv_email,tv_phone,tv_date;

    public CaNhanFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_canhan, container, false);
        tv_name=view.findViewById(R.id.tv_hoten);
        tv_date=view.findViewById(R.id.tv_ngaysinh);
        tv_email=view.findViewById(R.id.tv_email);
        tv_phone=view.findViewById(R.id.tv_sdt);
        onDataChange();
        return view;
    }
    private void onDataChange()
    {
        mFirebaseAuth= FirebaseAuth.getInstance();
        String id=mFirebaseAuth.getUid();
        if (id!=null){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Shipper").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Shipper sp=snapshot.getValue(Shipper.class);
                    String nameShipper=sp.getNameShipper();
                    String phoneShipper= sp.getPhoneShipper();
                    String emailShipper=sp.getEmailShipper();
                    String dateShipper=sp.getDateShipper();
                    tv_name.setText(nameShipper);
                    tv_date.setText(dateShipper);
                    tv_email.setText(emailShipper);
                    tv_phone.setText(phoneShipper);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}}