
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnCredenciado implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("participante")
    @Expose
    private String participante;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }
}
