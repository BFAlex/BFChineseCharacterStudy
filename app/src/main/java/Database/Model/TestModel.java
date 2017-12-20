package Database.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 1 on 2017/12/20.
 */

@Entity
public class TestModel {
    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount;

    @Generated(hash = 1318823781)
    public TestModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1568142977)
    public TestModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
