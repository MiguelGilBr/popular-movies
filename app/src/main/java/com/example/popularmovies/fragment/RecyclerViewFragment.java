package com.example.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.activity.BaseActivity;
import com.example.popularmovies.activity.MovieDetailActivity;
import com.example.popularmovies.datamodel.DataModel;
import com.example.popularmovies.popularmovies.R;
import com.example.popularmovies.ui.MovieCoverAdapter;

public class RecyclerViewFragment extends Fragment implements MovieCoverAdapter.IMovieCover{

    private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
        }
        setRecyclerViewLayoutManager();

        mAdapter = new MovieCoverAdapter(DataModel.getInstance().getSearchResult(),getActivity(),this);
        // Set MovieCoverAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)

        return rootView;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        //if (mRecyclerView.getLayoutManager() != null) {
        //    scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
        //            .findFirstCompletelyVisibleItemPosition();
        //}
        mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Save currently selected layout manager.
        //savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(int position) {
        ((BaseActivity)getActivity()).goToActivity(MovieDetailActivity.class);
    }
}