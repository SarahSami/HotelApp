package hotels.app.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hotels.app.R;
import hotels.app.adapter.HotelsAdapter;
import hotels.app.api.ApiService;
import hotels.app.model.Hotel;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipe;
    private List<Hotel> items;
    private RecyclerView mRecyclerView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        apiService = new ApiService();

        loadData();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }


    private void loadData() {
        swipe.setRefreshing(true);
        new LoadHotelsAsyncTask().execute();
    }

    private class LoadHotelsAsyncTask extends AsyncTask<Void, Void, List<Hotel>> {


        public LoadHotelsAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Hotel> doInBackground(Void... params) {
            return apiService.getHotels();
        }

        @Override
        protected void onPostExecute(List<Hotel> result) {
            swipe.setRefreshing(false);

            if (result == null) {
                Log.d("error", "show error toast");
            } else {
                HotelsAdapter adapter = new HotelsAdapter(MainActivity.this, result);
                mRecyclerView.setAdapter(adapter);
            }
        }
    }

}
