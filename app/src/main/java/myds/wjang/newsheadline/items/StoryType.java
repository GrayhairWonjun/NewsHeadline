package myds.wjang.newsheadline.items;

/**
 * Created by wjang on 2017. 3. 4..
 */

public enum StoryType {
    Text(0), Image(1), Portrait(2);

    private final int value;

    private StoryType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static StoryType convert(int num) {
        switch(num) {
            case 0:
                return Text;
            case 2:
                return Portrait;
            default:
                return Image;
        }
    }
}
