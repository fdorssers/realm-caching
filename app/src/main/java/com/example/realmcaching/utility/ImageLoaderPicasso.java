package com.example.realmcaching.utility;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.example.realmcaching.R;
import com.squareup.picasso.Picasso;

/**
 * Created by frank on 1-6-2016.
 */

public class ImageLoaderPicasso implements ImageLoader {
    private Context context;

    public ImageLoaderPicasso(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String source, ImageView imageView) {
        if (source != null && !source.trim().isEmpty()) {
            Picasso.with(context).load(source).fit().centerCrop().into(imageView);
        }
    }
}
