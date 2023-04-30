# Robi (FR)

Ce projet consiste en la création d'un prototype de moniteur pour un système de commande d'éléments graphiques représentés par des formes géométriques basiques telles que des rectangles, des ellipses, des lignes ou du texte. Le moniteur permet aux utilisateurs d'installer ces éléments dans un conteneur et de les animer. 

 

Ce projet est divisé en deux parties distinctes :

## **Partie 1 : Création et ajout d'objets graphiques en 2D**

La première partie du projet consiste à créer et ajouter des objets graphiques en 2D à l'aide d'un logiciel dédié. Les objets graphiques peuvent être de différentes formes, couleurs et tailles, et sont destinés à être affichés sur une fenêtre JFrame.

## **Partie 2 : Création d'un serveur Robi pour implémenter la partie 1**

La seconde partie du projet consiste à créer un serveur Robi qui permet d'implémenter la première partie du projet et qui renvoie le résultat à un client. Le serveur Robi est un serveur  qui permet de recevoir les demandes de création et d'ajout d'objets graphiques en 2D, de les traiter et de renvoyer le résultat au client via des Sockets.

## **Fonctionnalités**

- Création, ajout, suppression d'objets graphiques
- Modification des objets en termes de couleur, dimensions et position dans un GSpace.
- Création d'un environnement pour chaque client connecté(Le serveur traite les demandes d’un seul client à la fois et pas plus )
- Gestion des commandes pour chaque objet ajouté à l'espace graphique
- Modification des objets à partir des commandes correspondant à cet objet
- Affichage de l’environnement  et l’image du résultat coté client ainsi que les SNodes.

## **Éléments techniques et bilan critique**

Dans cette section, nous fournissons des informations techniques supplémentaires qui peuvent aider à comprendre nos choix de conception et de programmation.

### Solution

Notre projet suit une architecture client-serveur, où le client envoie des demandes au serveur qui les traite et renvoie les résultats. 

Dans la totalité du projet , le client envoie une demande qui est une instance de la classe DataCS qui est sérialisé en JSON , et le serveur renvoie un objet de la classe DataSC sérialisé.

1.  Dans les exercices 1, 2 et 3, nous utilisons la classe DataCS qui contient deux attributs : cmd (type de commande "prog", "block", "step") et param (l'expression qui sera exécutée). Lorsque le client envoie une requête au serveur, un objet DataCS est créé et envoyé avec le script à exécuter dans param, tandis que cmd est défini à "prog" pour informer le serveur qu'il s'agit d'un simple envoi de code. Le serveur stocke ensuite ce code pour une exécution ultérieure, soit en bloc, soit en étape, en fonction de la demande de l'utilisateur.
    
    Lorsque le serveur traite la requête, il renvoie un objet de type DataCS au client contenant l'image du résultat. Le client vérifie alors l'attribut "error" de cet objet pour s'assurer que le serveur a réussi à générer l'image avant de l'afficher.
    
2. Dans les exercices 4  on modifie la classe DataSC en ajoutant un objet EnvironmentSimple qui contient un tableau de ReferenceSimple .

⇒le package main dans Client et Server contient la version des exercices 1 ,2 et 3

⇒le package clnt_ex4 dans  Client contient la version de l’exercice 4 et le package svr_ex4 contient aussi la version correspondante. 

### **Bilan critique**

Lors de la conception de notre projet, nous avons constaté qu'il n'y avait pas de confirmation de réception (synack) envoyée par le serveur lorsque celui-ci recevait un message du client. Bien que cela n'ait aucun impact sur le fonctionnement de l'application, nous avons réalisé que cela était important pour une communication client-serveur plus claire et organisée.

## **Auteurs**

- Hanane ERRAJI
- Ziad LAHROUNI
- Youenn ROBITZER
- Gwendal LE TAREAU
