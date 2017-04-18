package com.example.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.activity.MainActivity;
import com.example.popularmovies.datamodel.searchResult.SearchResultMovie;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.ui.MovieCoverAdapter;

public class RecyclerViewFragment extends Fragment {

    private static final String TAG = RecyclerViewFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected MovieCoverAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private  SearchResultMovie cacheSearchResultMovie;

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment myFragment = new RecyclerViewFragment();
        return myFragment;
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
                ((MainActivity) getActivity()).refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();
        mAdapter = new MovieCoverAdapter(getActivity(),(MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (cacheSearchResultMovie != null) {
            refreshAdapter(cacheSearchResultMovie);
        }
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.recycler_span_count));
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    public void setCacheSearchResultMovie(SearchResultMovie cacheSearchResultMovie) {
        this.cacheSearchResultMovie = cacheSearchResultMovie;
    }
    public void refreshAdapter(SearchResultMovie searchResultMovie){
        if (mAdapter != null) {
            mAdapter.setmSearchResultMovie(searchResultMovie);
            mAdapter.notifyDataSetChanged();
        }
    }
}