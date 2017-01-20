package com.tk.service.factory;

import com.tk.dao.node.MinerNodeDao;
import com.tk.dao.node.NodeDao;
import com.tk.dao.node.SPNodeDao;
import com.tk.dao.node.SRNodeDao;
import com.tk.domain.enums.NodeRole;

/**
 * Created by trimkadriu on 17-01-20.
 */
public class NodeDaoFactory {

    public NodeDao getNodeDao(NodeRole role) {
        NodeDao nodeDao = null;
        if (role.equals(NodeRole.SR_NODE)) {
            nodeDao = new SRNodeDao();
        } else if (role.equals(NodeRole.SP_NODE)) {
            nodeDao = new SPNodeDao();
        } else if (role.equals(NodeRole.MINER_NODE)) {
            nodeDao = new MinerNodeDao();
        }
        return nodeDao;
    }
}
