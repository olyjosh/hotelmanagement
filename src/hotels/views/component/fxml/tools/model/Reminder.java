/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools.model;

/**
 *
 * @author NOVA
 */
public class Reminder {
    
    private String id;
    private String name;
    private String startTime;
    private String priority;
    private String message;
    private String interval;
    private String recievers;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getRecievers() {
        return recievers;
    }

    public void setRecievers(String recievers) {
        this.recievers = recievers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reminder{" + "id=" + id + ", name=" + name + ", startTime=" + startTime + ", priority=" + priority + ", message=" + message + ", interval=" + interval + ", recievers=" + recievers + ", status=" + status + '}';
    }
    
    
}
