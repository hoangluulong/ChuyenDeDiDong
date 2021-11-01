package java.android.quanlybanhang.Model.DatBan;

import java.util.ArrayList;

public class id_datban {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<DatBanModel> getDatBanModels() {
        return datBanModels;
    }

    public void setDatBanModels(ArrayList<DatBanModel> datBanModels) {
        this.datBanModels = datBanModels;
    }

    String id;
    private ArrayList<DatBanModel> datBanModels;

    public id_datban(String id, ArrayList<DatBanModel> datBanModels) {
        this.id = id;
        this.datBanModels = datBanModels;
    }
}
