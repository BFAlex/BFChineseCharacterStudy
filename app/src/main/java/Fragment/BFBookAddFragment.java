package Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bf.bfchinesecharacterstudy.BFConstant;
import com.bf.bfchinesecharacterstudy.R;

/**
 * Created by 1 on 2017/12/16.
 */

public class BFBookAddFragment extends Fragment {

    public static final int RESULT_SUCCEED = 0;
    public static final int RESULT_FAIL = 1;

    private EditText code_edit;
    private Button finishBtn;
    private OnGetResult mOnGetResult;

    public static BFBookAddFragment newInstance() {
        return new BFBookAddFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_add_book, container, false);

        code_edit = (EditText)contentView.findViewById(R.id.addbook_edit);
        code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(BFConstant.BFTAG, "[addTextChangedListener beforeTextChanged]"
                        + "s:" + s + ", start:" + start
                        + ",count:" + count + ",afer:" + after);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(BFConstant.BFTAG, "[addTextChangedListener onTextChanged]"
                        + "s:" + s + ", start:" + start
                        + ",before:" + before + ",count:" + count);
                // 当文本长度大于0时候，按钮切换可操作状态
                boolean isEnable = !TextUtils.isEmpty(s);
                if (isEnable != finishBtn.isEnabled()) {
                    finishBtn.setEnabled(isEnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(BFConstant.BFTAG, "[addTextChangedListener afterTextChanged]" + "s:" + s);
                Log.d(BFConstant.BFTAG, "------------------");
            }
        });

        finishBtn = (Button)contentView.findViewById(R.id.addbook_finish_btn);
//        finishBtn.setEnabled(false);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(BFConstant.BFTAG, "点击了按钮:" + v.toString());
                // 把获取到的条码字符串交给代理
                String resultStr = code_edit.getText().toString();
                if (resultStr.isEmpty()) {
                    return;
                }
//                finishBtn.setEnabled(false);

                if (mOnGetResult != null) {
                    mOnGetResult.onGetResult(RESULT_SUCCEED, resultStr);
                }
            }
        });

        return contentView;
    }

    /* Public */

    public OnGetResult getOnGetResult() {
        return mOnGetResult;
    }

    public void setOnGetResult(OnGetResult onGetResult) {
        mOnGetResult = onGetResult;
    }

    /* Action */
    private void actionFinishBtn(Button btn) {
        Log.d(BFConstant.BFTAG, "点击了按钮:" + btn.toString());
    }

    /* 内部接口(类似于代理方法) */
    public interface OnGetResult {
        void onGetResult(int resultCode, String resultStr);
    }

}


