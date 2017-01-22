package com.tk.domain.transaction;

import java.util.Date;

/**
 * SPTransaction
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPTransaction extends Transaction {
    private String providedServiceResults;
    private String spPublicKey;
    private String resultSignedData;
    private Date resultTimeStamp;
    private double spReputation;

    public String getProvidedServiceResults() {
        return providedServiceResults;
    }

    public void setProvidedServiceResults(String providedServiceResults) {
        this.providedServiceResults = providedServiceResults;
    }

    public String getSpPublicKey() {
        return spPublicKey;
    }

    public void setSpPublicKey(String spPublicKey) {
        this.spPublicKey = spPublicKey;
    }

    public String getResultSignedData() {
        return resultSignedData;
    }

    public void setResultSignedData(String resultSignedData) {
        this.resultSignedData = resultSignedData;
    }

    public Date getResultTimeStamp() {
        return resultTimeStamp;
    }

    public void setResultTimeStamp(Date resultTimeStamp) {
        this.resultTimeStamp = resultTimeStamp;
    }

    public double getSpReputation() {
        return spReputation;
    }

    public void setSpReputation(double spReputation) {
        this.spReputation = spReputation;
    }

    @Override
    public String toString() {
        return "SPTransaction{" +
                "providedServiceResults='" + providedServiceResults + '\'' +
                ", spPublicKey='" + spPublicKey + '\'' +
                ", resultSignedData='" + resultSignedData + '\'' +
                ", resultTimeStamp=" + resultTimeStamp +
                ", spReputation=" + spReputation +
                '}';
    }
}
