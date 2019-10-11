
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnAPIIPDEC implements Serializable
{

    @SerializedName("days")
    @Expose
    private List<Day> days = null;
    private final static long serialVersionUID = -4004374559765844994L;

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

}
