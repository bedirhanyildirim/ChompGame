/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

/**
 *
 * @author bedirhan
 */
public class Node<G> {
    G data;
    Node<G> nextNode;
    
    public Node(G variable) {
        this.data = variable;
        nextNode = null;
    }
}