package adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shipper.DonHang;
import com.example.shipper.LichSu;
import com.example.shipper.R;

import java.util.ArrayList;
import java.util.List;

public class LichSuAdapter extends ArrayAdapter<LichSu> {

    private Activity context;
    private int layout;
    private ArrayList<LichSu> arrayList;



    public void setData(List<LichSu> list){
        this.arrayList= (ArrayList<LichSu>) list;
        notifyDataSetChanged();
    }
    public LichSuAdapter(Activity context, int layout, ArrayList<LichSu> arrayList) {
        super(context,layout,arrayList);
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        view = context.getLayoutInflater().inflate(R.layout.activity_lichsu, parent, false);

        LichSu lichSu = arrayList.get(position);

        TextView textView1 = view.findViewById(R.id.tv_trangthails);
        TextView textView2 = view.findViewById(R.id.tv_diemnhan);
        TextView textView3 = view.findViewById(R.id.tv_diemgiao);
        TextView textView4 = view.findViewById(R.id.tv_thoigiannhan);
        TextView textView5 = view.findViewById(R.id.tv_thoigiangiao);

        textView1.setText(lichSu.getTrangThai()+"");
        textView2.setText(lichSu.getDiemNhan());
        textView3.setText(lichSu.getDiemGiao());
        textView4.setText(lichSu.getThoiGianNhan()+"");
        textView5.setText(lichSu.getThoiGianGiao()+"");
        return view;
    }
}
