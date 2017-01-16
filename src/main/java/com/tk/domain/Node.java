package com.tk.domain;

import com.tk.domain.enums.NodeRole;
import com.tk.domain.transaction.Transaction;

import java.util.List;

/**
 * Node
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class Node {
    private int id;
    private NodeRole role;
    private String publicKey;
    private String privateKey;
    private double reputation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NodeRole getRole() {
        return role;
    }

    public void setRole(NodeRole role) {
        this.role = role;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public double getReputation() {
        return reputation;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", role=" + role +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", reputation=" + reputation +
                '}';
    }
}
