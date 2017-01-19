package com.tk.controller;

import com.tk.domain.Node;
import com.tk.service.NodeService;

/**
 * SRNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeController implements NodeController {

    public void start(Node node) {
        System.out.println("SR NODE Started");
    }
}
