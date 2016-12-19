package com.example.usuario.MagicQuiz;

public class Data {

    public Tree<Quiz> testTree() {
        Tree<Quiz> tree = new Tree<Quiz>(new Quiz("Pregunta Principal"));
        tree.getRoot().getData().addAnswer("Respuesta 1");
        tree.getRoot().addChild(new Node<Quiz>(new Quiz("Pregunta1")));
        tree.getRoot().getData().addAnswer("Respuesta 2");

        return tree;
    }
}
