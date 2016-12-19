package com.example.usuario.MagicQuiz;

public class Tree<T> {

    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>(rootData);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public boolean existNode (int[] position) {
        Node<T> node = root;
        for(int i = 0; i < position.length; i++) {
            if(node.existChild(position[i])) node = node.getChild(position[i]);
            else return false;
        }
        return true;
    }

    //WARNING: ask only for a node that exist
    public Node<T> getNode(int[] position) {
        Node<T> node = root;
        for(int i = 0; i < position.length; i++) {
            node = node.getChild(position[i]);
        }
        return node;
    }

    public void createNode(int[] position, Node<T> n) {
        Node<T> node = root;
        for(int i = 0; i < position.length; i++) {
            while(!node.existChild(position[i])) node.addChild(n);
        }
    }
}