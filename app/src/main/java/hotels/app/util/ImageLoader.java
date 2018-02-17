package hotels.app.util;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hotels.app.api.ApiService;

/**
 * Created by Sarah on 2/17/18.
 */

public class ImageLoader {


    private ExecutorService mThreadPool;
    private final Map<ImageView, String> mImageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private int THREAD_POOL_SIZE = 2;
    private ApiService apiService;


    public ImageLoader() {
        apiService = new ApiService();
        mThreadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void loadDrawable(final String url, final ImageView imageView, Drawable placeholder) {
        mImageViews.put(imageView, url);
        imageView.setImageDrawable(placeholder);
        queueJob(url, imageView, placeholder);
    }


    private void queueJob(final String url, final ImageView imageView, final Drawable placeholder) {



        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String tag = mImageViews.get(imageView);
                if (tag != null && tag.equals(url)) {
                    if (imageView.isShown())
                        if (msg.obj != null) {
                            imageView.setImageDrawable((Drawable) msg.obj);
                        } else {
                            imageView.setImageDrawable(placeholder);
                        }
                }
            }
        };

        mThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                final Drawable bmp = downloadDrawable(url);
                if (imageView.isShown()) {
                    Message message = Message.obtain();
                    message.obj = bmp;
                    handler.sendMessage(message);
                }
            }
        });
    }


    private Drawable downloadDrawable(String url) {
        InputStream is = apiService.getInputStream(url);
        Drawable drawable = Drawable.createFromStream(is, url);
        return drawable;
    }


//TODO handler should be static class to avoid memory leaks but handleMessage() never called

//    private static class HandlerMessage extends Handler {
//        private ImageView imageView;
//        private String url;
//        private Drawable placeholder;
//        private Map<ImageView, String> imageViewStringMap;
//
//        public HandlerMessage(ImageView imageView, String url, Drawable drawable, Map<ImageView, String> imageViewStringMap) {
//            this.imageView = imageView;
//            this.url = url;
//            this.placeholder = drawable;
//            this.imageViewStringMap = imageViewStringMap;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            String tag = imageViewStringMap.get(imageView);
//            if (tag != null && tag.equals(url)) {
//                if (imageView.isShown())
//                    if (msg.obj != null) {
//                        imageView.setImageDrawable((Drawable) msg.obj);
//                    } else {
//                        imageView.setImageDrawable(placeholder);
//                    }
//            }
//        }
//
//    }
}
