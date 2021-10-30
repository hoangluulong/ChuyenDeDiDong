package java.android.quanlybanhang.function.BaoCao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.android.quanlybanhang.Common.FormatDate;
import java.android.quanlybanhang.HelperClasses.ChiSoSanPhamAdapter;
import java.android.quanlybanhang.Model.PieTongQuan;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BaoCao.SanPhamBaoCao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private PieChart pieChart, pieChartOnline, pieChartCuaHang;
    private RecyclerView recyclerView;
    private ChiSoSanPhamAdapter chiSoSanPhamAdapter;
    private ArrayList<PieTongQuan> dsSanPham = new ArrayList<>();
    private ArrayList<SanPhamBaoCao> dsSanPhamBienLai = new ArrayList<>();
    private ArrayList<Integer> checkThu = new ArrayList<>();
    private ArrayList<Integer> checkDSSP = new ArrayList<>();
    private ArrayList<String> listDays;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chi_so_san_pham, container, false);

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChartOnline = (PieChart) view.findViewById(R.id.piecharOnline);
        pieChartCuaHang = (PieChart) view.findViewById(R.id.piecharTaiQuan);
        listDays = MangNgay();

        getDataBienLai();
        setData();
        return view;
    }

    int i = 0;
    private void setData() {
        dsSanPham.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int demThu = 0;
                if (checkThu.size() == listDays.size()) {
                    for (int i = 0; i < dsSanPhamBienLai.size(); i++) {
                        if (checkThu.get(i) == 0) {
                            demThu++;
                        }
                    }

                    if (demThu == dsSanPhamBienLai.size()) {
                        Toast.makeText(view.getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        i= 0;
                    }else {
                        ArrayList<PieTongQuan> sp = new ArrayList<>();
                        Random obj = new Random();

                        ArrayList<String> list = new ArrayList<>();

                        for (int i = 0; i< dsSanPhamBienLai.size(); i ++) {
                            list.add(dsSanPhamBienLai.get(i).getTensanpham());
                        }

                        int count = list.size();
                        for (int i = 0; i < count; i++){
                            for (int j = i + 1; j < count; j++){
                                if (list.get(i).equals(list.get(j))){
                                    list.remove(j--);
                                    count--;
                                }
                            }
                        }

                        for (int i = 0; i < list.size(); i++){
                            dsSanPham.add(new PieTongQuan(list.get(i), 0));
                        }


                        for (int i = 0; i < dsSanPham.size(); i++) {
                            for (int j = 0; j < dsSanPhamBienLai.size(); j++) {
                                if (dsSanPham.get(i).getName().equals(dsSanPhamBienLai.get(j).getTensanpham())) {
                                    dsSanPham.get(i).setSoLuong(dsSanPham.get(i).getSoLuong() + dsSanPhamBienLai.get(j).getSoluong());
                                    dsSanPham.get(i).setGia(dsSanPham.get(i).getGia() + dsSanPhamBienLai.get(j).getGiatien());
                                }
                            }
                        }

//                        Log.d("sss", dsSanPham.get(0).getSoLuong()+"v" + dsSanPham.get(0).getGia());

                        for (int i = 0; i < dsSanPham.size(); i++) {
                            if (dsSanPham.get(i).getSoLuong() > 0 || dsSanPham.get(i).getGia() > 0) {
                                Log.d("sss", dsSanPham.get(i).getName()+"v");
                                sp.add(dsSanPham.get(i));
                            }
                        }

                        sp = dsSanPham;

                        sp.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());

                        for (int i = 0; i < sp.size(); i++) {
                            int rand_num = obj.nextInt(0xffffff + 1);
                            String colorCode = String.format("#%06x", rand_num);
                            sp.get(i).setColor(colorCode);
                        }

                        for (int i = 0; i < sp.size(); i++) {
                            pieChart.addPieSlice(
                                    new PieModel(
                                            sp.get(i).getName(),
                                            sp.get(i).getSoLuong(),
                                            Color.parseColor(sp.get(i).getColor())));
                        }
                        pieChart.startAnimation();

                        String colorCode1 = String.format("#%06x", obj.nextInt(0xffffff + 1));
                        String colorCode2 = String.format("#%06x", obj.nextInt(0xffffff + 1));

                        int online = 10;
                        int cuuhang = 20;

                        pieChartOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        online,
                                        Color.parseColor(colorCode1)));
                        pieChartOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        cuuhang,
                                        Color.parseColor("#FFFFFFFF")));
                        pieChartCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        cuuhang,
                                        Color.parseColor(colorCode2)));
                        pieChartCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        online,
                                        Color.parseColor("#FFFFFFFF")));

                        i = 0;

                        displayItem(view, sp);
                    }

                }else {
                    i++;
                    if (i <= 500) {
                        setData();
                    } else {
                        i = 0;
                    }
                }

            }
        }, 50);
    }

    private void displayItem(View view, ArrayList<PieTongQuan> sp) {
        Log.d("sss", sp.size()+"");
        recyclerView = view.findViewById(R.id.recycler_chi_so_san_pham);
        chiSoSanPhamAdapter = new ChiSoSanPhamAdapter(view.getContext(), sp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(chiSoSanPhamAdapter);
        chiSoSanPhamAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getDataBienLai() {
        dsSanPhamBienLai.clear();
        checkThu.clear();
        for (String st : listDays) {
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + st).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot snap : snapshot.child("sanpham").getChildren()) {
                                SanPhamBaoCao sanpham = snap.getValue(SanPhamBaoCao.class);
                                dsSanPhamBienLai.add(sanpham);
                            }
                        }
                        checkThu.add(1);
                    } else {
                        checkThu.add(0);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });
        }
    }


    private ArrayList<String> MangNgay() {
        ArrayList<String> arrNgay = new ArrayList<String>();
        FormatDate formatDate = new FormatDate();

        int days = formatDate.truThoiGian("11/10/2021", "16/10/2021");

        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("16/10/2021");
        } catch (ParseException e) {

        }
        cal.setTime(date);

        if (days != 0) {
            arrNgay.add("16/10/2021".replaceAll("/", "-"));
            for (int i = 0; i < days; i++) {
                String ngay = CustomNgay(cal, -1).replaceAll("/", "-");
                arrNgay.add(ngay);
            }
        } else {
            String ngay = "11/10/2021".replaceAll("/", "-");
            arrNgay.add(ngay);
        }
        return arrNgay;
    }

    public String CustomNgay(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }
}