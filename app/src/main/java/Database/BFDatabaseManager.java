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
    private boolean isIdExists(String lessonId, List<BFDBLessonModel> lessonModels) {
        for (BFDBLessonModel lessonModel : lessonModels) {
            if (lessonId.equals(lessonModel.getLessonID())) {
                lessonModels.remove(lessonModel);
                return true;
            }
        }
        return false;
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
            bookModel.setId((long) 1);
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

    /* 常用接口调用 */
    public void localSaveBookModel(BFBookModel bookModel) {
        if (bookModel == null) {return;}

        BFModelUtils modelUtils = BFModelUtils.newInstance();
        // 查询数据库相关key的实体
        List<BFDBBookModel> dbBookModels = queryBookEntityByBarcode(bookModel.bookBarcode);
        if (dbBookModels == null || dbBookModels.size() <= 0) {
            // 数据库中找不到相关数据
            Log.d(BFConstant.BFTAG, "数据库还没有该条形码的课本记录哦【"+bookModel.bookBarcode+"】");
            insertBookEntity(modelUtils.bookModelTurnsIntoDBBookModel(bookModel));
        } else {
            // 更新旧数据
            Log.d(BFConstant.BFTAG, "数据库已经有该条形码的课本记录了【"+bookModel.bookBarcode+"】");
            updateBookEntity(modelUtils.bookModelTurnsIntoDBBookModel(bookModel));
        }
        // 更新bookBarcode对应的课本内容
        localSaveLessonModels(bookModel.lessonModels, bookModel.bookBarcode);
    }
    public BFBookModel localGetBookModel(String bookBarcode) {
        List<BFDBBookModel> dbBookModels = queryBookEntityByBarcode(bookBarcode);
        if (dbBookModels != null && dbBookModels.size() > 0) {
            return BFModelUtils.newInstance().dbBookModelTurnsIntoBookModel(dbBookModels.get(0));
        }
        return null;
    }
    public void localSaveLessonModels(BFLessonModel[] lessonModelArr, String bookBarcode) {

        List<BFDBLessonModel> dbLessonModels = queryLessonEntityByBarcode(bookBarcode);
        for (BFLessonModel lessonModel : lessonModelArr) {
            Log.d(BFConstant.BFTAG, "当前LessonModels的成员数量:"+dbLessonModels.size());
            if (isIdExists(lessonModel.id, dbLessonModels)) {
                String descStr = String.format("数据库已经有条形码【%s】中id【%s】的课文了", bookBarcode, lessonModel.id);
                Log.d(BFConstant.BFTAG, descStr);
                updateLessonEntity(BFModelUtils.newInstance().lessonModelTurnsIntoDBLessonModel(lessonModel, bookBarcode));
            } else {
                String descStr = String.format("数据库还没有条形码【%s】中id【%s】的课文哦", bookBarcode, lessonModel.id);
                Log.d(BFConstant.BFTAG, descStr);
                insertLessonEntity(BFModelUtils.newInstance().lessonModelTurnsIntoDBLessonModel(lessonModel, bookBarcode));
            }
        }
    }
    public List<BFLessonModel> localGetLessonModel(String bookBarcode) {
        List<BFDBLessonModel> dbLessonModels = queryLessonEntityByBarcode(bookBarcode);
        if (dbLessonModels != null && dbLessonModels.size() > 0) {
            return BFModelUtils.newInstance().dbLessonModelTurnsIntoLessomModel(dbLessonModels);
        }
        return null;
    }


}
