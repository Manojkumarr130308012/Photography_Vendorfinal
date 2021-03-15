package com.astr.hc.celebrations.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.astr.hc.celebrations.Fragment.CustomerListFragment;
import com.astr.hc.celebrations.Model.CustomerData;
import com.astr.hc.celebrations.R;


import java.util.ArrayList;
import java.util.List;

public class CustomerListRecyclerViewAdapter extends RecyclerView.Adapter<CustomerListRecyclerViewAdapter.ViewHolder> implements Filterable {
    public static final String TAG = CustomerListRecyclerViewAdapter.class.getName();

    private List<CustomerData> _items;

    private List<CustomerData> _filteredItems;
    private final CustomerListFragment.OnListFragmentInteractionListener mListener;

    Context mContext;
    Typeface openSanRegularBold;

    public CustomerListRecyclerViewAdapter(List<CustomerData> items, CustomerListFragment.OnListFragmentInteractionListener listener, Context context) {
        _items = items;
        this._filteredItems = items;
        mListener = listener;
        mContext = context;
//        openSanRegularBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
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

        try {
            holder.mTxtCustomerName.setText(holder.mItem.getCustomername());
            holder.mTxtCustomerNo.setText(holder.mItem.getCustomerMobile());
            holder.mTxtCustomerPlace.setText(holder.mItem.getCustomerCityName());
            holder.mTxtOutstandingamount.setText("Address : " + holder.mItem.getCustomerOSAmount());
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            Character firstchar = holder.mItem.getCustomername().charAt(0);

            int color1 = mContext.getColor(R.color.primary_text_too_light);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig().useFont(openSanRegularBold)
                    .endConfig()
                    .buildRound(firstchar.toString(), color1);

            holder.mImgNameCircle.setImageDrawable(drawable);


            holder.telephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number=holder.mItem.getCustomerMobile();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+number));
                   mContext.startActivity(intent);
                }
            });


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
        public final ImageView telephone;
        public CustomerData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTxtCustomerName = view.findViewById(R.id.txt_customer_name);
            mTxtCustomerNo = view.findViewById(R.id.txt_customer_number);
            mTxtCustomerPlace = view.findViewById(R.id.txt_customer_place);
            mTxtOutstandingamount = view.findViewById(R.id.txt_customer_os_amount);
            mImgNameCircle = view.findViewById(R.id.img_letter_icon);
            telephone = view.findViewById(R.id.telephone);

        }
    }
}
