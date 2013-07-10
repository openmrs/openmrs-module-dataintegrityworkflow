/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dataintegrityworkflow;

import org.openmrs.OpenmrsObject;
import org.openmrs.event.Event;
import org.openmrs.event.SubscribableEventListener;
import org.openmrs.module.dataintegrity.IntegrityCheck;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Arrays;
import java.util.List;

/**
 * @author: harsz89
 */

/**
 * Class for listening the integrity check changes and update the workflow record status accordingly
 */
public class IntegrityCheckListener implements SubscribableEventListener{

    public void onMessage(Message msgParam) {
        try {
            MapMessage mapMessage = (MapMessage) msgParam;
            if (Event.Action.CREATED.toString().equals(mapMessage.getString("action")))
            {
                System.out.println("CheckCreate");
            }
            else if (Event.Action.UPDATED.toString().equals(mapMessage.getString("action")))
            {
                System.out.println("CheckUpdated");
            }
            else if (Event.Action.PURGED.toString().equals(mapMessage.getString("action")))
            {
                System.out.println("CheckPurged");
            }
        }
        catch (JMSException e) {
            System.out.println("Ooops! some error occurred");
        }
    }

    public List<Class<? extends OpenmrsObject>> subscribeToObjects() {
        Object classes = Arrays.asList(IntegrityCheck.class);
        return (List<Class<? extends OpenmrsObject>>) classes;
    }

    public List<String> subscribeToActions() {
        return Arrays.asList(Event.Action.CREATED.name(), Event.Action.UPDATED.name(), Event.Action.VOIDED.name(), Event.Action.PURGED.name());
    }
}
