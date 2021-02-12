package com.shashank.platform.loginui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.platform.loginui.Model.PersonItem;
import com.shashank.platform.loginui.R;

import java.util.List;

public class  SingleCheckAdapter extends RecyclerView.Adapter<SingleCheckAdapter.SingleCheckViewHolder> {

    private int mSelectedItem = -1;
    private List<PersonItem> mSingleCheckList;
    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;

    public SingleCheckAdapter(Context context, List<PersonItem> listItems) {
        mContext = context;
        mSingleCheckList = listItems;
    }

    @Override
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.item_single_check, viewGroup, false);
        return new SingleCheckViewHolder(view, this);
    }



    @Override
    public void onBindViewHolder(SingleCheckViewHolder viewHolder, final int position) {
        PersonItem item = mSingleCheckList.get(position);
        try {
            viewHolder.setDateToView(item, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSingleCheckList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void onItemHolderClick(SingleCheckViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
    }

    class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SingleCheckAdapter mAdapter;
        private RadioButton mRadio;
        private TextView mText;
        private TextView mText1;
        private TextView mText2;
        private TextView mText3;

        public SingleCheckViewHolder(View itemView, final SingleCheckAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;

            mText = (TextView) itemView.findViewById(R.id.text);
            mText1 = (TextView) itemView.findViewById(R.id.text1);
            mText2 = (TextView) itemView.findViewById(R.id.text2);
            mText3= (TextView) itemView.findViewById(R.id.text3);
            mRadio = (RadioButton) itemView.findViewById(R.id.radio);
            itemView.setOnClickListener(this);
            mRadio.setOnClickListener(this);
        }

        public void setDateToView(PersonItem item, int position) throws Exception {
            mRadio.setChecked(position == mSelectedItem);
            mText.setText(item.getPersonName());
            mText1.setText("Photos:"+item.getPhotos());
            mText2.setText("Videos:"+item.getVideos());
            mText3.setText("Amount:"+item.getAmount());
        }

        @Override
        public void onClick(View v) {
            mSelectedItem = getAdapterPosition();
            notifyItemRangeChanged(0, mSingleCheckList.size());
            mAdapter.onItemHolderClick(SingleCheckViewHolder.this);
        }
    }

}