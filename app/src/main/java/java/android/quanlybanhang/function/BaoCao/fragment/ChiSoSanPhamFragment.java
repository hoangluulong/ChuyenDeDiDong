package java.android.quanlybanhang.function.BaoCao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.android.quanlybanhang.HelperClasses.ChiSoSanPhamAdapter;
import java.android.quanlybanhang.Model.SanPhamTop;
import java.android.quanlybanhang.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiSoSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiSoSanPhamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiSoSanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiSoSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiSoSanPhamFragment newInstance(String param1, String param2) {
        ChiSoSanPhamFragment fragment = new ChiSoSanPhamFragment();
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


    // TODO: Firebase
    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

    //TODO: ID
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ChiSoSanPhamAdapter chiSoSanPhamAdapter;
    private List<SanPhamTop> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_so_san_pham, container, false);
        data(view);

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        displayItem(view);

        setData();
        return view;
    }

    private void setData() {
        for (int i = 0; i < list.size(); i++) {
            pieChart.addPieSlice(
                    new PieModel(
                            list.get(i).getName(),
                            Integer.parseInt(list.get(i).getSoLuong() + ""),
                            Color.parseColor(list.get(i).getColor())));
        }
        pieChart.startAnimation();
    }

    private void displayItem(View view) {
        recyclerView = view.findViewById(R.id.recycler_chi_so_san_pham);

        chiSoSanPhamAdapter = new ChiSoSanPhamAdapter(view.getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(chiSoSanPhamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void data(View view) {
        list = new ArrayList<>();

        Random obj = new Random();

        list.add(new SanPhamTop("Long hoang huu", 10));
        list.add(new SanPhamTop("Hoang huu long", 30));
        list.add(new SanPhamTop("Long huu hoang", 10));
        list.add(new SanPhamTop("Long huu huu", 60));
        list.add(new SanPhamTop("Long huu tis", 90));


        for (int i = 0; i < list.size(); i++) {
            int rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            list.get(i).setColor(colorCode);
        }
    }
}