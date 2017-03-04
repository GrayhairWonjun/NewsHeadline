package myds.wjang.newsheadline.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import myds.wjang.newsheadline.items.Story;
import myds.wjang.newsheadline.items.StoryType;

/**
 * Created by wjang on 2017. 3. 3..
 */

public class StoryParser extends AsyncTask<String, Void, ArrayList<Story>> {

    private static final String TAG_LOG = "StoryParser";

    private static final String TAG_RESULTS      = "results";
    private static final String TAG_TITLE        = "title";
    private static final String TAG_STORY_URL    = "url";
    private static final String TAG_MULTIMEDIA   = "multimedia";
    private static final String TAG_IMAGE_URL    = "url";
    private static final String TAG_IMAGE_FORMAT = "format";
    private static final String TAG_MEDIA_TYPE   = "type";

    public static final String IMAGE_FORMAT_NORMAL = "Normal";
    public static final String IMAGE_WIDTH         = "width";
    public static final String IMAGE_HEIGHT        = "height";
    public static final String MEDIA_TYPE_IMAGE    = "image";

    Context context;
    ArrayList<Story> storyList;

    boolean isConnected = false;

    public StoryParser(Context context) {
        this.context = context;
        storyList = new ArrayList<>();
        execute();
    }

    public void execute() {
        ConnectivityManager manager;
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) isConnected = true;
    }

    @Override
    protected ArrayList<Story> doInBackground(String... params) {

        String uri = params[0];
        BufferedReader br = null;

        Log.i(TAG_LOG, "url: " + uri);

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String data;
            while((data = br.readLine()) != null) {
                sb.append(data+"\n");
            }

            Log.i(TAG_LOG, sb.toString());
            this.storyList = parse(sb.toString().trim());

        }catch (Exception e) {
            e.printStackTrace();
        }

        return this.storyList;
    }

    private ArrayList<Story> parse(String json) throws JSONException {

        JSONObject body = new JSONObject(json);
        JSONArray stories = body.getJSONArray(TAG_RESULTS);

        ArrayList<Story> storyList = new ArrayList<>();

        for(int i=0; i<stories.length(); i++) {

            JSONObject storyJson  = stories.getJSONObject(i);
            String     title      = storyJson.getString(TAG_TITLE);
            String     storyUrl   = storyJson.getString(TAG_STORY_URL);
            Story story = new Story(title, storyUrl);

            String     imageUrl   = null;
            int imgWidth = 0;
            int imgHeight = 0;

            JSONArray multimedias = storyJson.getJSONArray(TAG_MULTIMEDIA);
            for (int j=0; j<multimedias.length(); j++) {
                JSONObject multimedia = multimedias.getJSONObject(j);
                if ( MEDIA_TYPE_IMAGE.equals(multimedia.get(TAG_MEDIA_TYPE)) ) {
                    story.setImage(multimedia.getString(TAG_IMAGE_FORMAT), multimedia.getString(TAG_IMAGE_URL));
                    if ( multimedia.getString(TAG_IMAGE_FORMAT)!=null
                            && !multimedia.getString(TAG_IMAGE_FORMAT).equalsIgnoreCase("thumb") ) {
                        imgWidth = Integer.parseInt( multimedia.getString(IMAGE_WIDTH) );
                        imgHeight = Integer.parseInt( multimedia.getString(IMAGE_HEIGHT) );
                    }
                }
            }

            story.setImageWidth(imgWidth);
            story.setImageHeight(imgHeight);
            story.setType( getType(story) );

            storyList.add(story);
        }

        return storyList;

    }
    private StoryType getType(Story story) {
        if (story.getImage() == null || story.getImage().trim().equals("")) {
            return StoryType.Text;
        } else if (story.getImageWidth() < story.getImageHeight()) {
            return StoryType.Portrait;
        }
        return StoryType.Image;
    }
}
