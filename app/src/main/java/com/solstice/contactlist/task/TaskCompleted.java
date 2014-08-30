package com.solstice.contactlist.task;

import com.solstice.contactlist.model.entity.Contact;

import java.util.List;

/**
 * Created by arielrey on 29/08/14.
 */
public interface TaskCompleted {
    //This would be the callback when the async task is completed
    public void onTaskContactFinalize(List<Contact> result);
}
