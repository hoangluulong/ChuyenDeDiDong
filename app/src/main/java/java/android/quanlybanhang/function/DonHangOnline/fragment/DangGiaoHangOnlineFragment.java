package java.android.quanlybanhang.function.DonHangOnline.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.adapter.DangXuLiAdapter;
import java.android.quanlybanhang.function.DonHangOnline.adapter.DonHangDangGiaoAdapter;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangGiaoHangOnlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangGiaoHangOnlineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangGiaoHangOnlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangGiaoHangOnlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangGiaoHangOnlineFragment newInstance(String param1, String param2) {
        DangGiaoHangOnlineFragment fragment = new DangGiaoHangOnlineFragment();
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

    private RecyclerView recyclerView;
    private DonHangDangGiaoAdapter donHangDangGiaoAdapter;
    private Dialog dialog;
    private ArrayList<DonHang> donHangs;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_giao_hang_online, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        dialog = new Dialog(view.getContext());

        displayItem(view);
        return view;
    }

    private void displayItem(View view){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        donHangDangGiaoAdapter = new DonHangDangGiaoAdapter(view.getContext(), donHangs, dialog);
        recyclerView.setAdapter(donHangDangGiaoAdapter);

        getDataFireBase(view);

        donHangDangGiaoAdapter.notifyDataSetChanged();
    }

    private void getDataFireBase(View view) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                donHangs = new ArrayList<>();
                int i = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getTrangthai() == 3) {
                        donHangs.add(donHang);
                        Date date = formatDate(donHangs.get(i).getTime());
                        donHangs.get(i).setDate(date);
                        i++;
                    }
                }
                SapXepDate(donHangs);
                displayItem(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private Date formatDate(String strDate) {
//        String stringDate = "08:27 22/01/2016";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        }catch (Exception e){
            Date date = new Date();
            return date;
        }
    }

    private void SapXepDate(ArrayList<DonHang> donHangs) {
        Collections.sort(donHangs, new Comparator<DonHang>() {
            public int compare(DonHang o1, DonHang o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }
}