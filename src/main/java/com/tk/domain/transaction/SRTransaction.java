package com.tk.domain.transaction;

import java.util.Date;

/**
 * SRTransaction
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRTransaction extends Transaction {
    private String requestedServiceName;
    private String srPublicKey;
    private String requestSignedData;
    private Date requestTimeStamp;
    private double srReputation;
    private String spPublicKey;

    public String getRequestedServiceName() {
        return requestedServiceName;
    }

    public void setRequestedServiceName(String requestedServiceName) {
        this.requestedServiceName = requestedServiceName;
    }

    public String getSrPublicKey() {
        return srPublicKey;
    }

    public void setSrPublicKey(String srPublicKey) {
        this.srPublicKey = srPublicKey;
    }

    public String getRequestSignedData() {
        return requestSignedData;
    }

    public void setRequestSignedData(String requestSignedData) {
        this.requestSignedData = requestSignedData;
    }

    public Date getRequestTimeStamp() {
        return requestTimeStamp;
    }

    public void setRequestTimeStamp(Date requestTimeStamp) {
        this.requestTimeStamp = requestTimeStamp;
    }

    public double getSrReputation() {
        return srReputation;
    }

    public void setSrReputation(double srReputation) {
        this.srReputation = srReputation;
    }

    public String getSpPublicKey() {
        return spPublicKey;
    }

    public void setSpPublicKey(String spPublicKey) {
        this.spPublicKey = spPublicKey;
    }

    @Override
    public String toString() {
        return "SRTransaction{" +
                "requestedServiceName='" + requestedServiceName + '\'' +
                ", srPublicKey='" + srPublicKey + '\'' +
                ", requestSignedData='" + requestSignedData + '\'' +
                ", requestTimeStamp=" + requestTimeStamp +
                ", spPublicKey=" + spPublicKey +
                ", srReputation=" + srReputation +
                '}';
    }
}
