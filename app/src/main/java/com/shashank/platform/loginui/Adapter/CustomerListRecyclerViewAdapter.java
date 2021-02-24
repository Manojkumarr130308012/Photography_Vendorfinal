package com.shashank.platform.loginui.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.shashank.platform.loginui.Fragment.CustomerListFragment;
import com.shashank.platform.loginui.Model.CustomerData;
import com.shashank.platform.loginui.R;
import com.shashank.platform.loginui.font.FontFace;
import com.shashank.platform.loginui.font.OpenSansFontUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerListRecyclerViewAdapter extends RecyclerView.Adapter<CustomerListRecyclerViewAdapter.ViewHolder> implements Filterable {
    public static final String TAG = CustomerListRecyclerViewAdapter.class.getName();

    private List<CustomerData> _items;

    private List<CustomerData> _filteredItems;
    private final CustomerListFragment.OnListFragmentInteractionListener mListener;
    OpenSansFontUtils mFontClass;
    Context mContext;
    Typeface openSanRegularBold;

    public CustomerListRecyclerViewAdapter(List<CustomerData> items, CustomerListFragment.OnListFragmentInteractionListener listener, Context context) {
        _items = items;
        this._filteredItems = items;
        mListener = listener;
        mContext = context;
        mFontClass = new OpenSansFontUtils(mContext);
        openSanRegularBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
    }

    @Override
    public int getItemCount() {
        return _filteredItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customerlist_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = _filteredItems.get(position);
        mFontClass.setTextView(holder.mTxtCustomerName, FontFace.SEMIBOLD);
        mFontClass.setTextView(holder.mTxtCustomerNo, FontFace.REGULAR);
        mFontClass.setTextView(holder.mTxtCustomerPlace, FontFace.REGULAR);
        mFontClass.setTextView(holder.mTxtOutstandingamount, FontFace.SEMIBOLD_ITALIC);
        try {
            holder.mTxtCustomerName.setText(holder.mItem.getCustomername());
            holder.mTxtCustomerNo.setText(holder.mItem.getCustomerMobile());
            holder.mTxtCustomerPlace.setText(holder.mItem.getCustomerCityName());
            holder.mTxtOutstandingamount.setText("Wallet amount : " + holder.mItem.getCustomerOSAmount() + " Rs");
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            Character firstchar = holder.mItem.getCustomername().charAt(0);

            int color1 = mContext.getColor(R.color.primary_text_too_light);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().useFont(openSanRegularBold)
                    .endConfig()
                    .buildRound(firstchar.toString(), color1);

            holder.mImgNameCircle.setImageDrawable(drawable);


        } catch (Exception e) {
//            LogCrashlytics.log(e);
            e.printStackTrace();
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    _filteredItems = _items;
                } else {
                    List<CustomerData> filteredList = new ArrayList<>();
                    for (CustomerData row : _items) {
                        if (row.getCustomername().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCustomerMobile().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }
                    _filteredItems = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = _filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _filteredItems = (ArrayList<CustomerData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updateList(List<CustomerData> list) {
        _items = list;
        // _filteredItems = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTxtCustomerName;
        public final TextView mTxtCustomerNo;
        public final TextView mTxtCustomerPlace;
        public final TextView mTxtOutstandingamount;
        public final ImageView mImgNameCircle;
        public CustomerData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTxtCustomerName = view.findViewById(R.id.txt_customer_name);
            mTxtCustomerNo = view.findViewById(R.id.txt_customer_number);
            mTxtCustomerPlace = view.findViewById(R.id.txt_customer_place);
            mTxtOutstandingamount = view.findViewById(R.id.txt_customer_os_amount);
            mImgNameCircle = view.findViewById(R.id.img_letter_icon);
 
        }
    }
}
