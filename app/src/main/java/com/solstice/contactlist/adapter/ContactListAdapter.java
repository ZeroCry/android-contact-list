package com.solstice.contactlist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.solstice.contactlist.ContactDetail;
import com.solstice.contactlist.R;
import com.solstice.contactlist.enumerator.PhoneType;
import com.solstice.contactlist.model.entity.Contact;
import com.solstice.contactlist.util.Utilities;

import java.util.List;

/**
 * Created by arielrey on 29/08/14.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {
    private List<Contact> resultList;
    private Context context;

    public ContactListAdapter(Context context, List<Contact> contacts){
        super(context, R.layout.contact_item, contacts);

        this.context = context;
        this.resultList = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        if( itemView == null ){
            itemView = ((Activity) context).getLayoutInflater().inflate(R.layout.contact_item, parent, false);
        }

        Contact contact = resultList.get(position);

        ((TextView) itemView.findViewById(R.id.contactName)).setText(contact.getName());

        if( Utilities.checkString(contact.getPhone().getHome()) && Utilities.checkString(contact.getPhone().getMobile()) && Utilities.checkString(contact.getPhone().getWork()) ){
            ((TextView) itemView.findViewById(R.id.titlePhone)).setText("No Available Phone");
        }else if( !Utilities.checkString(contact.getPhone().getHome()) ){
            ((TextView) itemView.findViewById(R.id.titlePhone)).setText(contact.getPhone().getHome() + " (" + PhoneType.Home.toString() + ")");
        }else if( !Utilities.checkString(contact.getPhone().getMobile()) ){
            ((TextView) itemView.findViewById(R.id.titlePhone)).setText(contact.getPhone().getMobile() + " (" + PhoneType.Mobile.toString() + ")");
        }else if( Utilities.checkString(contact.getPhone().getWork()) ){
            ((TextView) itemView.findViewById(R.id.titlePhone)).setText(contact.getPhone().getWork() + " (" + PhoneType.Work.toString() + ")");
        }

        ImageView img = (ImageView) itemView.findViewById(R.id.contactPhoto);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(contact.getSmallImageURL(), img);

        //Set Image minimum size
        img.setMinimumWidth(60);
        img.setMinimumHeight(60);

        //Create click listener
        createClickListener(itemView, "contact", contact);

        return itemView;
    }

    public void createClickListener(View itemView, final String key, final Contact value){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetail.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(key, value);

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }
}