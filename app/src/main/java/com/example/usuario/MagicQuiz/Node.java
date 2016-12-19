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

    public boolean existChild(int position) {
        return (position >= 0 && position < children.size());
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
