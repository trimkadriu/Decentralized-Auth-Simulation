package com.tk.domain.transaction;

import java.util.Date;

/**
 * blockchainTransaction
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class BlockchainTransaction extends Transaction {
    private double minerReputation;
    private String minerPublicKey;
    private String blockchainSignedData;
    private Date blockchainTimeStamp;
    private double srReputationOnBlockchain;
    private double spReputationOnBlockchain;
    private double minerReputationOnBlockchain;
    private String proofOfWork;
    private boolean confirmationServiceSent;
    private boolean confirmationServiceReceived;

    public double getMinerReputation() {
        return minerReputation;
    }

    public void setMinerReputation(double minerReputation) {
        this.minerReputation = minerReputation;
    }

    public String getMinerPublicKey() {
        return minerPublicKey;
    }

    public void setMinerPublicKey(String minerPublicKey) {
        this.minerPublicKey = minerPublicKey;
    }

    public String getBlockchainSignedData() {
        return blockchainSignedData;
    }

    public void setBlockchainSignedData(String blockchainSignedData) {
        this.blockchainSignedData = blockchainSignedData;
    }

    public Date getBlockchainTimeStamp() {
        return blockchainTimeStamp;
    }

    public void setBlockchainTimeStamp(Date blockchainTimeStamp) {
        this.blockchainTimeStamp = blockchainTimeStamp;
    }

    public double getSrReputationOnBlockchain() {
        return srReputationOnBlockchain;
    }

    public void setSrReputationOnBlockchain(double srReputationOnBlockchain) {
        this.srReputationOnBlockchain = srReputationOnBlockchain;
    }

    public double getSpReputationOnBlockchain() {
        return spReputationOnBlockchain;
    }

    public void setSpReputationOnBlockchain(double spReputationOnBlockchain) {
        this.spReputationOnBlockchain = spReputationOnBlockchain;
    }

    public double getMinerReputationOnBlockchain() {
        return minerReputationOnBlockchain;
    }

    public void setMinerReputationOnBlockchain(double minerReputationOnBlockchain) {
        this.minerReputationOnBlockchain = minerReputationOnBlockchain;
    }

    public String getProofOfWork() {
        return proofOfWork;
    }

    public void setProofOfWork(String proofOfWork) {
        this.proofOfWork = proofOfWork;
    }

    public boolean isConfirmationServiceSent() {
        return confirmationServiceSent;
    }

    public void setConfirmationServiceSent(boolean confirmationServiceSent) {
        this.confirmationServiceSent = confirmationServiceSent;
    }

    public boolean isConfirmationServiceReceived() {
        return confirmationServiceReceived;
    }

    public void setConfirmationServiceReceived(boolean confirmationServiceReceived) {
        this.confirmationServiceReceived = confirmationServiceReceived;
    }

    @Override
    public String toString() {
        return "BlockchainTransaction{" +
                "minerReputation=" + minerReputation +
                ", minerPublicKey='" + minerPublicKey + '\'' +
                ", blockchainSignedData='" + blockchainSignedData + '\'' +
                ", blockchainTimeStamp=" + blockchainTimeStamp +
                ", srReputationOnBlockchain=" + srReputationOnBlockchain +
                ", spReputationOnBlockchain=" + spReputationOnBlockchain +
                ", minerReputationOnBlockchain=" + minerReputationOnBlockchain +
                ", proofOfWork='" + proofOfWork + '\'' +
                ", confirmationServiceSent=" + confirmationServiceSent +
                ", confirmationServiceReceived=" + confirmationServiceReceived +
                '}';
    }
}
