
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnCredenciado implements Serializable
{

    @SerializedName("sucesso")
    @Expose
    private Sucesso sucesso;
    private final static long serialVersionUID = 3571315943549069057L;

    public Sucesso getSucesso() {
        return sucesso;
    }

    public void setSucesso(Sucesso sucesso) {
        this.sucesso = sucesso;
    }

}
