package com.tk.factory;

import com.tk.domain.*;

/**
 * NodeFactory
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class NodeFactory {
    public Node getNode(Role nodeRole) {
        if (nodeRole == null) {
            return null;
        }
        Node node = null;
        if (nodeRole.equals(Role.SR_NODE)) {
            node = new SRNode();
        }
        else if (nodeRole.equals(Role.SP_NODE)) {
            node = new SPNode();
        }
        else if (nodeRole.equals(Role.MINER_NODE)) {
            node = new MinerNode();
        }
        return node;
    }
}
