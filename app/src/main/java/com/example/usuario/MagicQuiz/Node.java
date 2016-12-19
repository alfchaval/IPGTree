package com.example.usuario.MagicQuiz;

import java.util.ArrayList;

public class Node<T> {
    private T data;
    private Node<T> parent;
    private ArrayList<Node<T>> children;

    public Node(T data) {
        this.data = data;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public Node(T data, ArrayList<Node<T>> children) {
        this.data = data;
        this.parent = null;
        this.children = children;
    }

    public Node(T data, Node<T> parent, ArrayList<Node<T>> children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node<T>> children) {
        for (Node<T> node : children) node.setParent(this);
        this.children = children;
    }

    public void addChild(Node<T> node) {
        node.setParent(this);
        children.add(node);
    }

    //MEJORAR
    public boolean existChild (int[] position) {
        Node<T> node = new Node<T>(data, parent, children);
        for(int i = 0; i < position.length; i++) {
            if(node.existChild(position[i])) node = node.getChild(position[i]);
            else return false;
        }
        return true;
    }

    public boolean existChild(int position) {
        return (position >= 0 && position < children.size());
    }

    //MEJORAR
    //WARNING: ask only for a node that exist
    public Node<T> getChild(int[] position) {
        Node<T> node = new Node<T>(data, parent, children);
        for(int i = 0; i < position.length; i++) {
            node = node.getChild(position[i]);
        }
        return node;
    }

    //WARNING: ask only for a child that exist
    public Node<T> getChild(int position) {
        return children.get(position);
    }

    public int numberOfChildren() {
        return children.size();
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }
}
