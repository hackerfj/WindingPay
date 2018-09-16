package com.magicsoft.geekfj.windingpay.windrecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.magicsoft.geekfj.windingpay.R;

/**
 * Created by 风鼎科技 on 2018/4/24.
 */

public class WindingRecyclerView extends RelativeLayout {


    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout mStatusLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnRefreshOrLoadListener onRefreshOrLoadListener;
    private RecyclerView.Adapter mAdapter;
    private View mEmptyView;
    private View mErrorView;

    private View mBottomLoadView;

    private boolean mIsRefresh = false;

    private boolean mIsLoad = false;
    private LinearLayout.LayoutParams statusLayoutParams ;

    /**
     * 是否需要显示空状态页面
     */
    private boolean mIsShowEmptyView = true;
    /**
     * 竖直向下的滑动方向
     */
    private final int DIRECTION_DOWN = 1;
    /**
     * 检查是否一开始有设置禁止刷新
     */
    private boolean isLockRefresh = false;
    /**
     * 判断是否一开始有设置禁止加载
     */
    private boolean isLockLoad = false;

    public WindingRecyclerView(Context context) {
        super(context, null);
        mContext = context;
    }

    public WindingRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        initView();
        setListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //两种写法都可以，只是在findViewById那里会有所区别
        // View.inflate(mContext , R.layout.layout_pull_to_refresh , this);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pull_to_refresh, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mStatusLayout = (LinearLayout) view.findViewById(R.id.ll_status);
        mBottomLoadView = view.findViewById(R.id.bottom_load_view);
        mBottomLoadView.setVisibility(GONE);
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.default_empty_layout, null);
        mErrorView = LayoutInflater.from(mContext).inflate(R.layout.default_error_layout , null);
        //设置默认的LayouManger
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        statusLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 监听都写在这里
     */
    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshOrLoadListener.onRefresh();
                innerRefreshAndLoadListener.isRefreshing(true);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //如果reyclerview不能在继续往下滑动，表明到达底部了，那么就显示加载更多
                    if (mLayoutManager.canScrollVertically() &&
                            !recyclerView.canScrollVertically(DIRECTION_DOWN)
                            && !isRefreshEnable() && !isLockLoad) {
//                        mBottomLoadView.setVisibility(VISIBLE);
//                        onRefreshOrLoadListener.onLoadMore();
//                        innerRefreshAndLoadListener.isLoading(true);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if(layoutManager instanceof GridLayoutManager){
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof LinearLayoutManager){
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }
                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        recyclerView.scrollToPosition(lastPosition);
                        Log.i("SLL:","滑动到底了");
                        mBottomLoadView.setVisibility(VISIBLE);
                        onRefreshOrLoadListener.onLoadMore();
                        innerRefreshAndLoadListener.isLoading(true);
                    }

                }
            }
        });

        setInnerRefreshAndLoadListener(new innerRefreshAndLoadListener() {
            @Override
            public void isLoading(boolean isLoad) {
                mIsLoad = isLoad;
                //如果一开始没有设置禁止刷新，则可以改变状态
                if (!isLockRefresh) {
                    mIsRefresh = !isLoad;
                    mSwipeRefreshLayout.setEnabled(!isLoad);
                }
            }

            @Override
            public void isRefreshing(boolean isRefresh) {
                mIsRefresh = isRefresh;
                //如果一开始没有设置禁止加载，则可以改变状态
                if (!isLockLoad) {
                    mIsLoad = !isRefresh;
                }


            }
        });
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * 设置布局管理器
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            mLayoutManager = layoutManager;
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

    }

    /**
     * 禁止使用刷新功能（默认开启）
     * @param enable
     */
    public void setDoNotRefresh(boolean enable){
        this.isLockRefresh = enable;
        this.mIsRefresh = enable;
        mSwipeRefreshLayout.setEnabled(mIsRefresh);
    }

    /**
     * 禁止使用加载功能（默认开启）
     * @param enable
     */
    public void setDoNotLoad(boolean enable){
        this.isLockLoad = enable;
        this.mIsLoad = enable;
    }

    /**
     * 当前状态是否允许刷新
     *
     * @return
     */
    public boolean isRefreshEnable() {
        return mIsRefresh;
    }

    /**
     * 当前状态是否允许加载
     *
     * @return
     */
    public boolean isLoadEnable() {
        return mIsLoad;
    }

    /**
     * 是否需要显示空状态图，默认是显示
     *
     * @param isShowEmptyView
     */
    public void isShowEmptyView(boolean isShowEmptyView) {
        this.mIsShowEmptyView = isShowEmptyView;
    }

    /**
     * 显示状态错误页面
     */
    public void setErrorLayout(int layoutId) {
        mErrorView = LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void setErrorLayout(View view){
        if (view != null) {
            mErrorView = view;
        }
    }

    /**
     * 显示错误状态
     */
    public void showErrorStatus(){
        if (mEmptyView.getParent() == null && mErrorView.getParent() == null) {
            mStatusLayout.addView(mErrorView);
            if (mSwipeRefreshLayout != null){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
        mRecyclerView.setVisibility(GONE);
    }

    /**
     * 显示有数据的页面
     */
    public void showDataStatus() {
        if (mEmptyView.getParent() != null) {
            mStatusLayout.removeView(mEmptyView);
        }else if(mErrorView.getParent() != null){
            mStatusLayout.removeView(mErrorView);
        }


        mRecyclerView.setVisibility(VISIBLE);
    }

    /**
     * 显示空白状态页
     */
    public void showEmptyStatus() {
        if (mEmptyView.getParent() == null && mErrorView.getParent() == null) {
            mStatusLayout.addView(mEmptyView , statusLayoutParams);
        }
        mRecyclerView.setVisibility(GONE);
    }

    /**
     * 设置空白页
     * @param view
     */
    public void setEmptyLayout(View view){
        if (view != null) {
            mEmptyView = view;
        }
    }

    /**
     * 设置空白页
     * @param layoutId
     */
    public void setEmptyLayout(int layoutId) {
        mEmptyView = LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    /**
     * 当刷新完毕或加载完毕后调用此方法
     */
    public void updateDataComplete() {
            if (isRefreshEnable()) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (mAdapter.getItemCount() == 0 && mIsShowEmptyView) {
                    showEmptyStatus();
                } else {
                    showDataStatus();
                }
                innerRefreshAndLoadListener.isRefreshing(false);
            } else if (isLoadEnable()) {
                mBottomLoadView.setVisibility(GONE);
                innerRefreshAndLoadListener.isLoading(false);
            }
    }


    /**
     * 刷新和加载更多的监听
     *
     * @param onRefreshOrLoadListener
     */
    public void setOnRefreshOrLoadListener(OnRefreshOrLoadListener onRefreshOrLoadListener) {
        this.onRefreshOrLoadListener = onRefreshOrLoadListener;
    }

    public interface OnRefreshOrLoadListener {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载
         */
        void onLoadMore();
    }

    public interface OnScrollListener {

    }

    /**
     * 内部的刷新和加载的监听，用于动态变更两者之间的状态
     * 保证两个状态不会同时被触发
     * 如果产生了刷新，那么就禁用加载，反之亦然
     * 不暴露给外部
     */
    private interface innerRefreshAndLoadListener {
        /**
         * 传入的加载状态 会对 更新状态 取反
         *
         * @param isLoading
         */
        void isLoading(boolean isLoading);

        /**
         * 传入的 更新状态 会对 加载状态 取反
         *
         * @param isRefreshing
         */
        void isRefreshing(boolean isRefreshing);
    }

    private innerRefreshAndLoadListener innerRefreshAndLoadListener;

    private void setInnerRefreshAndLoadListener(WindingRecyclerView.innerRefreshAndLoadListener innerRefreshAndLoadListener) {
        this.innerRefreshAndLoadListener = innerRefreshAndLoadListener;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        mRecyclerView.addItemDecoration(itemDecoration);
    }
    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}