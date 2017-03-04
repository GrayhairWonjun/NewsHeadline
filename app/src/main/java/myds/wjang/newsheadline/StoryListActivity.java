package myds.wjang.newsheadline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import myds.wjang.newsheadline.adapter.StoryAdapter;
import myds.wjang.newsheadline.adapter.StoryParser;
import myds.wjang.newsheadline.items.Story;

public class StoryListActivity extends AppCompatActivity {

    private static final String NY_TIMES_API_URL = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=cf23f0334a174fff975fc2400ccbfdd9";

    private RecyclerView recyclerView;
    private StoryAdapter adapter;
    private ArrayList<Story> storyList;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_story_list);

        recyclerView = (RecyclerView) findViewById( R.id.rv );
        recyclerView.setHasFixedSize(true);
        if (savedInstanceState == null) {
            storyList = getStorys();
        } else {
            storyList = (ArrayList<Story>) savedInstanceState.getSerializable("stories");
        }
        adapter = new StoryAdapter( this, storyList );

        recyclerView.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) );
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter( adapter );

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("stories", this.storyList);
    }

    private ArrayList<Story> getStorys() {
        storyList = new ArrayList<>();
        StoryParser parser = new StoryParser( this );
        try {
            storyList = parser.execute(NY_TIMES_API_URL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return storyList;
    }

}
