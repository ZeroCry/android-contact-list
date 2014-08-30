package com.solstice.contactlist.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.solstice.contactlist.factory.ContactFactory;
import com.solstice.contactlist.model.entity.Contact;
import com.solstice.contactlist.model.entity.ContactDetails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arielrey on 29/08/14.
 */
public class ReceiveContactList extends AsyncTask<Void, Integer, List<Contact>> {

    private Context context;
    private TaskCompleted callback;

    private String url = "https://solstice.applauncher.com/external/contacts.json";
    private RestTemplate restTemplate = new RestTemplate();

    private ProgressDialog progressDialog;

    private ContactFactory factory = new ContactFactory();

    public ReceiveContactList(Context context, TaskCompleted callback){
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading - Please Wait");
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected List<Contact> doInBackground(Void... params) {
        List<Contact> result = new ArrayList<Contact>();

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        try{
            String jsonResult =  restTemplate.getForObject(url, String.class);

            JSONArray jsonArray = new JSONArray(jsonResult);

            //Iterate all the contacts
            for( int i = 0; i < jsonArray.length(); i++ ){
                Contact contact;
                ContactDetails details;

                //Create the contact
                contact = factory.objectToContact(jsonArray.getJSONObject(i));

                //Get the details
                String detailsResult =  restTemplate.getForObject(contact.getDetailsURL(), String.class);

                //Create the details
                details = factory.objectToDetails(new JSONObject(detailsResult));

                contact.setDetails(details);

                result.add(contact);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Contact> result) {
        super.onPostExecute(result);

        callback.onTaskContactFinalize(result);

        progressDialog.dismiss();
    }
}