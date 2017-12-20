package Database;

import android.content.Context;
import android.util.Log;

import com.bf.bfchinesecharacterstudy.BFConstant;
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

        return false;
    }
    public boolean deleteBookEntity(BFBookModel bookModel) {

        return false;
    }


    /*--------------------------------------------------*/
    public void testtDatabase(Context context) {
        // 创建并获取数据库
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "BFChineseCharacter.db", null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
        // 获取目标Table的访问对象
        TestModelDao testModelDao = daoSession.getTestModelDao();
        // 给实体插入数据
        TestModel testModel = new TestModel(null, "test1");
        TestModel testModel2 = new TestModel(null, "test2");
        TestModel testModel3 = new TestModel(null, "test3");
        testModelDao.insert(testModel);
        testModelDao.insert(testModel2);
        testModelDao.insert(testModel3);
        // 查询数据
        List<TestModel> testModelList = (List<TestModel>)testModelDao.queryBuilder()
                .where(TestModelDao.Properties.Id.le(10)).build().list();
        for (TestModel model : testModelList) {
            String modelMsg = String.format("查询结果:【id:%d, name:%s】", model.getId(), model.getName());
            Log.d(BFConstant.BFTAG, modelMsg);
        }
        // 删除数据
        TestModel lastModel = testModelList.get(testModelList.size()-2);
        testModelDao.delete(lastModel);
    }
}
