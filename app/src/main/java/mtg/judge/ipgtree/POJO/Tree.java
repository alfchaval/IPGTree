package mtg.judge.ipgtree.POJO;

import java.io.Serializable;
import java.util.ArrayList;

public class Tree<T> implements Serializable {

    //region variables

    private T data;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;

    //endregion

    //region constructors

    public Tree(T data) {
        this.data = data;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Tree(T data, Tree<T> parent) {
        this.data = data;
        this.parent = parent;
        parent.addChild(this);
        this.children = new ArrayList<>();
    }

    public Tree(T data, ArrayList<Tree<T>> children) {
        this.data = data;
        this.parent = null;
        this.children = children;
    }

    public Tree(T data, Tree<T> parent, ArrayList<Tree<T>> children) {
        this.data = data;
        this.parent = parent;
        parent.addChild(this);
        this.children = children;
    }

    public Tree(Tree<T> tree) {
        this.data = tree.data;
        this.parent = tree.parent;
        this.children = new ArrayList<Tree<T>>();
        for (Tree<T> child: tree.children) {
            this.children.add(new Tree<T>(child,this));
        }
    }

    public Tree(Tree<T> tree, Tree<T> parent) {
        this.data = tree.data;
        this.parent = parent;
        this.children = new ArrayList<Tree<T>>();
        for (Tree<T> child: tree.children) {
            this.children.add(new Tree<T>(child,this));
        }
    }

    //endregion

    //region gettersAndSetters

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //WARNING: null if is root
    public Tree<T> getParent() {
        return parent;
    }

    public void setParent(Tree<T> parent) {
        if(parent != null) parent.children.remove(this);
        this.parent = parent;
    }

    public ArrayList<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Tree<T>> children) {
        for (Tree<T> tree : children) {
            tree.setParent(this);
        }
        this.children = children;
    }

    //WARNING: to avoid errors ask only for a child that exist, you can check it with existChild(position)
    public Tree<T> getChild(int position) {
        return this.children.get(position);
    }

    //This let you get the child of the child of the child...
    //WARNING: to avoid errors ask only for a child that exist, you can check it with existChild(position)
    public Tree<T> getChild(int[] position) {
        Tree<T> child = this;
        for(int i = 0; i < position.length; i++) {
            child = child.getChild(position[i]);
        }
        return child;
    }

    public Tree<T> getChildIfExist(int position) {
        if(this.existChild(position)) return this.getChild(position);
        else return this;
    }

    public Tree<T> getChildIfExist(int[] position) {
        Tree<T> child = this;
        for(int i = 0; i < position.length; i++) {
            if(child.existChild(position[i])) child = child.getChild(position[i]);
            else break;
        }
        return child;
    }

    public int getPositionChild(Tree<T> tree) {
        return this.children.indexOf(tree);
    }

    public Tree<T> getRoot() {
        Tree<T> root = this;
        while(!root.isRoot()) {
            root = root.getParent();
        }
        return root;
    }

    //How much have you entered in the tree
    public int getLevel() {
        int level = 0;
        Tree root = this;
        while(!root.isRoot()) {
            root = root.getParent();
            level++;
        }
        return level;
    }

    //endregion

    //region others

    public void addChildren(ArrayList<Tree<T>> children) {
        for (Tree<T> tree : children) {
            this.addChild(tree);
        }
    }

    public void addChild(Tree<T> tree) {
        tree.setParent(this);
        this.children.add(tree);
    }

    //Return true if could find and remove the child
    public boolean removeChild(int position) {
        if(existChild(position)) {
            this.getChild(position).setParent(null);
            return true;
        }
        return false;
    }

    public boolean existChild(int position) {
        return (position >= 0 && position < children.size());
    }

    public boolean existChild(int[] position) {
        Tree<T> child = this;
        for(int i = 0; i < position.length; i++) {
            if(child.existChild(position[i])) child = child.getChild(position[i]);
            else return false;
        }
        return true;
    }

    public int numberOfChildren() {
        return this.children.size();
    }

    //A root is where a tree start
    public boolean isRoot() {
        return this.parent == null;
    }

    //And a leaf is where a tree ends
    public boolean isLeaf() {
        return numberOfChildren() == 0;
    }

    //endregion
}