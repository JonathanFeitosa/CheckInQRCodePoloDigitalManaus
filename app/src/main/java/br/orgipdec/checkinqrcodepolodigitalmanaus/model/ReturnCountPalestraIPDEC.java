
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReturnCountPalestraIPDEC implements Serializable
{

    @SerializedName("contador")
    @Expose
    private int contador;
    @SerializedName("palestra")
    @Expose
    private int palestra;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getPalestra() {
        return palestra;
    }

    public void setPalestra(int palestra) {
        this.palestra = palestra;
    }

}
