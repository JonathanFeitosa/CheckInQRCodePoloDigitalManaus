
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sala implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sala")
    @Expose
    private String sala;
    private final static long serialVersionUID = -5638221827515335631L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

}
