/*
 * NotesGUIAdapter.java
 * Saturday, July 16th 2011
 *
 * Created by Jose Miguel Salcido Aguilar (jose152)
 * The brief description of the class is on Javadoc format.
 */
package local.pruebas.adaptadores;

import local.pruebas.NotepadUtils;
import local.pruebas.R;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * NotesGUIAdapter:
 * Adapter for the GridView on the GUI
 * Based on the ImageAdapter from Google Android Developers GridView Tutorial.
 */
public class NotesGUIAdapter extends BaseAdapter {
	private Context mContexto;
	private Cursor mNotes;
	
	public NotesGUIAdapter(Context c, Cursor notes) {
		mContexto = c;
		mNotes = notes;
	}
	
	/**
	 * getCount:
	 * get the count of notes, basically the number of notes in the db
	 */
	public int getCount() {
		return mNotes.getCount();
	}
	
	/**
	 * getItem:
	 * ??
	 */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * getItemId:
	 * ??
	 */
	public long getItemId(int position) {
		mNotes.moveToPosition(position);
		return mNotes.getLong(mNotes.getColumnIndexOrThrow(NotepadUtils.KEY_ROWID_DATABASE));
	}

	/**
	 * getView:
	 * return the ImageView of the note drawable
	 *
	 * Added this way, if someone knows a better way change it.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout;
		if(convertView == null) {
			// JustMe saved the day at this point :)
			LayoutInflater layoutInflater = 
				(LayoutInflater) mContexto.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			layout = layoutInflater.inflate(R.layout.maingridlayout, null);
			
			// Check the orientation of the device
			int orientation = mContexto.getResources().getConfiguration().orientation;
	    	if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    		LinearLayout mainGridLinearLayout = (LinearLayout) layout.findViewById(R.id.mainGridLinearLayout);
	    		mainGridLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
	    	} else;
	    	
	    	// Set the text as the title
			TextView gridText = (TextView) layout.findViewById(R.id.gridTextView);
			String title = getCursorTitle(position);
			gridText.setText(title);
			// Dont change the image dinamically, it will be static content.
		} else {
			layout = convertView;
		}
		return layout;
	}
	
	private String getCursorTitle(int position) {
		mNotes.moveToPosition(position);
		return mNotes.getString(mNotes.getColumnIndexOrThrow(NotepadUtils.KEY_TITLE_DATABASE));
	}
}
