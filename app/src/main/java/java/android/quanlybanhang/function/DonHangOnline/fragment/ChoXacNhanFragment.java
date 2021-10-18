package java.android.quanlybanhang.function.DonHangOnline.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.android.quanlybanhang.HelperClasses.DanhSachHoaDonAdapter;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BaoCao.BaoCaoTongQuanActivity;
import java.android.quanlybanhang.function.DonHangOnline.adapter.ChoXacNhanAdapter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoXacNhanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoXacNhanFragment extends Fragment {

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
    private ArrayList<String> list = new ArrayList<>();
    private ChoXacNhanAdapter choXacNhanAdapter;
    private Dialog dialog;
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
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        dialog = new Dialog(view.getContext());
        getData();
        displayItem(view);

        return view;
    }

    private void getData() {

    }

    private void displayItem(View view){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));

        choXacNhanAdapter = new ChoXacNhanAdapter(view.getContext(), list, dialog);
        recyclerView.setAdapter(choXacNhanAdapter);

        choXacNhanAdapter.notifyDataSetChanged();
    }
}