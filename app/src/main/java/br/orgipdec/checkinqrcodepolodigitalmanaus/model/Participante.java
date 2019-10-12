
package br.orgipdec.checkinqrcodepolodigitalmanaus.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participante implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("idSympla")
    @Expose
    private Integer idSympla;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("ticketNumQrCode")
    @Expose
    private String ticketNumQrCode;
    @SerializedName("cpf")
    @Expose
    private Object cpf;
    @SerializedName("rg")
    @Expose
    private Object rg;
    @SerializedName("deviceToken")
    @Expose
    private Object deviceToken;
    @SerializedName("deviceOs")
    @Expose
    private Object deviceOs;
    private final static long serialVersionUID = -159295427899509015L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getIdSympla() {
        return idSympla;
    }

    public void setIdSympla(Integer idSympla) {
        this.idSympla = idSympla;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketNumQrCode() {
        return ticketNumQrCode;
    }

    public void setTicketNumQrCode(String ticketNumQrCode) {
        this.ticketNumQrCode = ticketNumQrCode;
    }

    public Object getCpf() {
        return cpf;
    }

    public void setCpf(Object cpf) {
        this.cpf = cpf;
    }

    public Object getRg() {
        return rg;
    }

    public void setRg(Object rg) {
        this.rg = rg;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(Object deviceOs) {
        this.deviceOs = deviceOs;
    }

}
