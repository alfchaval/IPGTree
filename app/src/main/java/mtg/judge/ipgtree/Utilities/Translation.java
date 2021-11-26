package mtg.judge.ipgtree.Utilities;

public class Translation {
    //Yes, I know I can change localice and have a strings.xml for each language
    public static String StringMap(int index) {
        String result = "";
        switch (Repository.language) {
            case Repository.SPANISH:
                switch (index) {
                    case 1:
                        result = "Reiniciar";
                        break;
                    case 2:
                        result = "Total: ";
                        break;
                    case 3:
                        result = "Deshacer";
                        break;
                    case 4:
                        result = "Lista de baneos";
                        break;
                    case 5:
                        result = "Proceso de descalificación";
                        break;
                    case 6:
                        result = "Enlaces";
                        break;
                    case 7:
                        result = "Sobre: ";
                        break;
                    case 8:
                        result = " Carta: ";
                        break;
                    case 9:
                        result = "Tiempo para revisar los picks";
                        break;
                    case 10:
                        result = "Anterior";
                        break;
                    case 11:
                        result = "Tiempo para mirar el sobre";
                        break;
                    case 12:
                        result = "Modo: Normal";
                        break;
                    case 13:
                        result = "Modo: 2HG";
                        break;
                    case 14:
                        result = "Siguiente";
                        break;
                    case 15:
                        result = "Pausar";
                        break;
                    case 16:
                        result = "Reiniciar sobre";
                        break;
                    case 17:
                        result = "Reiniciar draft";
                        break;
                    case 18:
                        result = "Reanudar";
                        break;
                    case 19:
                        result = "Modo: Rochester";
                        break;
                    case 20:
                        result = "Comenzar";
                        break;
                    case 21:
                        result = "Editar";
                        break;
                    case 22:
                        result = "Minutos: ";
                        break;
                    case 23:
                        result = "No";
                        break;
                    case 24:
                        result = "REGISTRO";
                        break;
                    case 25:
                        result = "REINICIAR";
                        break;
                    case 26:
                        result = "¿Seguro que quieres reiniciar las vidas?";
                        break;
                    case 27:
                        result = "Reiniciar vidas";
                        break;
                    case 28:
                        result = "Si";
                        break;
                    case 29:
                        result = "usuario";
                        break;
                    case 30:
                        result = "Contador de listas";
                        break;
                    case 31:
                        result = "Documentos";
                        break;
                    case 32:
                        result = "Draft";
                        break;
                    case 33:
                        result = "Quiz IPG";
                        break;
                    case 34:
                        result = "Árbol IPG";
                        break;
                    case 35:
                        result = "Contador de vida";
                        break;
                    case 36:
                        result = "Oracle";
                        break;
                    case 37:
                        result = "Ajustes";
                        break;
                    case 38:
                        result = "Temporizador";
                        break;
                    case 39:
                        result = "Introduce una carta en inglés";
                        break;
                    case 40:
                        result = "Cambiar a búsqueda por nombre";
                        break;
                    case 41:
                        result = "Cambiar a búsqueda por edición y número";
                        break;
                    case 42:
                        result = "ERROR";
                        break;
                    case 43:
                        result = "No se encontraron resultados";
                        break;
                    case 44:
                        result = "No tienes cartas en la base de datos local, puedes descargar la base de datos actualizada desde Opciones";
                        break;
                    case 45:
                        result = "Set seleccionado: ";
                        break;
                    case 46:
                        result = "Introduce una edición en inglés";
                        break;
                    case 47:
                        result = "FINALIZAR";
                        break;
                    case 48:
                        result = "No hay más preguntas, pulsa en finalizar para comprobar tus resultados... o puedes usar la flecha para volver y repasar tus respuestas";
                        break;
                    case 49:
                        result = " Puedes ver las respuestas correctas desplazándote por las preguntas con las flechas";
                        break;
                    case 50:
                        result = "Tu puntuación total es: ";
                        break;
                    case 51:
                        result = "Jugador";
                        break;
                    case 52:
                        result = "FONDOS";
                        break;
                    case 53:
                        result = "Continuar";
                        break;
                    case 54:
                        result = "La base de datos pesa más que un Colossal Dreadmaw, recomendamos conexión wifi antes de continuar";
                        break;
                    case 55:
                        result = "Descargado en: ";
                        break;
                    case 56:
                        result = "Descargar Oracle";
                        break;
                    case 57:
                        result = "Error";
                        break;
                    case 58:
                        result = "Error de conexión";
                        break;
                    case 59:
                        result = "Salir";
                        break;
                    case 60:
                        result = "Servidor FTP";
                        break;
                    case 61:
                        result = "Actualizar oracle";
                        break;
                    case 62:
                        result = "contraseña";
                        break;
                    case 63:
                        result = "Guardar";
                        break;
                    case 64:
                        result = "Activar envío de datos";
                        break;
                    case 65:
                        result = "Mostrar anotaciones en AMTR Y AIPG";
                        break;
                    case 66:
                        result = "(También pueden mostarse/ocultarse manteniendo pulsado en el título de la sección del documento)";
                        break;
                    case 67:
                        result = "Conexión exitosa";
                        break;
                    case 68:
                        result = "Buscar";
                        break;
                    case 69:
                        result = "No se encontraron resultados";
                        break;
                    case 70:
                        result = "Buscar actualizaciones al iniciar";
                        break;
                    case 71:
                        result = "Actualizar documentos";
                        break;
                    case 72:
                        result = "Ajustes avanzados";
                        break;
                    case 73:
                        result = "Parece que no tienes este documento, puedes descargarlo desde el menú Ajustes";
                        break;
                    case 74:
                        result = "Actualización disponible";
                        break;
                    case 75:
                        result = "Anuncio del HJ";
                        break;
                    case 76:
                        result = "Mostrar/Ocultar FTP";
                        break;
                    case 77:
                        result = "Mostrar/Ocultar Links";
                        break;
                    case 78:
                        result = "Copiado al Salvapapeles";
                        break;
                    case 79:
                        result = "Resultado de D";
                        break;
                    case 80:
                        result = "Elige número de caras";
                        break;
                    case 81:
                        result = "Aceptar";
                        break;
                    case 82:
                        result = "Cancelar";
                        break;
                    case 83:
                        result = "¿Seguro que quieres reiniciar el temporizador?";
                        break;
                    case 84:
                        result = "Reiniciar temporizador";
                        break;
                    case 85:
                        result = "Reglas completas";
                        break;
                    case 86:
                        result = "JAR";
                        break;
                    case 87:
                        result = "IPG anotadas";
                        break;
                    case 88:
                        result = "MTR anotadas";
                        break;
                    case 89:
                        result = "IPG digital";
                        break;
                    case 90:
                        result = "MTR digital";
                        break;
                    case 91:
                        result = "Jugadores en el contador de vida";
                        break;
                    case 92:
                        result = "Número de jugadores";
                        break;
                    case 93:
                        result = "Mostrar/Ocultar Contador de vida";
                        break;
                    case 94:
                        result = "Invertir vida superior";
                        break;
                    case 95:
                        result = "Ejemplo.png";
                        break;
                }
                break;
            case Repository.ENGLISH:
                switch (index) {
                    case 1:
                        result = "Reset";
                        break;
                    case 2:
                        result = "Total: ";
                        break;
                    case 3:
                        result = "Undo";
                        break;
                    case 4:
                        result = "Banned lists";
                        break;
                    case 5:
                        result = "Disqualification process";
                        break;
                    case 6:
                        result = "Links";
                        break;
                    case 7:
                        result = "Booster: ";
                        break;
                    case 8:
                        result = " Card: ";
                        break;
                    case 9:
                        result = "Time to revise picks";
                        break;
                    case 10:
                        result = "Last";
                        break;
                    case 11:
                        result = "Time to look the booster";
                        break;
                    case 12:
                        result = "Mode: Normal";
                        break;
                    case 13:
                        result = "Mode: 2HG";
                        break;
                    case 14:
                        result = "Next";
                        break;
                    case 15:
                        result = "Pause";
                        break;
                    case 16:
                        result = "Reset booster";
                        break;
                    case 17:
                        result = "Reset draft";
                        break;
                    case 18:
                        result = "Resume";
                        break;
                    case 19:
                        result = "Mode: Rochester";
                        break;
                    case 20:
                        result = "Start";
                        break;
                    case 21:
                        result = "Edit";
                        break;
                    case 22:
                        result = "Minutes: ";
                        break;
                    case 23:
                        result = "No";
                        break;
                    case 24:
                        result = "REGISTRY";
                        break;
                    case 25:
                        result = "RESET";
                        break;
                    case 26:
                        result = "Do you really want to reset?";
                        break;
                    case 27:
                        result = "Reset life";
                        break;
                    case 28:
                        result = "Yes";
                        break;
                    case 29:
                        result = "user";
                        break;
                    case 30:
                        result = "Decklist counter";
                        break;
                    case 31:
                        result = "Documents";
                        break;
                    case 32:
                        result = "Draft";
                        break;
                    case 33:
                        result = "IPG Quiz";
                        break;
                    case 34:
                        result = "IPG Tree";
                        break;
                    case 35:
                        result = "Life counter";
                        break;
                    case 36:
                        result = "Oracle";
                        break;
                    case 37:
                        result = "Settings";
                        break;
                    case 38:
                        result = "Timer";
                        break;
                    case 39:
                        result = "Enter a card name";
                        break;
                    case 40:
                        result = "Change to name search";
                        break;
                    case 41:
                        result = "Change to set search";
                        break;
                    case 42:
                        result = "ERROR";
                        break;
                    case 43:
                        result = "Nothing found";
                        break;
                    case 44:
                        result = "There are no cards in your local database, you can download an updated database from Settings menu";
                        break;
                    case 45:
                        result = "Selected set: ";
                        break;
                    case 46:
                        result = "Enter a set name";
                        break;
                    case 47:
                        result = "END";
                        break;
                    case 48:
                        result = "There are no more questions, you can press end to see your result or use the arrow to check your answers";
                        break;
                    case 49:
                        result = " You can see the correct answers using the arrows";
                        break;
                    case 50:
                        result = "Your total score is: ";
                        break;
                    case 51:
                        result = "Player";
                        break;
                    case 52:
                        result = "BACKGROUNDS";
                        break;
                    case 53:
                        result = "Continue";
                        break;
                    case 54:
                        result = "The database weighs more than a Colossal Dreadmaw, we recommend wifi connection before download";
                        break;
                    case 55:
                        result = "Downloaded in: \"";
                        break;
                    case 56:
                        result = "Download Oracle";
                        break;
                    case 57:
                        result = "Error";
                        break;
                    case 58:
                        result = "Connection error";
                        break;
                    case 59:
                        result = "Close";
                        break;
                    case 60:
                        result = "FTP Server";
                        break;
                    case 61:
                        result = "Update oracle";
                        break;
                    case 62:
                        result = "password";
                        break;
                    case 63:
                        result = "Save";
                        break;
                    case 64:
                        result = "Allow sending data";
                        break;
                    case 65:
                        result = "Show annotations in AMTR and AIPG";
                        break;
                    case 66:
                        result = "(You can also show/hide annotations pressing and holding in the document section title)";
                        break;
                    case 67:
                        result = "Suscessful connection";
                        break;
                    case 68:
                        result = "Search";
                        break;
                    case 69:
                        result = "No results found";
                        break;
                    case 70:
                        result = "Check for updates at start";
                        break;
                    case 71:
                        result = "Update documents";
                        break;
                    case 72:
                        result = "Advanced settings";
                        break;
                    case 73:
                        result = "It looks like you don't have this document, you can download it from Settings menu";
                        break;
                    case 74:
                        result = "Available update";
                        break;
                    case 75:
                        result = "HJ Announcement";
                        break;
                    case 76:
                        result = "Show/Hide FTP Config";
                        break;
                    case 77:
                        result = "Show/Hide Document links";
                        break;
                    case 78:
                        result = "Saved to Clipboard";
                        break;
                    case 79:
                        result = "Result from D";
                        break;
                    case 80:
                        result = "Choose number of sides";
                        break;
                    case 81:
                        result = "Ok";
                        break;
                    case 82:
                        result = "Cancel";
                        break;
                    case 83:
                        result = "Do you really want to reset?";
                        break;
                    case 84:
                        result = "Reset timer";
                        break;
                    case 85:
                        result = "Comprehensive Rules";
                        break;
                    case 86:
                        result = "JAR";
                        break;
                    case 87:
                        result = "Annotated IPG";
                        break;
                    case 88:
                        result = "Annotated MTR";
                        break;
                    case 89:
                        result = "Digital IPG";
                        break;
                    case 90:
                        result = "Digital MTR";
                        break;
                    case 91:
                        result = "Players in life counter";
                        break;
                    case 92:
                        result = "Number of players";
                        break;
                    case 93:
                        result = "Show/Hide Lifecounter";
                        break;
                    case 94:
                        result = "Inverse upper life";
                        break;
                    case 95:
                        result = "Example.png";
                        break;
                }
                break;
            case Repository.FRENCH:
                switch (index) {
                    case 1:
                        result = "Redémarrage";
                        break;
                    case 2:
                        result = "Total: ";
                        break;
                    case 3:
                        result = "Annuler";
                        break;
                    case 4:
                        result = "Cartes Interdites";
                        break;
                    case 5:
                        result = "Processus de disqualification";
                        break;
                    case 6:
                        result = "Liens";
                        break;
                    case 7:
                        result = "Booster: ";
                        break;
                    case 8:
                        result = " Carte: ";
                        break;
                    case 9:
                        result = "Temps de réviser les choix";
                        break;
                    case 10:
                        result = "Antérieur";
                        break;
                    case 11:
                        result = "Temps de regarder le booster";
                        break;
                    case 12:
                        result = "Mode: Normal";
                        break;
                    case 13:
                        result = "Mode: 2HG";
                        break;
                    case 14:
                        result = "Suivant";
                        break;
                    case 15:
                        result = "Pause";
                        break;
                    case 16:
                        result = "Redémarrage booster";
                        break;
                    case 17:
                        result = "Redémarrage draft";
                        break;
                    case 18:
                        result = "Reprendre";
                        break;
                    case 19:
                        result = "Mode: Rochester";
                        break;
                    case 20:
                        result = "Début";
                        break;
                    case 21:
                        result = "Éditer";
                        break;
                    case 22:
                        result = "Minutes: ";
                        break;
                    case 23:
                        result = "Non";
                        break;
                    case 24:
                        result = "RECORD";
                        break;
                    case 25:
                        result = "REDÉMARRAGE";
                        break;
                    case 26:
                        result = "Voulez-vous vraiment redémarrage?";
                        break;
                    case 27:
                        result = "Redémarrage vie";
                        break;
                    case 28:
                        result = "Oui";
                        break;
                    case 29:
                        result = "usager";
                        break;
                    case 30:
                        result = "Compteur de liste";
                        break;
                    case 31:
                        result = "Documents";
                        break;
                    case 32:
                        result = "Draft";
                        break;
                    case 33:
                        result = "IPG Quiz";
                        break;
                    case 34:
                        result = "IPG Arbre";
                        break;
                    case 35:
                        result = "Compteur de vie ";
                        break;
                    case 36:
                        result = "Oracle";
                        break;
                    case 37:
                        result = "Paramètres";
                        break;
                    case 38:
                        result = "Minuteur";
                        break;
                    case 39:
                        result = "Entrer une carte en anglais";
                        break;
                    case 40:
                        result = "Passer à la recherche de nom";
                        break;
                    case 41:
                        result = "Passer à la recherche de édition";
                        break;
                    case 42:
                        result = "ERREUR";
                        break;
                    case 43:
                        result = "Rien n'a été trouvé";
                        break;
                    case 44:
                        result = "Il n'y a pas de cartes dans votre base de données locale, vous pouvez télécharger une base de données mise à jour à partir du menu Paramètres";
                        break;
                    case 45:
                        result = "Édition sélectionné: ";
                        break;
                    case 46:
                        result = "Entrez un nom d'édition";
                        break;
                    case 47:
                        result = "FINIR";
                        break;
                    case 48:
                        result = "Il n'y a plus de questions, vous pouvez appuyer sur fin pour voir votre résultat ou utiliser la flèche pour vérifier vos réponses";
                        break;
                    case 49:
                        result = " Vous pouvez voir les bonnes réponses en utilisant les flèches";
                        break;
                    case 50:
                        result = "Votre note totale est: ";
                        break;
                    case 51:
                        result = "Joueur";
                        break;
                    case 52:
                        result = "FONDS";
                        break;
                    case 53:
                        result = "Continuaire";
                        break;
                    case 54:
                        result = "La base de données pèse plus qu'un Colossal Dreadmaw, nous recommandons la connexion wifi avant le téléchargement";
                        break;
                    case 55:
                        result = "Téléchargé dans: \"";
                        break;
                    case 56:
                        result = "Télécharger Oracle";
                        break;
                    case 57:
                        result = "Erreur";
                        break;
                    case 58:
                        result = "Erreur de connexion";
                        break;
                    case 59:
                        result = "Fermer";
                        break;
                    case 60:
                        result = "Serveur FTP";
                        break;
                    case 61:
                        result = "Mettre à jour l'oracle";
                        break;
                    case 62:
                        result = "Mot de passe";
                        break;
                    case 63:
                        result = "Sauvegarder";
                        break;
                    case 64:
                        result = "Autoriser l'envoi de données";
                        break;
                    case 65:
                        result = "Afficher les annotations dans AMTR et AIPG";
                        break;
                    case 66:
                        result = "(Vous pouvez également afficher/masquer les annotations en appuyant longuement sur le titre de la section du document)";
                        break;
                    case 67:
                        result = "Connexion réussie";
                        break;
                    case 68:
                        result = "Chercher";
                        break;
                    case 69:
                        result = "Aucun résultat trouvé";
                        break;
                    case 70:
                        result = "Vérifier les mises à jour au démarrage";
                        break;
                    case 71:
                        result = "Mettre à jour les documents";
                        break;
                    case 72:
                        result = "Réglages avancés";
                        break;
                    case 73:
                        result = "Il semble que vous n'ayez pas ce document, vous pouvez le télécharger depuis le menu Paramètres";
                        break;
                    case 74:
                        result = "Mise à jour disponible";
                        break;
                    case 75:
                        result = "HJ Annonce";
                        break;
                    case 76:
                        result = "Afficher/Masquer la configuration FTP";
                        break;
                    case 77:
                        result = "Afficher/Masquer les liens vers les documents";
                        break;
                    case 78:
                        result = "Enregistré dans le presse-papiers ";
                        break;
                    case 79:
                        result = "Résultat de D";
                        break;
                    case 80:
                        result = "Choisissez le nombre de côtés";
                        break;
                    case 81:
                        result = "D'accord";
                        break;
                    case 82:
                        result = "Annuler";
                        break;
                    case 83:
                        result = "Voulez-vous vraiment redémarrage?";
                        break;
                    case 84:
                        result = "Redémarrage la minuterie";
                        break;
                    case 85:
                        result = "Règles complètes";
                        break;
                    case 86:
                        result = "JAR";
                        break;
                    case 87:
                        result = "Annoté IPG";
                        break;
                    case 88:
                        result = "Annoté MTR";
                        break;
                    case 89:
                        result = "Virtuel IPG";
                        break;
                    case 90:
                        result = "Virtuel MTR";
                        break;
                    case 91:
                        result = "Compteur de joueurs en vie";
                        break;
                    case 92:
                        result = "Nombre de joueurs";
                        break;
                    case 93:
                        result = "Afficher/Masquer le compteur de vie";
                        break;
                    case 94:
                        result = "Vie supérieure inversée";
                        break;
                    case 95:
                        result = "Exemple.png";
                        break;
                }
                break;
            default:
                if(index == 37)
                {
                    result = "Select language";
                }
                else
                {
                    result = "---";
                }
                break;
        }
        return result;
    }
}
