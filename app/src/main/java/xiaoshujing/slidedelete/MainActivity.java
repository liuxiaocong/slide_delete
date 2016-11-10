package xiaoshujing.slidedelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mList;
    Adapter mAdapter;
    private SlideView mLastSlideViewWithStatusOn;
    private SlideView mFocusedItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(linearLayoutManager);
        mAdapter = new Adapter(this, mOnSlideViewListener);
        mList.setAdapter(mAdapter);
        mList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        View childViewUnder = mList.findChildViewUnder(x, y);
                        if (childViewUnder != null && childViewUnder instanceof SlideView) {
                            mFocusedItemView = (SlideView) childViewUnder;
                        }
                    }
                    default:
                        break;
                }

                if (mFocusedItemView != null) {
                    mFocusedItemView.onRequireTouchEvent(motionEvent);
                }

                return false;
            }
        });
        ArrayList<String> mDataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDataList.add("Title:" + i);
        }
        mAdapter.addData(mDataList, true);
    }

    private SlideView.OnSlideViewListener<String> mOnSlideViewListener = new SlideView.OnSlideViewListener<String>() {
        @Override
        public void onSlide(View view, int status) {
            if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
                mLastSlideViewWithStatusOn.shrink();
            }

            if (status == SLIDE_STATUS_ON) {
                mLastSlideViewWithStatusOn = (SlideView) view;
            }
        }

        @Override
        public void onClickItem(String item, int position) {

        }

        @Override
        public void onDeleteItem(String item, int position) {
            Toast.makeText(MainActivity.this, "Click:" + item, Toast.LENGTH_LONG).show();
        }
    };
}
