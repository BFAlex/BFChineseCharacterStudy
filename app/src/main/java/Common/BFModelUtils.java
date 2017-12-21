package Common;

import com.bf.bfchinesecharacterstudy.Model.BFBookModel;
import com.bf.bfchinesecharacterstudy.Model.BFLessonModel;

import java.util.ArrayList;
import java.util.List;

import Database.Model.BFDBBookModel;
import Database.Model.BFDBLessonModel;

/**
 * Created by 1 on 2017/12/21.
 */

public class BFModelUtils {

    public BFModelUtils() {}

    public static BFModelUtils newInstance() {
        BFModelUtils modelUtils = new BFModelUtils();

        return modelUtils;
    }

    /* Book */
    public BFDBBookModel bookModelTurnsIntoDBBookModel(BFBookModel bookModel) {
        if (bookModel == null) return null;
        BFDBBookModel dbBookModel = new BFDBBookModel(null, bookModel.bookSubject,
                                            bookModel.bookName, bookModel.bookBarcode);
        return dbBookModel;
    }
    public BFBookModel dbBookModelTurnsIntoBookModel(BFDBBookModel dbBookModel) {
        if (dbBookModel == null) return null;
        BFBookModel bookModel = new BFBookModel();
        if (bookModel != null) {
            bookModel.bookSubject = dbBookModel.getType();
            bookModel.bookName = dbBookModel.getName();
            bookModel.bookBarcode = dbBookModel.getBarcode();
        }
        return bookModel;
    }

    /* Lesson */
    public BFDBLessonModel lessonModelTurnsIntoDBLessonModel(BFLessonModel lessonModel, String bookBarcode) {
        if (lessonModel == null) return null;
        BFDBLessonModel dbLessonModel = new BFDBLessonModel(null, bookBarcode, lessonModel.id,
                                                            lessonModel.name, lessonModel.dataStr);

        return dbLessonModel;
    }
    public List<BFLessonModel> dbLessonModelTurnsIntoLessomModel(List<BFDBLessonModel> dbLessonModels) {

        if (dbLessonModels == null) return null;

        List<BFLessonModel> lessonModels = new ArrayList<>();
        for (BFDBLessonModel dbLessonModel : dbLessonModels) {
            BFLessonModel lessonModel = new BFLessonModel();
            if (lessonModel != null) {
                lessonModel.id = dbLessonModel.getLessonID();
                lessonModel.name = dbLessonModel.getLessonName();
                lessonModel.dataStr = dbLessonModel.getLessonWords();
            }
            lessonModels.add(lessonModel);
        }

        return lessonModels;
    }
}
