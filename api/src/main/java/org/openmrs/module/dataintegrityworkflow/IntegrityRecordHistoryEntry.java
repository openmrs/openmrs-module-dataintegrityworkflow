package org.openmrs.module.dataintegrityworkflow;

import org.openmrs.User;
import java.util.Date;

/**
 * @author: harsz89
 */

/**
 * Class manipulate integrity record history
 */
public class IntegrityRecordHistoryEntry implements Comparable<IntegrityRecordHistoryEntry> {
    private String action;
    private Date dateActionPerformed;
    private User changeBy;
    private int recordId;

    public int compareTo(IntegrityRecordHistoryEntry o) {
        return getDateActionPerformed().compareTo(o.getDateActionPerformed());
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateActionPerformed() {
        return dateActionPerformed;
    }

    public void setDateActionPerformed(Date dateActionPerformed) {
        this.dateActionPerformed = dateActionPerformed;
    }

    public User getChangeBy() {
        return changeBy;
    }

    public void setChangeBy(User changeBy) {
        this.changeBy = changeBy;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
