package com.tk.service.util;

import com.tk.domain.enums.CLIOptions;
import com.tk.domain.enums.ConfigKeys;
import org.apache.commons.cli.*;
import org.apache.commons.lang.SystemUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Utils
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class CommonUtils {
    public static CommandLine getCLIOptions(String[] args) {
        Options options = new Options();
        Option idOption = Option.builder(CLIOptions.ID.getOption())
                .hasArg(true).desc("Specify Node ID.").required().build();
        Option roleOption = Option.builder(CLIOptions.ROLE.getOption())
                .longOpt("role").hasArg(true).desc("Specify Node Role (SR_NODE, SP_NODE, MINER_NODE).").required().build();
        Option pubKeyOption = Option.builder(CLIOptions.PUB_KEY.getOption())
                .longOpt("pub-key").hasArg(true).desc("Specify Node Public Key.").required().build();
        Option privKeyOption = Option.builder(CLIOptions.PRIV_KEY.getOption())
                .longOpt("priv-key").hasArg(true).desc("Specify Node Private Key.").required().build();
        Option reputationOption = Option.builder(CLIOptions.REPUTATION.getOption())
                .longOpt("reputation").hasArg(true).desc("Specify Node Reputation.").build();
        options.addOption(idOption)
                .addOption(roleOption)
                .addOption(pubKeyOption)
                .addOption(privKeyOption)
                .addOption(reputationOption);

        CommandLine commandLine = null;
        try {
            CommandLineParser parser = new DefaultParser();
            commandLine = parser.parse(options, args);
        } catch (ParseException exception) {
            System.out.println("Error while running the application");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java auth-sim", options);
        }
        return commandLine;
    }

    public static void clearCMD() {
        String clearCommand = SystemUtils.IS_OS_WINDOWS ? "cls" : "clear";
        try {
            Runtime.getRuntime().exec(clearCommand);
        } catch (IOException exception) {
        }
    }

    public static int generateId() {
        long timeNow = new Date().getTime();
        String timeNowString = String.valueOf(timeNow);
        int id = Integer.parseInt(timeNowString.substring(3, timeNowString.length() - 1));
        return id;
    }

    public static void sync() throws InterruptedException {
        Thread.sleep(Integer.parseInt(Config.readValue(ConfigKeys.SYNC_TIMEOUT)));
    }
}
