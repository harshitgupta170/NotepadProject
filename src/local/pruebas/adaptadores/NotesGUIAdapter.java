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
	private Long[] mIds;
	private String[] mTitles;
	private int mCount;
	
	public NotesGUIAdapter(Context context, Cursor cursor) {
		mContexto = context;
		// PRUEBA
		Cursor notes = cursor;
		fillData(notes);
		notes = null;
	}
	
	/**
	 * fillData:
	 * fill all the data needed by the adapter
	 * WHY: reduce ram usage a-lot (at least on my phone)
	 * @param notes
	 */
	private void fillData(Cursor notes) {
		// Must be on first always
		notes.moveToFirst();
		mCount = notes.getCount();
		mIds = new Long[mCount];
		mTitles = new String[mCount];
		
		for (int i = 0; i < mCount; i++) {
			mIds[i] = notes.getLong(notes.getColumnIndexOrThrow(NotepadUtils.KEY_ROWID_DATABASE));
			mTitles[i] = notes.getString(notes.getColumnIndexOrThrow(NotepadUtils.KEY_TITLE_DATABASE));
			notes.moveToNext();
		}
	}
	
	/**
	 * getCount:
	 * get the count of notes, basically the number of notes in the db
	 */
	public int getCount() {
		return mCount;
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
	 * return the id of a position
	 */
	public long getItemId(int position) {
		return mIds[position];
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
			// Create the LayoutInflater
			LayoutInflater layoutInflater = 
				(LayoutInflater) mContexto.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			layout = layoutInflater.inflate(R.layout.maingridlayout, null);
			
			// Check the orientation of the device
			int orientation = mContexto.getResources().getConfiguration().orientation;
	    	if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    		LinearLayout mainGridLinearLayout = (LinearLayout) layout.findViewById(R.id.mainGridLinearLayout);
	    		mainGridLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
	    	} else; // Nothing.
	    	
	    	// Set the text as the title
			TextView gridText = (TextView) layout.findViewById(R.id.gridTextView);
			String title = getTitle(position);
			gridText.setText(title);
		} else {
			layout = convertView;
		}
		return layout;
	}
	
	/**
	 * getCursorTitle:
	 * get
	 * @param position
	 * @return
	 */
	private String getTitle(int position) {
		return mTitles[position];
	}
	
	/**
     * fixTitle:
     * Fixes the title in the mainGridTextView to get a cool and soft display
     * @param title
     * @return
     */
    private String fixTitle(String title, String body) {
    	// No of characters (add 3 '.' for the total No) to display at the mainGridTextView.
    	int length = NotepadUtils.MIN_CHARACTERS;
    	String empty = "";
    	if (title.equals(empty)) {
    		title = body;
    	}
    	// Title hack
		return (title.length() <= length) ? title : title.substring(0, length);
    }
}
