package java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.function.DatBan.DatBan;
import java.android.quanlybanhang.function.ThanhToanActivity;
import java.android.quanlybanhang.function.MonOrder;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.OrderMenu;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRvHolderBan> {
    private OrderMenu orderMenu;
    public ArrayList<StaticBanModel> staticBanModels;
    ArrayList<StaticModelKhuVuc> items;
    boolean check = true;
    boolean select= true;
    private  ArrayList<ProuductPushFB1> listmon = new ArrayList<>();
    private  ArrayList<ProductPushFB> ListDate_yc = new ArrayList<>();
    String Id_khuvuc;
    private Dialog dialogban;
    Window window;
    boolean xacdinh = true;
    TextView datban;
    private DatabaseReference mDatabase;


        public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels,OrderMenu orderMenu,  ArrayList<StaticModelKhuVuc> items,String Id_khuvuc){
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;

    }
    public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels,OrderMenu orderMenu,  ArrayList<StaticModelKhuVuc> items,String Id_khuvuc,Window window,Dialog dialogban){
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;
        this.window= window;
        this.dialogban= dialogban;

    }
    public class StaticRvHolderBan extends RecyclerView.ViewHolder {

//

        public TextView tenPhucVu;
        public TextView tenBan;
        public TextView ngayGio;
        public TextView trangThai;
        ImageView bacham;
        LinearLayout cardview_ban;


        CardView constraintLayout;

        public StaticRvHolderBan(@NonNull View itemView) {
            super(itemView);
            tenPhucVu = itemView.findViewById(R.id.tvtenphucvu);
            tenBan = itemView.findViewById(R.id.tvtenban);
            ngayGio = itemView.findViewById(R.id.tvngaygio);
            trangThai = itemView.findViewById(R.id.tvtrangthai);
            bacham = itemView.findViewById(R.id.bacham);
//            view = itemView.findViewById(R.id.view2);
            cardview_ban = itemView.findViewById(R.id.cardview_ban);
            constraintLayout = itemView.findViewById(R.id.constraintLayouts);
        }
    }

    @NonNull
    @Override
    public StaticRvHolderBan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban,parent,false);

        StaticRvHolderBan staticRvHolderBan = new StaticRvHolderBan(view);

        return staticRvHolderBan;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvHolderBan holder, int position) {
        StaticBanModel CrrItem = staticBanModels.get(position);

        holder.tenPhucVu.setText(CrrItem.getTenNhanVien());
        holder.tenBan.setText(CrrItem.getTenban());
        holder.ngayGio.setText(changeDate(CrrItem.getGioDaOder()));
        holder.trangThai.setText(CrrItem.getTrangthai());

         if (staticBanModels.get(position).getTrangthai().equals("3")){
             holder.cardview_ban.setBackgroundResource(R.color.red);
            holder.constraintLayout.setEnabled(false);

        }
        if (staticBanModels.get(position).getTrangthai().equals("2")){
            holder.cardview_ban.setBackgroundResource(R.color.maudat);


        }

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(CrrItem);

            }
        });
        holder.bacham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HamTaodialog(Gravity.BOTTOM,CrrItem);
                Toast.makeText(orderMenu,"bacham",Toast.LENGTH_LONG).show();


//                FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("3");

            }
        });
    }
    //chuyeenr doii String sang ngay
    public String changeDate(String date){
            long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if(dates ==0){
            return "";
        }
        Date date1 =new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        return  aaa;

    }

    @Override
    public int getItemCount() {
        return staticBanModels.size() ;
    }

    public  void getData(StaticBanModel CrrItem ){
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID()+"_"+Id_khuvuc);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    Intent intent = new Intent(orderMenu, ThanhToanActivity.class);
                    intent.putExtra("id_ban",CrrItem.getID());
                    Log.d("id_khuvuc_Truong",Id_khuvuc);
                    intent.putExtra("id_khuvuc",Id_khuvuc);
                    orderMenu.startActivity(intent);
                    }
                else {
                    Intent intent = new Intent(orderMenu,MonOrder.class);
                    intent.putExtra("id_ban",CrrItem.getID());
                    Log.d("id_khuvuc_Truong1",Id_khuvuc);
                    intent.putExtra("id_khuvuc",Id_khuvuc);
                    orderMenu.startActivity(intent);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void HamTaodialog(int gravity,StaticBanModel CrrItem){

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        dialogban.setCancelable(true);
        datban=dialogban.findViewById(R.id.tvdatban);
        EvenlistDatban(datban,CrrItem);

        dialogban.show();
    }
    private void EvenlistDatban(TextView datban,StaticBanModel CrrItem){
            datban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(orderMenu,"datban",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(orderMenu, DatBan.class);
                    intent.putExtra("id_ban",CrrItem.getID());
                    intent.putExtra("tenban",CrrItem.getTenban());
                    intent.putExtra("id_khuvuc",Id_khuvuc);
                    orderMenu.startActivity(intent);
                }
            });

    }


}
