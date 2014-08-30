package com.solstice.contactlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.solstice.contactlist.enumerator.PhoneType;
import com.solstice.contactlist.model.entity.Contact;
import com.solstice.contactlist.model.entity.ContactDetails;
import com.solstice.contactlist.util.Utilities;

public class ContactDetail extends Activity{

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        contact = (Contact) getIntent().getSerializableExtra("contact");

        populateDetails(contact.getDetails());
    }

    public void populateDetails(ContactDetails details) {
        ((TextView) findViewById(R.id.contactName)).setText(contact.getName());
        ((TextView) findViewById(R.id.contactCompanyName)).setText(contact.getCompany());

        if( Utilities.checkString(contact.getPhone().getHome()) && Utilities.checkString(contact.getPhone().getMobile()) && Utilities.checkString(contact.getPhone().getWork()) ){
            ((TextView) findViewById(R.id.contactPhone)).setText("No Available Phone");
            ((TextView) findViewById(R.id.contactPhoneType)).setVisibility(View.INVISIBLE);
        }else if( !Utilities.checkString(contact.getPhone().getHome()) ){
            ((TextView) findViewById(R.id.contactPhone)).setText(contact.getPhone().getHome());
            ((TextView) findViewById(R.id.contactPhoneType)).setText(PhoneType.Home.toString());
        }else if( !Utilities.checkString(contact.getPhone().getMobile()) ){
            ((TextView) findViewById(R.id.contactPhone)).setText(contact.getPhone().getMobile());
            ((TextView) findViewById(R.id.contactPhoneType)).setText(PhoneType.Mobile.toString());
        }else if( Utilities.checkString(contact.getPhone().getWork()) ){
            ((TextView) findViewById(R.id.contactPhone)).setText(contact.getPhone().getWork());
            ((TextView) findViewById(R.id.contactPhoneType)).setText(PhoneType.Work.toString());
        }

        ((TextView) findViewById(R.id.contactAddress)).setText(details.getAddress().getStreet());
        ((TextView) findViewById(R.id.contactCity)).setText(details.getAddress().getCity() + ", " + details.getAddress().getState());
        ((TextView) findViewById(R.id.contactBirthDate)).setText(contact.getBirthdate());
        ((TextView) findViewById(R.id.contactEmail)).setText(details.getEmail());

        ImageView img = (ImageView) findViewById(R.id.contactLargePhoto);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(details.getLargeImageURL(), img);

        //Set Image minimum size
        img.setMinimumWidth(100);
        img.setMinimumHeight(100);

        if( !details.getFavorite() ){
            ((ImageView) findViewById(R.id.contactFavorite)).setVisibility(View.INVISIBLE);
        }
    }
}