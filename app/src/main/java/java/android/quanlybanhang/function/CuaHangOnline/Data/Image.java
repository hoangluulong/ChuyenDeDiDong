package java.android.quanlybanhang.function.CuaHangOnline.Data;

public class Image {
    private String key;
    private String imageUrl;

    public Image() {
    }

    public Image(String key, String imageUrl) {
        this.key = key;
        this.imageUrl = imageUrl;
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
