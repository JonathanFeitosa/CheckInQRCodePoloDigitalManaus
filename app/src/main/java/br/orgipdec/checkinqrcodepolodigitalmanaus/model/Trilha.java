package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trilha implements Serializable
{

    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("chairman")
    @Expose
    private String chairman;
    @SerializedName("chairman-company")
    @Expose
    private String chairmanCompany;
    @SerializedName("talks")
    @Expose
    private List<Talk> talks = null;
    private final static long serialVersionUID = -7459205422371812032L;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getChairmanCompany() {
        return chairmanCompany;
    }

    public void setChairmanCompany(String chairmanCompany) {
        this.chairmanCompany = chairmanCompany;
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }

}