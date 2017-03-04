package myds.wjang.newsheadline.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static myds.wjang.newsheadline.adapter.StoryParser.IMAGE_FORMAT_NORMAL;

/**
 * Created by wjang on 2017. 3. 3..
 */

public class Story implements Serializable {

    private static final int TITLE_LENGTH = 50;

    private String title;
    private Map<String, String> imageUrls;
    private String storyUrl;

    private int imageWidth;
    private int imageHeight;
    private StoryType type;

    public Story(String title, String storyUrl) {
        this.title = title;
        this.storyUrl = storyUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTrimTitle() {
        return (title.length() > TITLE_LENGTH )
                ? title.substring(0,TITLE_LENGTH) + "..."
                : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public StoryType getType() {
        return type;
    }

    public void setType(StoryType type) {
        this.type = type;
    }
    public void setImage(String key, String url) {
        if (this.imageUrls == null)
            this.imageUrls = new HashMap<>();
        this.imageUrls.put(key, url);
    }

    public String getImage() {
        if (this.imageUrls == null || this.imageUrls.size()==0) {
            return null;
        }

        if (this.imageUrls.containsKey(IMAGE_FORMAT_NORMAL)) {
            return imageUrls.get(IMAGE_FORMAT_NORMAL);
        }

        return imageUrls.values().iterator().next();
    }
}
