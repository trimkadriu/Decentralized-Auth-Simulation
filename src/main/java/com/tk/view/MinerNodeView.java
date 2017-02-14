package com.tk.view;

import com.tk.domain.Node;
import com.tk.domain.enums.ConfigKeys;
import com.tk.service.util.Config;

/**
 * MinerView
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class MinerNodeView {

    public void showBanner(Node node) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          Miner NODE Started          ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("--> This node current Reputation: " + node.getReputation());
        System.out.println("--> Waiting for an Authentication Request");
    }

    public void printAuthenticationRequested() {
        System.out.println("--> Authentication is requested.");
        System.out.println("--> Verifying the transaction.");
    }

    public void printVerifyIntegrity(boolean integrity) {
        System.out.println(String.format("  --> Verifying Transaction => Integrity: %s",
                Boolean.toString(integrity).toUpperCase()));
    }

    public void printVerifyRequestReputation(boolean hasReputation, double reputation) {
        System.out.println(String.format("  --> Verifying Transaction => Required Reputation: %s",
                Boolean.toString(hasReputation).toUpperCase()));
        System.out.println(String.format("  --> Has [%.0f] reputation out of [%s] required",
                reputation, Config.readValue(ConfigKeys.MIN_REPUTATION)));
    }

    public void printVerifyReputationBlockchain(boolean reputation) {
        System.out.println(String.format("  --> Verifying Transaction => Reputation on Blockchain: %s",
                Boolean.toString(reputation).toUpperCase()));
    }

    public void printTrxAuthenticated() {
        System.out.println("--> Transaction is Authenticated");
    }

    public void printTrxNotAuthenticated() {
        System.out.println("--> Transaction is NOT Authenticated");
    }

    public void printWaitServiceProvided() {
        System.out.println("--> Waiting for the Service to be provided");
    }

    public void printServiceConfirmation() {
        System.out.println("--> Waiting for Service Provided & Service Received Confirmations");
    }

    public void printRewardAndPunish() {
        System.out.println("--> Deciding for Rewards or Punishments");
    }

    public void printProofOfWork(String pow) {
        System.out.println("--> Solving the Proof-of-Work (PoW)");
        System.out.println(String.format("  --> PoW: %s", pow));
        System.out.println("--> Transaction Added on Blockchain");
    }

    public void printUpdatedReputation(double reputation) {
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + reputation);
        System.out.println("--> Reputation is updated");
        System.out.println("\n\n\n");
    }
}
