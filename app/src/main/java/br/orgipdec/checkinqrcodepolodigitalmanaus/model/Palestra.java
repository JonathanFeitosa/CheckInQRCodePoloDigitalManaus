
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Palestra implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sala")
    @Expose
    private Sala sala;
    @SerializedName("palestra")
    @Expose
    private String palestra;
    private final static long serialVersionUID = -8318552064809290679L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getPalestra() {
        return palestra;
    }

    public void setPalestra(String palestra) {
        this.palestra = palestra;
    }

}
