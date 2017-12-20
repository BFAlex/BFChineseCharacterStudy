package Database.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 1 on 2017/12/20.
 */

@Entity
public class BFBookModel {
    @Id
    private Long id;
    private int type;
    private String name;
    private String barcode;

    @Generated(hash = 1496587510)
    public BFBookModel(Long id, int type, String name, String barcode) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.barcode = barcode;
    }
    @Generated(hash = 1504554016)
    public BFBookModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBarcode() {
        return this.barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
