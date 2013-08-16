package terry.mydemoapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
// try again
public class RotateResizeImageActivity extends Activity{
    
    Context mContext = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_image);
        
        String sdpath = Environment.getExternalStorageDirectory() + "/"; //travel/greece/
        getFiles(sdpath);
        if(imagePath.size() < 1) {
            return;
        }
        Log.v("terry", "image number: "+imagePath.size());
        
        GridView gridView = (GridView) findViewById(R.id.gridView1);
        BaseAdapter adapter = new BaseAdapter() {
            
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if(convertView == null) {
                    imageView = new ImageView(RotateResizeImageActivity.this);
                    //set image width and height
                    imageView.setAdjustViewBounds(true);
                    imageView.setMaxWidth(150);
                    imageView.setMaxHeight(113);
                    //set imageView padding
                    imageView.setPadding(5, 5, 5, 5);
                    
                    Log.v("terry", "Adapter:getView if");
                    Log.v("terry", "position: "+position);
                }
                else {
                    imageView = (ImageView)convertView;
                    Log.v("terry", "Adapter:getView else");
                    Log.v("terry", "position: "+position);
                }
                //set image for imageView
                try {
                    Bitmap bm = BitmapFactory.decodeFile(imagePath.get(position));
                    Log.v("terry", "decode file: "+imagePath.get(position));
                    imageView.setImageBitmap(bm);
                }
                catch(OutOfMemoryError err) {
                    Log.v("terry", "OOM: position: "+position);
                }
                return imageView;
            }
            
            @Override
            public long getItemId(int position) {
                Log.v("terry", "getItemId");
                return position;
            }
            
            @Override
            public Object getItem(int position) {
                Log.v("terry", "getItem");
                return position;
            }
            
            @Override
            public int getCount() {
                Log.v("terry", "getCount");
                return imagePath.size();
            }
        };
        
        gridView.setAdapter(adapter);
    }
    
    private List<String> imagePath = new ArrayList<String>();
    private static String[] imageFormatSet = new String[] {"jpg", "png", "gif"};
    
    private static boolean isImageFile(String path) {
        for(String format: imageFormatSet) {
            if(path.contains(format)) {
                return true;
            }
        }
        return false;
    }
    
    private void getFiles(String url) {
        File files = new File(url);
        File[] file = files.listFiles();
        try {
            for(File f: file) {
                if(f.isDirectory()) {
                    getFiles(f.getAbsolutePath());
                }
                else {
                    if(isImageFile(f.getPath())) {
                        imagePath.add(f.getPath());
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

