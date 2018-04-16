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
public class Stack<G> {

    private Node<G> header;
    private int size = 0;

    public Stack() {

    }

    public Stack(G data) {
        push(data);
    }

    public void push(G data) {
        Node<G> newNode = new Node(data);

        if (header == null) {
            header = newNode;
        } else {
            newNode.nextNode = header;
            header = newNode;
        }
        size++;
    }

    public G pop() {
        if (header == null) {
            System.out.println("Stack is empty !");
            return null;
        } else {
            Node<G> tempNode = header;
            header = header.nextNode;
            size--;

            tempNode.nextNode = null;
            return tempNode.data;
        }
    }
    
    public G peek() {
        if (header == null) {
            System.out.println("Stack is empty !");
            return null;
        } else {
            Node<G> tempNode = header;
            return tempNode.data;
        }
    }

    public void printStack() {
        Node<G> tempNode = header;

        while (tempNode != null) {
            System.out.print(tempNode.data + " -> ");
            tempNode = tempNode.nextNode;
        }

        System.out.println("null");
    }

    public int getSize() {
        return size;
    }
}
