package com.utc.graphemobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.utc.graphemobile.specific.SpecificInterface;

public class AndroidSpecific implements SpecificInterface{

	private Activity mContext;

	public AndroidSpecific(Activity context) {
		this.mContext = context;
	}
	
	@Override
	public void openFileChooser() {

		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
	    intent.setType("*/*"); 
	    intent.addCategory(Intent.CATEGORY_OPENABLE);

	    try {
	        mContext.startActivityForResult(
	                Intent.createChooser(intent, "Select a File to Upload"),
	                10);
	    } catch (android.content.ActivityNotFoundException ex) {
	        // Potentially direct the user to the Market with a Dialog
	        Toast.makeText(mContext, "Please install a File Manager.", 
	                Toast.LENGTH_SHORT).show();
	    }
	    
		
	}

}
