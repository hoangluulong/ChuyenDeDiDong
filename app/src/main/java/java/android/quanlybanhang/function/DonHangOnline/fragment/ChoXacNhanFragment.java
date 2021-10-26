package java.android.quanlybanhang.function.DonHangOnline.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.SupportFragmentDonOnline;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.adapter.ChoXacNhanAdapter;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoXacNhanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoXacNhanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChoXacNhanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChoXacNhanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoXacNhanFragment newInstance(String param1, String param2) {
        ChoXacNhanFragment fragment = new ChoXacNhanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private ChoXacNhanAdapter choXacNhanAdapter;
    private Dialog dialog;
    private Dialog dialogHuy;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private ArrayList<DonHang> donHangs;
    private SupportFragmentDonOnline support = new SupportFragmentDonOnline();
    private TextView lblThongBao;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        lblThongBao = view.findViewById(R.id.lblThongBao);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.swipeRefreshlayout);

        progressBar.setVisibility(View.VISIBLE);

        donHangs = new ArrayList<>();
        dialog = new Dialog(view.getContext());
        dialogHuy = new Dialog(view.getContext());

        getDataFireBase(view);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }


    private void displayItem(View view) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        choXacNhanAdapter = new ChoXacNhanAdapter(view.getContext(), donHangs, dialog, dialogHuy);
        recyclerView.setAdapter(choXacNhanAdapter);

        choXacNhanAdapter.notifyDataSetChanged();
    }

    private void getDataFireBase(View view) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                donHangs = new ArrayList<>();
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snap : postSnapshot.getChildren()) {
                        DonHang donHang = snap.getValue(DonHang.class);
                        if (donHang.getTrangthai() == 0) {
                            donHangs.add(donHang);
                            Date date = support.formatDate(donHangs.get(i).getTime());
                            donHangs.get(i).setDate(date);
                            i++;
                        }
                    }
                }
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
                if (donHangs.size() > 0) {
                    lblThongBao.setText("");
                }else {
                    lblThongBao.setText("Không có đơn hàng chờ xác nhận nào");
                }
                support.SapXepDate(donHangs);
                displayItem(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onRefresh() {
        getDataFireBase(view);
    }
}