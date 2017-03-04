package myds.wjang.newsheadline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myds.wjang.newsheadline.R;
import myds.wjang.newsheadline.StoryActivity;
import myds.wjang.newsheadline.items.Story;
import myds.wjang.newsheadline.items.StoryType;

/**
 * Created by wjang on 2017. 3. 3..
 */

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG_LOG = "StoryAdapter";

    private Context context;
    private List<Story> storyList;

    public StoryAdapter (Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StoryType type = StoryType.convert(viewType);

        switch(type) {
            case Portrait:
                View portraitView = layoutInflater.inflate(R.layout.story_portrait_card, parent, false);
                viewHolder = new PortraitViewHolder(portraitView);
                break;
            case Text:
                View simpleView = layoutInflater.inflate(R.layout.story_text_card, parent, false);
                viewHolder = new TextViewHolder(simpleView);
                break;
            default:
                View view = layoutInflater.inflate(R.layout.story_image_card, parent, false);
                viewHolder = new ImageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        StoryType type = StoryType.convert(holder.getItemViewType());
        switch (type) {
            case Portrait:
                PortraitViewHolder portraitViewHolder = (PortraitViewHolder) holder;
                configurePortraitViewHolder(portraitViewHolder, position);
                break;
            case Text:
                TextViewHolder simpleViewHolder = (TextViewHolder) holder;
                configureTextViewHolder(simpleViewHolder, position);
                break;
            default:
                ImageViewHolder viewHolder = (ImageViewHolder) holder;
                configureImageViewHolder(viewHolder, position);
        }
    }

    private void configurePortraitViewHolder(PortraitViewHolder holder, int position) {
        final Story story = storyList.get(position);
        holder.tvTitle.setText(story.getTrimTitle());

        Glide.with(context).load(story.getImage()).into(holder.ivImage);

        holder.tvTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("story_url", story.getStoryUrl());
                intent.putExtra("title", story.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    private void configureTextViewHolder(TextViewHolder holder, int position) {
        final Story story = storyList.get(position);
        holder.tvTitle.setText(story.getTrimTitle());

        holder.tvTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("story_url", story.getStoryUrl());
                intent.putExtra("title", story.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void configureImageViewHolder(ImageViewHolder holder, int position) {
        final Story story = storyList.get(position);
        holder.tvTitle.setText(story.getTrimTitle());

        Glide.with(context).load(story.getImage()).into(holder.ivImage);

        holder.tvTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("story_url", story.getStoryUrl());
                intent.putExtra("title", story.getTitle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        storyList.get(position);
        if (storyList.get(position).getType() == StoryType.Text) {
            return StoryType.Text.ordinal();
        } else if (storyList.get(position).getType() == StoryType.Portrait) {
            return StoryType.Portrait.ordinal();
        } else {
            return StoryType.Image.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return this.storyList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
    public class TextViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TextViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
    public class PortraitViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        public PortraitViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
