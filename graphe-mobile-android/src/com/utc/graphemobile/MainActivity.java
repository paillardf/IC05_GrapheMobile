package com.utc.graphemobile;

import java.io.File;
import java.net.URISyntaxException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    protected static final int FILE_RESULT_CODE = 10;
	private GrapheMobile graphe;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        AndroidSpecific spe = new AndroidSpecific(this);
        graphe =  new GrapheMobile(spe);
        initialize(graphe, cfg);
        
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch (requestCode) {
	        case 10:
	        if (resultCode == RESULT_OK) {
	            // Get the Uri of the selected file 
	            Uri uri = data.getData();
	            System.out.println("File Uri: " + uri.toString());
	            // Get the path
	            String path;
				try {
					path = getPath(this, uri);
					System.out.println("File Path: " + path);
					graphe.openGraphe(new File(path));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
	            
	            // Get the file instance
	            // File file = new File(path);
	            // Initiate the upload
	        }
	        break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
	public static String getPath(Context context, Uri uri) throws URISyntaxException {
	    if ("content".equalsIgnoreCase(uri.getScheme())) {
	        String[] projection = { "_data" };
	        Cursor cursor = null;

	        try {
	            cursor = context.getContentResolver().query(uri, projection, null, null, null);
	            int column_index = cursor.getColumnIndexOrThrow("_data");
	            if (cursor.moveToFirst()) {
	                return cursor.getString(column_index);
	            }
	        } catch (Exception e) {
	            // Eat it
	        }
	    }
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	} 
}