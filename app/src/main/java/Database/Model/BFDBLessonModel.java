package Database.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

/**
 * Created by 1 on 2017/12/20.
 */

@Entity
public class BFDBLessonModel {
    @Id
    private Long id;
    private String bookBarcode;
    private String lessonID;
    private String lessonName;
    private String lessonWords;

    @Generated(hash = 216973958)
    public BFDBLessonModel(Long id, String bookBarcode, String lessonID,
            String lessonName, String lessonWords) {
        this.id = id;
        this.bookBarcode = bookBarcode;
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonWords = lessonWords;
    }
    @Generated(hash = 1559236801)
    public BFDBLessonModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBookBarcode() {
        return this.bookBarcode;
    }
    public void setBookBarcode(String bookBarcode) {
        this.bookBarcode = bookBarcode;
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
