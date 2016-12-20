package com.example.usuario.MagicQuiz;

import java.util.ArrayList;

public class Tree<T> {

    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>(rootData);
    }

    public Tree(T rootData, ArrayList<Node<T>> children) {
        root = new Node<T>(rootData, children);
    }

    public Tree(Node<T> node) {
        node.setParent(null);
        root = node;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

}