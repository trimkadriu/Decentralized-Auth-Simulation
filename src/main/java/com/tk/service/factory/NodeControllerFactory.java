package com.tk.service.factory;

import com.tk.controller.MinerNodeController;
import com.tk.controller.NodeController;
import com.tk.controller.SPNodeController;
import com.tk.controller.SRNodeController;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionType;

/**
 * TransactionFactory
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class NodeControllerFactory {
    public NodeController getNodeController(NodeRole nodeRole) {
        NodeController nodeController = null;
        if (nodeRole.equals(NodeRole.SR_NODE)) {
            nodeController = new SRNodeController();
        } else if (nodeRole.equals(TransactionType.SP_TRANSACTION)) {
            nodeController = new SPNodeController();
        } else if (nodeRole.equals(TransactionType.BLOCKCHAIN_TRANSACTION)) {
            nodeController = new MinerNodeController();
        }
        return nodeController;
    }
}
