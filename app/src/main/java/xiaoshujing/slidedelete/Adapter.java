package xiaoshujing.slidedelete;

import android.content.Context;
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
        SlideView slideView = new SlideView(mContext);
        View itemView = mLayoutInflater.inflate(R.layout.item_list, null);
        slideView.setContentView(itemView);
        slideView.setOnSlideListener(mOnSlideViewListener);
        return new ViewHolder(slideView);
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

        public ViewHolder(View itemView) {
            super(itemView);
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
