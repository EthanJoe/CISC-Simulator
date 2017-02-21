package Logic;

/**
 * Created by yichenzhou on 2/21/17.
 */
public class CacheLine {
    private String address;
    private String data;

    public CacheLine(String address, String data) {
        this.address = address;
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public String getData() {
        return data;
    }

}
