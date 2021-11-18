package java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan;

import android.app.Dialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.DatBan.DanhSachDatBan;
import java.android.quanlybanhang.function.DatBan.DatBan;
import java.android.quanlybanhang.function.ThanhToanActivity;
import java.android.quanlybanhang.function.MonOrder;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.OrderMenu;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRvHolderBan> {
    private OrderMenu orderMenu;
    public ArrayList<StaticBanModel> staticBanModels;
    ArrayList<StaticModelKhuVuc> items;
    boolean check = true;
    boolean select = true;
    private ArrayList<ProuductPushFB1> listmon = new ArrayList<>();
    private ArrayList<ProductPushFB> ListDate_yc = new ArrayList<>();
    String Id_khuvuc;
    private Dialog dialogban;
    Window window;
    boolean xacdinh = true;
    TextView datban, listdatban, hoantac, gopban;
    String ids;
    String id_bk;
    String trangthaigop;
    String id_ban_thanhtoan;
    String id_khuvuc_thanhtoan;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase3;

    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";
    ArrayList<ProuductPushFB1> prouductPushFB1;
    ArrayList<ProductPushFB> productPushFBS;
    ArrayList<ProductPushFB> carsList3 = new ArrayList<>();
    ProductPushFB ProductTachBan;
    String id_ban_tachban;
    String id_khuvuc_tachban;
    ArrayList<ProuductPushFB1> carsListsaukhichon;
    String trangthai_tachBan;


    public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels, OrderMenu orderMenu, ArrayList<StaticModelKhuVuc> items, String Id_khuvuc) {
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;

    }

    public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels, OrderMenu orderMenu, ArrayList<StaticModelKhuVuc> items, String Id_khuvuc, Window window, Dialog dialogban, String trangthaigop, String id_ban_thanhtoan,
                           String id_khuvuc_thanhtoan, ArrayList<ProuductPushFB1> prouductPushFB1, ArrayList<ProductPushFB> productPushFBS, ProductPushFB ProductTachBan, ArrayList<ProuductPushFB1> carsListsaukhichon, String id_ban_tachban, String id_khuvuc_tachban
    ) {
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;
        this.window = window;
        this.dialogban = dialogban;
        this.trangthaigop = trangthaigop;
        this.id_ban_thanhtoan = id_ban_thanhtoan;
        this.id_khuvuc_thanhtoan = id_khuvuc_thanhtoan;
        this.prouductPushFB1 = prouductPushFB1;
        this.productPushFBS = productPushFBS;
        this.ProductTachBan = ProductTachBan;
        this.carsListsaukhichon = carsListsaukhichon;
        this.id_ban_tachban = id_ban_tachban;
        this.id_khuvuc_tachban = id_khuvuc_tachban;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban, parent, false);

        StaticRvHolderBan staticRvHolderBan = new StaticRvHolderBan(view);
        getDatasql();
        return staticRvHolderBan;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvHolderBan holder, int position) {
        StaticBanModel CrrItem = staticBanModels.get(position);
//        hamdatban(CrrItem);
        Log.d("list_as_string", id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan + "adapter");
        holder.tenPhucVu.setText(CrrItem.getTenNhanVien());
        holder.tenBan.setText(CrrItem.getTenban());
        holder.ngayGio.setText(changeDate(CrrItem.getGioDaOder()));
        holder.trangThai.setText(CrrItem.getTrangthai());

        if (staticBanModels.get(position).getTrangthai().equals("3")) {
            holder.cardview_ban.setBackgroundResource(R.color.red);
            holder.constraintLayout.setEnabled(false);

        }
        if (staticBanModels.get(position).getTrangthai().equals("2")) {
            holder.cardview_ban.setBackgroundResource(R.color.maudat);


        }
        if (staticBanModels.get(position).getTrangthai().equals("4")) {
            holder.cardview_ban.setBackgroundResource(R.color.bac);


        }
        if (trangthaigop.equals("1")) {
            if ((CrrItem.getID() + "_" + Id_khuvuc).equals(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan)) {
                holder.constraintLayout.setVisibility(View.GONE);
            }
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

                HamTaodialog(Gravity.BOTTOM, CrrItem);
                Toast.makeText(orderMenu, "bacham", Toast.LENGTH_LONG).show();

            }
        });
    }

    //chuyeenr doii String sang ngay
    public String changeDate(String date) {
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if (dates == 0) {
            return "";
        }
        Date date1 = new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        Log.d("simpleDateFormat", aaa + "");
        return aaa;

    }

    @Override
    public int getItemCount() {
        if (staticBanModels != null) {
            return staticBanModels.size();
        }
        return 0;

    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    public void getData(StaticBanModel CrrItem) {
        Log.d("trangthaigop", trangthaigop);
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (trangthaigop.equals("1")) {
                    new AlertDialog.Builder(orderMenu).setMessage(
                            "bạn có muốn gộp bàn không"
                    ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < prouductPushFB1.size(); i++) {
                                FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").push().setValue(prouductPushFB1.get(i));
                            }
                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("gopban").child("trangthai").setValue("0");
                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);

                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                            String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");

                        }
                    }).setNegativeButton("No", null)
                            .show();

                } else if (trangthaigop.equals("2")) {

                    new AlertDialog.Builder(orderMenu).setMessage(
                            "bạn có muốn Chuyển bàn không"
                    ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("2");
                            String Id = CrrItem.getID() + "_" + Id_khuvuc;
                            String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                            for (int i = 0; i < productPushFBS.size(); i++) {
                                FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).setValue(productPushFBS.get(i));
                                FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("gioDaOder").setValue(productPushFBS.get(i).getDate());
                            }
                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("gopban").child("trangthai").setValue("0");
                            database_order.QueryData("UPDATE " + TEN_BANG + " SET Id = '" + Id + "' WHERE Id= '" + id + "'");

                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);

                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();


                            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                            notifyDataSetChanged();

                        }
                    }).setNegativeButton("No", null)
                            .show();
                }
                if (trangthaigop.equals("3")) {
                    new AlertDialog.Builder(orderMenu).setMessage(
                            "bạn chắc chắn tách món vào bàn này không"
                    ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase3 = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID());
                            mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    trangthai_tachBan = snapshot.child("trangthai").getValue() + "";
                                    ProductTachBan.getDate();
                                    Log.d("ProductTachBandate", ProductTachBan.getDate()+"");
                                    if (trangthai_tachBan.equals("2")) {
                                        String ids = id_ban_tachban+ "_" +id_khuvuc_tachban;
                                        for (int i = 0; i < ProductTachBan.getSanpham().size(); i++) {
                                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").push().setValue(ProductTachBan.getSanpham().get(i));
                                        }
                                        if(carsListsaukhichon.size()==0){
                                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(ids).setValue(null);
                                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                        }
                                        else {
                                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(ids).child("sanpham").setValue(carsListsaukhichon);

                                        }
                                        FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("gopban").child("trangthai").setValue("0");

                                    }
                                      if(trangthai_tachBan.equals("1")){
                                        String ids = id_ban_tachban + "_" + id_khuvuc_tachban;
                                        FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).setValue(ProductTachBan);
                                        if(carsListsaukhichon.size()==0){
                                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(ids).setValue(null);
                                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                            FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                        }
                                        else {
                                            FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(ids).child("sanpham").setValue(carsListsaukhichon);

                                        }
                                        FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("2");
                                        FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("gioDaOder").setValue(ProductTachBan.getDate());
                                        FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("gopban").child("trangthai").setValue("0");
                                    }
                                      notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
