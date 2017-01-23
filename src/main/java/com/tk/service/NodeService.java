package com.tk.service;

import com.tk.dao.node.NodeDao;
import com.tk.dao.node.SRNodeDao;
import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.service.factory.NodeDaoFactory;

import java.util.List;

/**
 * SRNodeService
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class NodeService {
    NodeDao nodeDao;

    public NodeService(NodeRole role) {
        NodeDaoFactory nodeDaoFactory = new NodeDaoFactory();
        nodeDao = nodeDaoFactory.getNodeDao(role);
    }

    public Node getNode(int id) {
        return nodeDao.getById(id);
    }

    public boolean exists(int id) {
        if(getNode(id) == null) {
            return false;
        }
        return true;
    }

    public boolean exists(Node node) {
        return exists(node.getId());
    }

    public void saveOrUpdate(Node node) {
        if (exists(node)) {
            nodeDao.update(node);
        } else {
            nodeDao.save(node);
        }
    }

    public Node initializeNode(String id, String role, String publicKey, String privateKey, String reputation) {
        Node node;
        if(exists(Integer.parseInt(id))) {
            node = getNode(Integer.parseInt(id));
            if(reputation != null) {
                node.setReputation(Double.parseDouble(reputation));
            }
        }
        else {
            node = new Node();
            if(reputation != null) {
                node.setReputation(Double.parseDouble(reputation));
            } else {
                node.setReputation(0);
            }
        }
        node.setId(Integer.parseInt(id));
        node.setRole(NodeRole.valueOf(role));
        node.setPublicKey(publicKey);
        node.setPrivateKey(privateKey);

        saveOrUpdate(node);
        return node;
    }

    public List<Node> getNodesByRole(NodeRole role) {
        NodeDaoFactory nodeDaoFactory = new NodeDaoFactory();
        NodeDao nodeDao = nodeDaoFactory.getNodeDao(role);
        return nodeDao.getAll();
    }
}
