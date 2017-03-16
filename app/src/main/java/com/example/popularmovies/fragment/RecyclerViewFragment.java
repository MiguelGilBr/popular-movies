package com.example.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.activity.BaseActivity;
import com.example.popularmovies.activity.MainActivity;
import com.example.popularmovies.activity.MovieDetailActivity;
import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.ui.MovieCoverAdapter;

public class RecyclerViewFragment extends Fragment implements MovieCoverAdapter.IMovieCover{

    private static final String TAG = RecyclerViewFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected MovieCoverAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public RecyclerViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        rootView.setTag(TAG);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_movie_list);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
        }
        setRecyclerViewLayoutManager();

        mAdapter = new MovieCoverAdapter(DataModel.getInstance().getSearchResultMovie(),getActivity(),this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.recycler_span_count));
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void refreshAdapter(SearchResultMovie searchResultMovie){
        if (mAdapter != null) {
            mAdapter.setmSearchResultMovie(searchResultMovie);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.POSITION_KEY,position);
        ((BaseActivity)getActivity()).goToActivity(MovieDetailActivity.class, bundle);
    }
}