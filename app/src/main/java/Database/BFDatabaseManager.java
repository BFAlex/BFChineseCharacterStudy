package Database;

import android.content.Context;

import com.bf.bfchinesecharacterstudy.db.DaoMaster;
import com.bf.bfchinesecharacterstudy.db.DaoSession;

/**
 * Created by BFAlex on 2017/12/19.
 */

public class BFDatabaseManager {

    private static final String dbName = "BFChineseCharacter.db";

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public BFDatabaseManager() {}

    /*
    * 实例化数据库管理类
    * */
    public static BFDatabaseManager newInstance(Context context) {
        BFDatabaseManager databaseManager = new BFDatabaseManager();
        if (databaseManager != null) {
            databaseManager.initDatabase(context);
        }
        return databaseManager;
    }

    /* Private */
    /*
    * 初始化数据库
    * */
    private void initDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    /* 课本Table */
//    public boolean insertBookEntity(BFDBBookModel bookModel) {
//        if (daoSession == null) {
//            daoSession = daoMaster.newSession();
//        }
//
//        if (bookModel != null) {
//            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
//            Long result = daoSession.insert(bookModel);
//            Log.d(BFConstant.BFTAG, "添加结果码:" + result);
//            if (result > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public boolean deleteBookEntity(BFDBBookModel bookModel) {
//        if (daoSession == null) {
//            daoSession = daoMaster.newSession();
//        }
//
//        if (bookModel != null) {
//            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
//            bookModelDao.delete(bookModel);
//            return  true;
//        }
//
//        return false;
//    }
//    public boolean updateBookEntity(BFDBBookModel bookModel) {
//        if (daoSession == null) {
//            daoSession = daoMaster.newSession();
//        }
//
//        if (bookModel != null) {
//            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
//            bookModelDao.update(bookModel);
//            return  true;
//        }
//
//        return false;
//    }
//    public List<BFDBBookModel> queryBookEntityByBarcode(String barcode) {
//
//        List<BFDBBookModel> bookModels = null;
//
//        if (daoSession == null) {
//            daoSession = daoMaster.newSession();
//        }
//
//        if (barcode != null && barcode.length() > 0) {
//            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
//            bookModels = bookModelDao.queryBuilder()
//                    .where(BFBookModelDao.Properties.Barcode.like(barcode)).build().list();
//
//            return  bookModels;
//        }
//
//        return bookModels;
//    }
//
//    /* 文章Table */
//    public void insertLessonEntity(BFDBLessonModel lessonModel) {
//        if (lessonModel == null) { return; }
//        BFLessonModelDao lessonModelDao = daoSession.getBFLessonModelDao();
//        lessonModelDao.insert(lessonModel);
//    }
//    public void deleteLessonEntity(BFDBLessonModel lessonModel) {
//        if (lessonModel == null) { return; }
//        BFLessonModelDao lessonModelDao = daoSession.getBFLessonModelDao();
//        lessonModelDao.delete(lessonModel);
//    }
//    public void updateLessonEntity(BFDBLessonModel lessonModel) {
//        if (lessonModel == null) { return; }
//        BFLessonModelDao lessonModelDao = daoSession.getBFLessonModelDao();
//        lessonModelDao.update(lessonModel);
//    }
//    public List<BFDBLessonModel> queryLessonEntityByBarcode(String barcode) {
//
//        if (barcode == null || barcode.length() <= 0) { return null; }
//
//        List<BFDBLessonModel> lessonModels = null;
//        BFLessonModelDao lessonModelDao = daoSession.getBFLessonModelDao();
//        WhereCondition whereCondition = BFLessonModelDao.Properties.Barcode.like(barcode);
//        lessonModels = lessonModelDao.queryBuilder().where(whereCondition).build().list();
//
//        return lessonModels;
//    }

}
