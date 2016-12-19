package com.example.usuario.MagicQuiz;

public class Data {

    /*
    public Tree<Quiz> exampleTree() {
        Tree<Quiz> tree = new Tree<Quiz>(new Quiz("Pregunta Principal"));
        tree.getRoot().getData().addAnswer("Respuesta 1");
        tree.createNode(new int[]{0}, new Node<Quiz>(new Quiz("Pregunta 1")));
            tree.getNode(new int[]{0}).getData().addAnswer("Respuesta 1.1");
            tree.createNode(new int[]{0,0}, new Node<Quiz>(new Quiz("Pregunta 1.1")));
                tree.getNode(new int[]{0,0}).getData().addAnswer("Respuesta 1.1.1");
                tree.createNode(new int[]{0,0,0}, new Node<Quiz>(new Quiz("Pregunta 1.1.1")));
                tree.getNode(new int[]{0,0}).getData().addAnswer("Respuesta 1.1.2");
                tree.createNode(new int[]{0,0,1}, new Node<Quiz>(new Quiz("Pregunta 1.1.2")));
            tree.getNode(new int[]{0}).getData().addAnswer("Respuesta 1.2");
            tree.createNode(new int[]{0,1}, new Node<Quiz>(new Quiz("Pregunta 1.2")));
                tree.getNode(new int[]{0,1}).getData().addAnswer("Respuesta 1.2.1");
                tree.createNode(new int[]{0,0,0}, new Node<Quiz>(new Quiz("Pregunta 1.2.1")));
                tree.getNode(new int[]{0,1}).getData().addAnswer("Respuesta 1.2.2");
                tree.createNode(new int[]{0,1,1}, new Node<Quiz>(new Quiz("Pregunta 1.2.2")));
        tree.getRoot().getData().addAnswer("Respuesta 2");
        tree.createNode(new int[]{1}, new Node<Quiz>(new Quiz("Pregunta 2")));
            tree.getNode(new int[]{1}).getData().addAnswer("Respuesta 2.1");
            tree.createNode(new int[]{1,0}, new Node<Quiz>(new Quiz("Pregunta 2.1")));
                tree.getNode(new int[]{1,0}).getData().addAnswer("Respuesta 2.1.1");
                tree.createNode(new int[]{1,0,0}, new Node<Quiz>(new Quiz("Pregunta 2.1.1")));
                tree.getNode(new int[]{1,0}).getData().addAnswer("Respuesta 2.1.2");
                tree.createNode(new int[]{1,0,1}, new Node<Quiz>(new Quiz("Pregunta 2.1.2")));
            tree.getNode(new int[]{1}).getData().addAnswer("Respuesta 2.2");
            tree.createNode(new int[]{1,1}, new Node<Quiz>(new Quiz("Pregunta 2.2")));
                tree.getNode(new int[]{1,1}).getData().addAnswer("Respuesta 2.2.1");
                tree.createNode(new int[]{1,0,0}, new Node<Quiz>(new Quiz("Pregunta 2.2.1")));
                tree.getNode(new int[]{1,1}).getData().addAnswer("Respuesta 2.2.2");
                tree.createNode(new int[]{1,1,1}, new Node<Quiz>(new Quiz("Pregunta 2.2.2")));

        return tree;
    }
    */

    public static Tree<Quiz> testTree() {
        Tree<Quiz> tree = new Tree<Quiz>(new Quiz("¿Qué ha pasado?"));
        tree.getRoot().getData().addAnswer("El jugador ha robado una carta extra");
        tree.createNode(new int[]{0}, new Node<Quiz>(new Quiz("¿Conocía la carta del top?")));
            tree.getNode(new int[]{0}).getData().addAnswer("Si");
            tree.createNode(new int[]{0,0}, new Node<Quiz>(new Quiz("¿El oponente la conocía?")));
                tree.getNode(new int[]{0,0}).getData().addAnswer("Si");
                tree.createNode(new int[]{0,0,0}, new Node<Quiz>(new Quiz("GPE - Game Rule Violation, se coloca la carta de vuelta al top, tiene un warning ")));
                tree.getNode(new int[]{0,0}).getData().addAnswer("No");
                tree.createNode(new int[]{0,0,1}, new Node<Quiz>(new Quiz("GPE - Hidden Card Error, el oponente le mira la mano, elige una carta de allí y se pone en el top, tiene un warning")));
            tree.getNode(new int[]{0}).getData().addAnswer("No");
            tree.createNode(new int[]{0,1}, new Node<Quiz>(new Quiz("GPE - Hidden Card Error, el oponente le mira la mano, elige una carta de allí y se baraja en la porción aleatoria de la biblioteca, tiene un warning")));
        tree.getRoot().getData().addAnswer("El jugador ha apuñalado a su oponente");
        tree.createNode(new int[]{1}, new Node<Quiz>(new Quiz("¿El oponente jugaba Eggs?")));
            tree.getNode(new int[]{1}).getData().addAnswer("Si");
            tree.createNode(new int[]{1,0}, new Node<Quiz>(new Quiz("UC - Minor, dale un Warning")));
            tree.getNode(new int[]{1}).getData().addAnswer("No");
            tree.createNode(new int[]{1,1}, new Node<Quiz>(new Quiz("UC - Aggresive Behavior, DQ, involucra al TO y de paso llama a la poli")));
            tree.getNode(new int[]{1}).getData().addAnswer("No, jugaba Lantern");
            tree.createNode(new int[]{1,1}, new Node<Quiz>(new Quiz("Di a los jugadores que continúen jugando")));

        return tree;
    }
}
