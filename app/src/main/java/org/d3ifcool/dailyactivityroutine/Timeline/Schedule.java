package org.d3ifcool.dailyactivityroutine.Timeline;


public class Schedule {
    private String scheduleName;
    private String startTime;
    private String finishTime;
    private String days;
    private String label;
    private String alarm;
    private String description;
    //private int icon;


    public Schedule(String scheduleName, String startTime, String finishTime, String days, String label, String alarm, String description) {
        this.scheduleName = scheduleName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.days = days;
        this.label = label;
        this.alarm = alarm;
        this.description = description;
    }

    public Schedule(String scheduleName, String startTime) {
        this.scheduleName = scheduleName;
        this.startTime = startTime;

    }

    public Schedule(){

    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public String getDays() {
        return days;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlarm() {
        return alarm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
