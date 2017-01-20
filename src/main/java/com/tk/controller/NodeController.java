package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.exception.DecAuthSimException;

/**
 * NodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public interface NodeController {
    void start(Node node) throws DecAuthSimException, InterruptedException;
}