//
                        }
                    }).setNegativeButton("No", null)
                            .show();

                } else {
                    if (snapshot.getValue() != null) {
                        Intent intent = new Intent(orderMenu, ThanhToanActivity.class);
                        intent.putExtra("id_ban", CrrItem.getID());
                        Log.d("id_khuvuc_Truong", Id_khuvuc);
                        intent.putExtra("id_khuvuc", Id_khuvuc);
                        orderMenu.startActivity(intent);
                    } else {
                        Intent intent = new Intent(orderMenu, MonOrder.class);
                        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("MangDi");
                        mDatabase.child("trangthai").setValue("0");
                        intent.putExtra("id_ban", CrrItem.getID());
                        Log.d("id_khuvuc_Truong1", Id_khuvuc);
                        intent.putExtra("id_khuvuc", Id_khuvuc);
                        orderMenu.startActivity(intent);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void HamTaodialog(int gravity, StaticBanModel CrrItem) {
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialogban.setCancelable(true);
        } else {
            dialogban.setCancelable(false);
        }
        datban = dialogban.findViewById(R.id.tvdatban);
        listdatban = dialogban.findViewById(R.id.listdatban);
        hoantac = dialogban.findViewById(R.id.hoantac);
        EvenlistDatban(datban, listdatban, hoantac, CrrItem);

        dialogban.show();

    }

    private void EvenlistDatban(TextView datban, TextView listdatban, TextView hoantac, StaticBanModel CrrItem) {
        datban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(orderMenu, "datban", Toast.LENGTH_LONG).show();
                dialogban.dismiss();
                Intent intent = new Intent(orderMenu, DatBan.class);
                intent.putExtra("id_ban", CrrItem.getID());
                intent.putExtra("tenban", CrrItem.getTenban());
                intent.putExtra("id_khuvuc", Id_khuvuc);
                orderMenu.startActivity(intent);

            }
        });
        listdatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
                Toast.makeText(orderMenu, "danh sach dat ban", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(orderMenu, DanhSachDatBan.class);
                intent.putExtra("id_ban", CrrItem.getID());
                intent.putExtra("tenban", CrrItem.getTenban());
                intent.putExtra("id_khuvuc", Id_khuvuc);
                orderMenu.startActivity(intent);

            }
        });

        if (CrrItem.getTrangthai().equals("4")) {
            hoantac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(orderMenu, "hoantac", Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("1");
                    dialogban.dismiss();

                }
            });
        } else {
            hoantac.setEnabled(true);
        }

    }

    private void getDatasql() {

        database_order = new Database_order(orderMenu, "app_database.sqlite", null, 2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
    }

}
