package Database.Model;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 1 on 2017/12/20.
 */

public class BFBookModel {
    @Id
    private Long id;
    private int type;
    private String name;
    private String barcode;

}
