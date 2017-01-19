package com.tk.service;

import com.tk.dao.node.SRNodeDao;
import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;

/**
 * SRNodeService
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class NodeService {
    SRNodeDao srNodeDao;

    public NodeService() {
        srNodeDao = new SRNodeDao();
    }

    public Node getNode(int id) {
        return srNodeDao.getById(id);
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
        Node nodeInDB = getNode(node.getId());
        if (nodeInDB != null) {
            srNodeDao.update(node);
        } else {
            srNodeDao.save(node);
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
}
