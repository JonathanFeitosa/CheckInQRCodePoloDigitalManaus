
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sucesso implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("palestra")
    @Expose
    private Palestra palestra;
    @SerializedName("participante")
    @Expose
    private Participante participante;
    @SerializedName("horarioAcesso")
    @Expose
    private String horarioAcesso;
    @SerializedName("responsavel")
    @Expose
    private String responsavel;
    private final static long serialVersionUID = 6762706691473787866L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Palestra getPalestra() {
        return palestra;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getHorarioAcesso() {
        return horarioAcesso;
    }

    public void setHorarioAcesso(String horarioAcesso) {
        this.horarioAcesso = horarioAcesso;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

}
