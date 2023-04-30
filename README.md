# Robi

Robi est un prototype de moniteur permettant la création, l'ajout et l'animation d'éléments graphiques basiques tels que des rectangles, ellipses, lignes ou textes. Le projet se divise en deux parties :

## Partie 1 : Gestion d'objets graphiques 2D
Création et ajout d'objets graphiques 2D avec différentes formes, couleurs et tailles, affichés sur une fenêtre JFrame.

## Partie 2 : Serveur Robi
Implémentation d'un serveur Robi pour gérer les requêtes de création et d'ajout d'objets graphiques 2D et renvoyer les résultats aux clients via des Sockets.

## Partie 3 : Client Robi
Le client Robi dispose d'une interface graphique comprenant :
- Un champ de texte pour envoyer des scripts
- Deux boutons (envoyer et exécuter) pour l'exécution des scripts en mode "step by step" ou "bloc"
- Une section de logs pour afficher l'historique des actions effectuées
- Une zone d'affichage des objets graphiques créés et modifiés

## Fonctionnalités
- Création, ajout et suppression d'objets graphiques
- Modification des objets (couleur, dimensions, position)
- Gestion des commandes pour chaque objet ajouté
- Affichage de l’environnement et du résultat côté client, y compris les objets graphiques
- Interface graphique pour l'envoi et l'exécution des scripts

## Technologies
- Architecture client-serveur
- Java (Swing, Jackson)
- Communication via JSON et Sockets
- Classes DataCS et DataSC pour la gestion des requêtes et des réponses
- GitLab pour le contrôle de version

## Auteurs
- Hanane Erraji
- Ziad Lahrouni
- Youenn Robitzer
- Gwendal Le Tareau
