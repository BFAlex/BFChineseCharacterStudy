package Database.Model;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 1 on 2017/12/20.
 */

public class BFLessonModel {
    @Id
    private Long id;
    private String barcode;
    private String lessonID;
    private String lessonName;
    private String lessonWords;

}
