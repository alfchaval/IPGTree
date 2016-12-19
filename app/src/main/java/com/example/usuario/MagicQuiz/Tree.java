package com.example.usuario.MagicQuiz;

import java.util.ArrayList;

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
}