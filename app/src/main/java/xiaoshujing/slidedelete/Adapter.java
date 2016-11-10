package xiaoshujing.slidedelete;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuXiaocong on 11/10/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater mLayoutInflater;
    ArrayList<String> mData = new ArrayList<>();
    Context mContext;
    SlideView.OnSlideViewListener<String> mOnSlideViewListener;

    public Adapter(Context context, SlideView.OnSlideViewListener<String> onSlideViewListener) {
        mOnSlideViewListener = onSlideViewListener;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void addData(List<String> data, boolean needRefresh) {
        if (needRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_list_full, parent, false);
        SlideView slideView = (SlideView) view.findViewById(R.id.slideView);
        //SlideView slideView = new SlideView(mContext);
        View itemView = mLayoutInflater.inflate(R.layout.item_list, null);
        itemView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slideView.setContentView(itemView);
        slideView.setOnSlideListener(mOnSlideViewListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position);
        holder.bind(title, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mDelete;
        SlideView mSlideView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSlideView = (SlideView) itemView.findViewById(R.id.slideView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
            mDelete = (TextView) itemView.findViewById(R.id.delete);
        }

        public void bind(final String title, final int positon) {
            mDelete.setTag(title);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSlideViewListener != null) {
                        mOnSlideViewListener.onDeleteItem(title, positon);
                    }
                }
            });
        }
    }
}
