package Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bf.bfchinesecharacterstudy.BFConstant;
import com.bf.bfchinesecharacterstudy.db.BFBookModelDao;
import com.bf.bfchinesecharacterstudy.db.DaoMaster;
import com.bf.bfchinesecharacterstudy.db.DaoSession;
import com.bf.bfchinesecharacterstudy.db.TestModelDao;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.List;

import Database.Model.BFBookModel;
import Database.Model.TestModel;

/**
 * Created by 1 on 2017/12/19.
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
    public boolean addBookEntity(BFBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {
            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
            Long result = daoSession.insert(bookModel);
            Log.d(BFConstant.BFTAG, "添加结果码:" + result);
            if (result > 0) {
                return true;
            }
        }
        return false;
    }
    public boolean deleteBookEntity(BFBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {
            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
            bookModelDao.delete(bookModel);
            return  true;
        }

        return false;
    }
    public boolean updateBookEntity(BFBookModel bookModel) {
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (bookModel != null) {
            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
            bookModelDao.update(bookModel);
            return  true;
        }

        return false;
    }
    public List<BFBookModel> queryBookEntryByBarcode(String barcode) {

        List<BFBookModel> bookModels = null;

        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }

        if (barcode != null && barcode.length() > 0) {
            BFBookModelDao bookModelDao = daoSession.getBFBookModelDao();
            bookModels = bookModelDao.queryBuilder()
                    .where(BFBookModelDao.Properties.Barcode.like(barcode)).build().list();

            return  bookModels;
        }

        return bookModels;
    }

}
