package Database;

import android.content.Context;
import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;
import com.bf.bfchinesecharacterstudy.Model.BFBookModel;
import com.bf.bfchinesecharacterstudy.Model.BFLessonModel;
import com.bf.bfchinesecharacterstudy.db.BFDBBookModelDao;
import com.bf.bfchinesecharacterstudy.db.BFDBLessonModelDao;
import com.bf.bfchinesecharacterstudy.db.DaoMaster;
import com.bf.bfchinesecharacterstudy.db.DaoSession;

import org.greenrobot.greendao.query.WhereCondition;

import java.security.PublicKey;
import java.util.List;

import Common.BFModelUtils;
import Database.Model.BFDBBookModel;
import Database.Model.BFDBLessonModel;

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
    public boolean insertBookEntity(BFDBBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {

            BFDBBookModelDao bookModelDao = daoSession.getBFDBBookModelDao();

            String msgStr = String.format("[id:%d, type:%s, name:%s, barcode:%s]",
                                                bookModel.getId(), bookModel.getType(),
                                                bookModel.getName(), bookModel.getBarcode());
            Log.d(BFConstant.BFTAG, "BFDatabaseManager -> 准备添加数据:"+msgStr);

            Long result = daoSession.insert(bookModel);
            Log.d(BFConstant.BFTAG, "BFDatabaseManager -> 添加结果码:" + result);
            if (result > 0) {
                return true;
            }
        }
        return false;
    }
    public boolean deleteBookEntity(BFDBBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {
            BFDBBookModelDao bookModelDao = daoSession.getBFDBBookModelDao();
            bookModelDao.delete(bookModel);
            return  true;
        }

        return false;
    }
    public boolean updateBookEntity(BFDBBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {
            BFDBBookModelDao bookModelDao = daoSession.getBFDBBookModelDao();
            bookModelDao.update(bookModel);
            return  true;
        }

        return false;
    }
    public List<BFDBBookModel> queryBookEntityByBarcode(String barcode) {

        List<BFDBBookModel> bookModels = null;

        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (barcode != null && barcode.length() > 0) {
            BFDBBookModelDao bookModelDao = daoSession.getBFDBBookModelDao();
            bookModels = bookModelDao.queryBuilder()
                    .where(BFDBBookModelDao.Properties.Barcode.like(barcode)).build().list();

            return  bookModels;
        }

        return bookModels;
    }

    /* 文章Table */
    public void insertLessonEntity(BFDBLessonModel lessonModel) {
        if (lessonModel == null) { return; }
        BFDBLessonModelDao lessonModelDao = daoSession.getBFDBLessonModelDao();
        lessonModelDao.insert(lessonModel);
    }
    public void deleteLessonEntity(BFDBLessonModel lessonModel) {
        if (lessonModel == null) { return; }
        BFDBLessonModelDao lessonModelDao = daoSession.getBFDBLessonModelDao();
        lessonModelDao.delete(lessonModel);
    }
    public void updateLessonEntity(BFDBLessonModel lessonModel) {
        if (lessonModel == null) { return; }
        BFDBLessonModelDao lessonModelDao = daoSession.getBFDBLessonModelDao();
        lessonModelDao.update(lessonModel);
    }
    public List<BFDBLessonModel> queryLessonEntityByBarcode(String barcode) {

        if (barcode == null || barcode.length() <= 0) { return null; }

        List<BFDBLessonModel> lessonModels = null;
        BFDBLessonModelDao lessonModelDao = daoSession.getBFDBLessonModelDao();
        WhereCondition whereCondition = BFDBLessonModelDao.Properties.BookBarcode.like(barcode);
        lessonModels = lessonModelDao.queryBuilder().where(whereCondition).build().list();

        return lessonModels;
    }

    /* Model的存储 */
    public void localSaveBookModel(BFBookModel bookModel) {
        BFModelUtils modelUtils = BFModelUtils.newInstance();
        boolean result = insertBookEntity(modelUtils.bookModelTurnsIntoDBBookModel(bookModel));
        if (result) {
            for (BFLessonModel lessonModel : bookModel.lessonModels) {
                insertLessonEntity(modelUtils.lessonModelTurnsIntoDBLessonModel(lessonModel, bookModel.bookBarcode));
            }
        }
    }
    public BFBookModel localGetBookModel(String bookBarcode) {
        List<BFDBBookModel> dbBookModels = queryBookEntityByBarcode(bookBarcode);
        if (dbBookModels != null && dbBookModels.size() > 0) {
            return BFModelUtils.newInstance().dbBookModelTurnsIntoBookModel(dbBookModels.get(0));
        }
        return null;
    }
    public void localSaveLessonModel(BFLessonModel lessonModel, String bookBarcode) {
        insertLessonEntity(BFModelUtils.newInstance().lessonModelTurnsIntoDBLessonModel(lessonModel, bookBarcode));
    }
    public List<BFLessonModel> localGetLessonModel(String bookBarcode) {
        List<BFDBLessonModel> dbLessonModels = queryLessonEntityByBarcode(bookBarcode);
        if (dbLessonModels != null && dbLessonModels.size() > 0) {
            return BFModelUtils.newInstance().dbLessonModelTurnsIntoLessomModel(dbLessonModels);
        }
        return null;
    }

}
