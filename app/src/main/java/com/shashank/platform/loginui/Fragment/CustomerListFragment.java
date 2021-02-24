package com.shashank.platform.loginui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.platform.loginui.Adapter.CustomGalleryAdapter;
import com.shashank.platform.loginui.Adapter.CustomerListRecyclerViewAdapter;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.Config.DBHelper;
import com.shashank.platform.loginui.Model.CustomerData;
import com.shashank.platform.loginui.Model.YoutubeVideo;
import com.shashank.platform.loginui.R;
import com.shashank.platform.loginui.Util.AsyncTaskNetCheck;
import com.shashank.platform.loginui.Util.INetworkCallBack;
import com.wang.avi.AVLoadingIndicatorView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class CustomerListFragment extends Fragment implements INetworkCallBack {
    DBHelper dbHelper;
    String id,na,pa;
    private OnListFragmentInteractionListener mListener;
    List<CustomerData> mCustomerList = new ArrayList<>();
    CustomerListRecyclerViewAdapter mRcvAdapter;
    //private ProgressDialog pDialog = null;
    private AVLoadingIndicatorView pNewDialog = null;
    Context mContext;
//    APIInterface apiInterface;
    SharedPreferences mSharedPreference;
    RelativeLayout mLytNoNetwork;
    LinearLayout mLytDetails;
    Button mTryAgain;
    TextView mTxtNoNetText;
    RecyclerView mRcyCustomerList;
    RelativeLayout rootLayout;
    SearchView searchView;
    Api api;
    public CustomerListFragment() {
    }


    public static final String TAG = CustomerListFragment.class.getName();

    public static Fragment createInstance(Context context) {
        return Fragment.instantiate(context, CustomerListFragment.class.getName(), null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
//        apiInterface = APIClient.getClient().create(APIInterface.class);
        mRcvAdapter = new CustomerListRecyclerViewAdapter(mCustomerList, mListener, mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        mLytNoNetwork = view.findViewById(R.id.lyt_network_fail);
        mLytDetails = view.findViewById(R.id.lyt_details);

        dbHelper=new DBHelper(getActivity());
        Cursor res = dbHelper.getAllData();
api=new Api();
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        mTryAgain = view.findViewById(R.id.btn_try_again);
        mTxtNoNetText = view.findViewById(R.id.txt_display);
        mRcyCustomerList = view.findViewById(R.id.list);
        rootLayout = view.findViewById(R.id.root_layout);
        pNewDialog = view.findViewById(R.id.loading_progress);
        pNewDialog.setVisibility(View.GONE);
        mRcyCustomerList.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyCustomerList.setAdapter(mRcvAdapter);
        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetandLoadData();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkNetandLoadData();
    }

    public void checkNetandLoadData() {
        new AsyncTaskNetCheck(mContext, this, false).execute();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListPaymentInteraction(CustomerData item);

        void onListOrderInteraction(CustomerData item);
    }


    class ReadJSON1 extends AsyncTask<String, Integer, String> {
        ArrayList<YoutubeVideo> videoArrayList=new ArrayList<>();
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("enq");
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    CustomerData addobj = new CustomerData();
//                                addobj.setCustomerID(jsonObject.optString("ID"));
                    addobj.setCustomername(productObject.getString("enquiry_cname"));
                    addobj.setCustomerMobile(productObject.getString("enquiry_cmobile"));
                    addobj.setCustomerCityName(productObject.getString("enquiry_cemail"));
                    addobj.setCustomerOSAmount(productObject.getString("enquiry_caddress"));
                    mCustomerList.add(addobj);

                }

                if (getActivity()!=null) {
                    //set layout manager and adapter for "GridView"

                    SetCustomerListAdapter(mCustomerList);

                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }


        }
    }

    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void SetCustomerListAdapter(List<CustomerData> _ArrayList) {
        mRcvAdapter.updateList(_ArrayList);
        if(searchView != null){
            if(!searchView.getQuery().toString().trim().isEmpty())
                mRcvAdapter.getFilter().filter(searchView.getQuery().toString().trim());
        }

    }

    private void dismissDialog() {
        try {
            if (pNewDialog != null && pNewDialog.isShown()) {
                pNewDialog.hide();
                pNewDialog.setVisibility(View.GONE);
            }
            //pNewDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchViewMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchViewMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mRcvAdapter != null) {
                    mRcvAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

    }

    @Override
    public void NetWorkCallBackResultTryAgain(boolean result) {

    }

    @Override
    public void NetWorkCallBackResultDismiss(boolean result) {
        if (result) {
            mLytNoNetwork.setVisibility(View.GONE);
            mLytDetails.setVisibility(View.VISIBLE);
            new ReadJSON1().execute(Api.Customerlisturl+""+na);

        } else {
            mLytNoNetwork.setVisibility(View.VISIBLE);
            mLytDetails.setVisibility(View.GONE);
        }
    }

    public void showResponseFailSnackBar(View root, String snackTitle, final int clickactiontype) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT).setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickactiontype == 1) {
                    new ReadJSON1().execute(Api.Customerlisturl+""+na);
                }
            }
        });
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_VERTICAL);
    }

//    }
}
