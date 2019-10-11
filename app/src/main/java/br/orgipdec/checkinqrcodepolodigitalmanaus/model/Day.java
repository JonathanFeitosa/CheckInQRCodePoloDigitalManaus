
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("trilha")
    @Expose
    private List<Trilha> trilha = null;
    private final static long serialVersionUID = 5139346259398503319L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trilha> getTrilha() {
        return trilha;
    }

    public void setTrilha(List<Trilha> trilha) {
        this.trilha = trilha;
    }

}
