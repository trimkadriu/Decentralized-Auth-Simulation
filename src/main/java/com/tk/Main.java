package com.tk;

import com.tk.controller.NodeController;
import com.tk.domain.Node;
import com.tk.domain.enums.CLIOptions;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.service.NodeService;
import com.tk.service.factory.NodeControllerFactory;
import com.tk.service.util.CommonUtils;
import org.apache.commons.cli.CommandLine;

class Main {

    public static void main(String[] args) throws DecAuthSimException {
        CommandLine commandLine = CommonUtils.getCLIOptions(args);
        if(commandLine == null)
            throw new DecAuthSimException("No options are provided");

        String id = commandLine.getOptionValue(CLIOptions.ID.getOption());
        String role = commandLine.getOptionValue(CLIOptions.ROLE.getOption());
        String publicKey = commandLine.getOptionValue(CLIOptions.PUB_KEY.getOption());
        String privateKey = commandLine.getOptionValue(CLIOptions.PRIV_KEY.getOption());
        String reputation = commandLine.getOptionValue(CLIOptions.REPUTATION.getOption());

        NodeService nodeService = new NodeService();
        Node node = nodeService.initializeNode(id, role, publicKey, privateKey, reputation);

        NodeControllerFactory nodeControllerFactory = new NodeControllerFactory();
        NodeController nodeController = nodeControllerFactory.getNodeController(NodeRole.valueOf(role));
        nodeController.start(node);
    }
}
