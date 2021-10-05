package java.android.quanlybanhang.function.BaoCao.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

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

    private HorizontalBarChart barChart;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_so_san_pham, container, false);
        barChart = view.findViewById(R.id.horizonChar);

        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        barEntries.add(new BarEntry(0, 1));
        barEntries.add(new BarEntry(1, 2));
        barEntries.add(new BarEntry(2, 4));
        barEntries.add(new BarEntry(3, 6));
        barEntries.add(new BarEntry(4, 5));
        barEntries.add(new BarEntry(5, 7));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Contracts");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setHighLightColor(Color.RED);

        BarData barData = new BarData(barDataSet);

        barChart.getDescription().setText("Top sản phẩm bán chạy nhất");
        barChart.getDescription().setTextSize(12);
        barChart.setDrawMarkers(true);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        ArrayList<String> labels = new ArrayList<String> ();

        labels.add( "Redbull");
        labels.add( "Trà sữa mimi");
        labels.add( "Hello kus");
        labels.add( "aa");
        labels.add( "MAY");
        labels.add( "JUN");


        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.animateY(1000);

        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setGranularity(1.0f);
        barChart.getXAxis().setLabelCount(barDataSet.getEntryCount());

        barChart.setData(barData);


        return view;
    }

    private void getDataSanPham() {

    }
}