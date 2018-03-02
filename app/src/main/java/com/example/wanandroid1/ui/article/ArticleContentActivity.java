package com.example.wanandroid1.ui.article;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
import com.example.wanandroid1.constant.Constant;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;

import butterknife.BindView;

@Route(path = "/article/ArticleContentActivity")
public class ArticleContentActivity extends BaseActivity<ArticleContentPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    @Autowired
    public int id;
    @Autowired
    public String url;
    @Autowired
    public String title;
    @Autowired
    public String author;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_content;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mReceivedTitleCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mReceivedTitleCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            setToolbarTitle(title);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }


    public static void start(int id, String url, String title, String author) {
        ARouter.getInstance().build("/article/ArticleContentActivity")
                .withInt(Constant.CONTENT_ID_KEY, id)
                .withString(Constant.CONTENT_URL_KEY, url)
                .withString(Constant.CONTENT_TITLE_KEY, title)
                .withString(Constant.CONTENT_AUTHOR_KEY, author)
                .navigation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), title, url));
            intent.setType("text/plain");
            startActivity(intent);
            //收藏
        } else if (itemId == R.id.menuLike) {
            if (id == 0){
                mPresenter.collectOutsideArticle(title, author, url);
            }else {
                mPresenter.collectArticle(id);
            }
        } else {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);

    }
}
