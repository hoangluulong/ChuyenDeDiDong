package java.android.quanlybanhang.function.BaoCao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.android.quanlybanhang.HelperClasses.ChiSoSanPham2Adapter;
import java.android.quanlybanhang.HelperClasses.ChiSoSanPhamAdapter;
import java.android.quanlybanhang.HelperClasses.ChiSoSanPhamNamAdapter;
import java.android.quanlybanhang.Model.SanPhamTop;
import java.android.quanlybanhang.R;
import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachTopSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachTopSanPhamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachTopSanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachTopSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachTopSanPhamFragment newInstance(String param1, String param2) {
        Log.d("www param1", param1);
        DanhSachTopSanPhamFragment fragment = new DanhSachTopSanPhamFragment();
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


    private PieChart piechart, piecharOnline, piecharCuaHang;
    private TextView lblTitle, lblPhanTramOnline, lblPhanCuaHang, lblTitleThang, lblnam;
    public TextView lblTatCa1, lblTatCa2;
    private RecyclerView recyclerViewThang, recyclerViewNam;
    private ArrayList<SanPhamTop> list;
    private ChiSoSanPham2Adapter chiSoSanPham2Adapter;
    private ChiSoSanPhamNamAdapter chiSoSanPhamNamAdapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_top_san_pham, container, false);
        anhXaID(view);

        data();
        setPie();
        displayItem(view);
        return view;
    }

    private void setPie() {
        Random obj = new Random();
        String colorCode1 = String.format("#%06x", obj.nextInt(0xffffff + 1));
        String colorCode2 = String.format("#%06x", obj.nextInt(0xffffff + 1));

        int online = 80;
        int cuuhang = 20;

        piecharOnline.addPieSlice(
                new PieModel(
                        "online",
                        online,
                        Color.parseColor(colorCode1)));
        piecharOnline.addPieSlice(
                new PieModel(
                        "online",
                        cuuhang,
                        Color.parseColor("#FFFFFFFF")));
        piecharCuaHang.addPieSlice(
                new PieModel(
                        "Cửa hàng",
                        cuuhang,
                        Color.parseColor(colorCode2)));
        piecharCuaHang.addPieSlice(
                new PieModel(
                        "Cửa hàng",
                        online,
                        Color.parseColor("#FFFFFFFF")));
    }

    private void data() {
        list = new ArrayList<>();

        Random obj = new Random();
        list.add(new SanPhamTop("Long hoang huu", 50, 5000));
        list.add(new SanPhamTop("Hoang huu long", 30, 4000));
        list.add(new SanPhamTop("Long huu hoang", 10, 8000));
        list.add(new SanPhamTop("Long huu huu", 60, 200));
        list.add(new SanPhamTop("Long huu tis", 90, 7000));
        list.add(new SanPhamTop("Long hoang huu", 10, 10000));


        list.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());

        for (int i = 0; i < list.size(); i++) {
            int rand_num = obj.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", rand_num);
            list.get(i).setColor(colorCode);
        }
    }

    private void displayItem(View view) {
        recyclerViewThang = view.findViewById(R.id.recylerThang);
        chiSoSanPham2Adapter = new ChiSoSanPham2Adapter(view.getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewThang.setAdapter(chiSoSanPham2Adapter);
        recyclerViewThang.setLayoutManager(linearLayoutManager);

        recyclerViewNam = view.findViewById(R.id.recylerNam);
        chiSoSanPhamNamAdapter = new ChiSoSanPhamNamAdapter(view.getContext(), list);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext());
        recyclerViewNam.setAdapter(chiSoSanPham2Adapter);
        recyclerViewNam.setLayoutManager(linearLayoutManager1);

    }

    private void anhXaID(View view){
        piechart = (PieChart) view.findViewById(R.id.piechart);
        piecharOnline = (PieChart) view.findViewById(R.id.piecharOnline);
        piecharCuaHang = (PieChart) view.findViewById(R.id.piecharCuaHang);
        lblPhanTramOnline = view.findViewById(R.id.lblPhanTramOnline);
        lblPhanCuaHang = view.findViewById(R.id.lblPhanTramCuaHang);
        lblTitleThang = view.findViewById(R.id.lblTitleThang);
        lblTatCa1 = view.findViewById(R.id.lblTatCa1);
        lblTatCa2 = view.findViewById(R.id.lblTatCa2);
        lblnam = view.findViewById(R.id.lblTitleNam);
        recyclerViewNam = view.findViewById(R.id.recylerNam);
        recyclerViewThang = view.findViewById(R.id.recylerThang);
    }
}