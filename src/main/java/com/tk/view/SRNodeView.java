package com.tk.view;

import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;

import java.util.List;
import java.util.Scanner;

/**
 * SRView
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeView {
    private Scanner scanner;

    public SRNodeView() {
        scanner = new Scanner(System.in);
    }

    public void showBanner() {
        System.out.println("╔════════════════════════╗");
        System.out.println("║    Service Requester NODE Started    ║");
        System.out.println("╚════════════════════════╝");
    }

    public String askForSpNodePubKey(List<Node> spNodes) {
        System.out.println("Please select from which SP Node do you want to request for service: ");
        int count = 1;
        for(Node node : spNodes) {
            System.out.println("[" + count + "] - " + node.getPublicKey());
        }
        int choice = scanner.nextInt();
        while(choice <= 0 || choice > count) {
            System.out.println("Invalid choice. Please choose again");
            choice = scanner.nextInt();
        }
        return spNodes.get(choice - 1).getPublicKey();
    }

    public void printSendingServiceRequest() {
        System.out.println("--> Sending a service request transaction");
    }

    public void printWaitServiceReceive() {
        System.out.println("--> Service Request transaction sent");
        System.out.println("--> Waiting for the Service to be Received");
    }

    public void printTrxNotAuthenticated() {
        System.out.println("--> Transaction is NOT Authenticated");
    }

    public void printServiceIsReceived(String results) {
        System.out.println("--> Service is received");
        System.out.println("  --> SERVICE RESULTS:");
        System.out.println("     -----------------------");
        System.out.println("      " + results + "\n");
    }

    public void printServiceReceivedConfirm() {
        System.out.println("--> Sending Service Received Confirmation");
    }

    public void printWaitTrxOnBlockchain() {
        System.out.println("--> Waiting for the Transaction to be added on Blockchain");
    }

    public void printUpdatedReputation(double reputation) {
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + reputation);
        System.out.println("--> Reputation is updated");
        System.out.println("\n\n\n");
    }
}
