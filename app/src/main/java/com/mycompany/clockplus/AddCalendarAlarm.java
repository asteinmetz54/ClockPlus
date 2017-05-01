package com.mycompany.clockplus;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCalendarAlarm extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //ids
    private static final int CALENDAR_LOADER = 3;
    static final private int PICK_ACCOUNT = 2;

    //variable to store user picked account name
    private String accountName= "Press to Select Account";


    //adapter being used to display calendars data
    SimpleCursorAdapter mAdapter;
    //ListAdapter listAdapter;

    //list to hold first line and second line
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();


    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.VISIBLE                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_alarm);

        Map<String, String> datum = new HashMap<String, String>(2);
        datum.put("First Line", "Account");
        datum.put("Second Line", accountName);
        data.add(datum);

        Map<String, String> datum1 = new HashMap<String, String>(2);
        datum1.put("First Line", "Calendar");
        datum1.put("Second Line", "");
        data.add(datum1);

        Map<String, String> datum2 = new HashMap<String, String>(2);
        datum2.put("First Line", "Time Before");
        datum2.put("Second Line", "0");
        data.add(datum2);

        Map<String, String> datum3 = new HashMap<String, String>(2);
        datum3.put("First Line", "Tone");
        datum3.put("Second Line", "This Tone");
        data.add(datum3);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.calendar_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.setting_alarm, R.id.setting_alarm_detail});
        ListView listView = (ListView) findViewById(R.id.listview_calendar_alarm);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                                false, null, null, null, null);
                        startActivityForResult(pickAccountIntent, PICK_ACCOUNT);
                        break;
                    case 1:

                        break;


                    default:
                        break;
                }
            }
        });

        mAdapter = new SimpleCursorAdapter(
                getApplicationContext(),       //context
                R.layout.list_view_item_layout, //layout
                null,   //cursor null because it will be swapped in from loader
                new String[] {CalendarContract.Calendars.CALENDAR_DISPLAY_NAME},    //from column name representing the data to bind in the ui
                new int[] {R.id.text_view_calendar_name},   //the views that should display the column in the from parameter
                0);     //flags

        getSupportLoaderManager().initLoader(0, null, this);
    }
       // Resources res = getResources();
      //  String[] alarmOptions =res.getStringArray(R.array.calendar_alarm_options);
     /*   mAdapter = new SimpleCursorAdapter(
                this,       //context
                R.layout.list_view_item_layout, //layout
                null,   //cursor null because it will be swapped in from loader
                new String[] {CalendarContract.Calendars.CALENDAR_DISPLAY_NAME},    //from column name representing the data to bind in the ui
                new int[] {R.id.text_view_calendar_name},   //the views that should display the column in the from parameter
                0);     //flags
        ListView listView = (ListView)findViewById(R.id.listview_calendar);
        listView.setAdapter(mAdapter);
        *//*
         * Initializes the CursorLoader. The URL_LOADER value is eventually passed
         * to onCreateLoader().
         *//*
*/





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
     /*
     * Takes action based on the ID of the Loader that's being created
     */
        Uri baseUri;
        switch (id) {
            case CALENDAR_LOADER:
                baseUri = CalendarContract.Calendars.CONTENT_URI;
                String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                        + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                        + CalendarContract.Calendars.VISIBLE + " = ?))";
                String[] selectionArgs = new String[]{accountName, "com.google", "1"};
                // Returns a new CursorLoader
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        baseUri,        // Table to query
                        EVENT_PROJECTION,     // Projection to return
                        selection,            // No selection clause
                        selectionArgs,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        if (requestCode == PICK_ACCOUNT && resultCode == RESULT_OK) {
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            getSupportLoaderManager().restartLoader(CALENDAR_LOADER, null, this);
        }
    }

}


    /*//ask user to pick gmail account to get calendars
    Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
            false, null, null, null, null);
    startActivityForResult(pickAccountIntent, PICK_ACCOUNT);
*/


//    final CharSequence[] items = {"A", "B", "C"};
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//builder.setTitle("Pick Your Calendar");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//public void onClick(DialogInterface dialog, int item) {
//        // Do something with the selection
//        mDoneButton.setText(items[item]);
//        }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
