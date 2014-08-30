package com.solstice.contactlist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.solstice.contactlist.adapter.ContactListAdapter;
import com.solstice.contactlist.model.entity.Contact;
import com.solstice.contactlist.task.ReceiveContactList;
import com.solstice.contactlist.task.TaskCompleted;

import java.util.List;

public class ContactList extends Activity implements TaskCompleted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        configureImageLoader();

        //Only being done when its create and not when i hit back
        new ReceiveContactList(ContactList.this, this).execute();
    }

    @Override
    public void onTaskContactFinalize(List<Contact> result) {
        ArrayAdapter<Contact> adapter = new ContactListAdapter(this, result);

        ListView contactList = (ListView) findViewById(R.id.contactList);

        contactList.setAdapter(adapter);
    }

    public void configureImageLoader(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(config);
    }
}