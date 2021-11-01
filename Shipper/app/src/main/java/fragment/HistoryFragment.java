package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shipper.ChiTietActivity;
import com.example.shipper.DonHang;
import com.example.shipper.LichSu;
import com.example.shipper.R;

import java.util.ArrayList;

import adapter.DonHangAdapter;
import adapter.LichSuAdapter;


public class HistoryFragment extends Fragment {
    private Context context;
    ListView listView;
    ArrayList<LichSu> arrayList;
    private LichSuAdapter adapter;
    private void AnhXa(){
        listView = listView.findViewById(R.id.lvlichsu);
    }
    public void setData(Context context){
        this.context=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        listView =(ListView) view.findViewById(R.id.lvlichsu);
        arrayList = new ArrayList<>();
        arrayList.add(new LichSu("53 Võ Văn Ngân, TP Thủ Đức", "129/41 Nguyễn Văn Công, Go Vap", "8:30","10:30","Thành công"));
        arrayList.add(new LichSu("58 Võ Văn Ngân, TP Thủ Đức", "129/41 Nguyễn Văn Công, Go Vap", "11:30","12:32","Thất bại"));
        LichSuAdapter adapter = new LichSuAdapter((Activity) view.getContext(), R.layout.activity_lichsu, arrayList);
        listView.setAdapter(adapter);


        return view;
    }
}