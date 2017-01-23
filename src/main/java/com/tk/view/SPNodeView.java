package com.tk.view;

/**
 * SPView
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPNodeView {

    public void showBanner() {
        System.out.println("╔════════════════════════╗");
        System.out.println("║    Service Provider NODE Started     ║");
        System.out.println("╚════════════════════════╝");
        System.out.println("--> Waiting for a service request");
    }

    public void printServiceIsRequested() {
        System.out.println("--> A Service is Requested");
        System.out.println("--> Waiting for the transaction to be Authenticated");
    }

    public void printTrxNotAuthenticated() {
        System.out.println("--> Transaction is NOT Authenticated");
    }

    public void printProvidingResults(String results) {
        System.out.println("--> Transaction Authenticated");
        System.out.println("--> Providing the requested Service");
        System.out.println("--> Service is provided");
        System.out.println("  --> SERVICE RESULTS:");
        System.out.println("     -----------------------");
        System.out.println("      " + results + "\n");
        System.out.println("--> Sending Service Provided Confirmation");
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
