package Database.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 1 on 2017/12/20.
 */

@Entity
public class BFLessonModel {
    @Id
    private Long id;
    private String barcode;
    private String lessonID;
    private String lessonName;
    private String lessonWords;
    @Generated(hash = 1620622977)
    public BFLessonModel(Long id, String barcode, String lessonID,
            String lessonName, String lessonWords) {
        this.id = id;
        this.barcode = barcode;
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonWords = lessonWords;
    }
    @Generated(hash = 1795771507)
    public BFLessonModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBarcode() {
        return this.barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getLessonID() {
        return this.lessonID;
    }
    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }
    public String getLessonName() {
        return this.lessonName;
    }
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    public String getLessonWords() {
        return this.lessonWords;
    }
    public void setLessonWords(String lessonWords) {
        this.lessonWords = lessonWords;
    }

}
