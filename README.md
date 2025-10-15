## 🧩 À propos du projet

Ce projet a été réalisé durant ma **première année de BUT Informatique** à l’**IUT Robert Schuman**, sous la supervision d’enseignants et dans un cadre académique.  

Le README fourni par les enseignants peut être trouvé [ici](/README_OLD.md).

L’objectif est de développer une **application Java** pour un **jeu de rôle Dungeons & Dragons (D&D)**.  
C’est un jeu de **Medieval Fantasy** où les joueurs incarnent des personnages affrontant des monstres dans plusieurs **donjons successifs**.

<br>

## 👥 Organisation

Travail en **binôme** avec :  
- **[Diya B.](https://github.com/Dididouu)**

<br>

## 🎲 Description du jeu

Ce projet implémente un **jeu de rôle basé sur Donjons & Dragons** dans un terminal Java.  

Fonctionnalités principales :  
- **Création de personnages** : Personnages avec différentes races et classes  
- **Exploration de donjons** : Navigation à travers plusieurs cartes  
- **Système de combat** : Combats au tour par tour contre des monstres  
- **Gestion d’inventaire** : Stockage d’armes et d’armures  
- **Implémentation du DM** : Maître du jeu contrôlant le déroulement des parties  

Le jeu se termine lorsque **les joueurs meurent** ou **après la victoire sur 3 donjons**.

<br>

## 🛠️ Technologies utilisées

- **Java** — Langage principal du projet  
- **POO (Programmation Orientée Objet)** — Utilisation de l’héritage et du polymorphisme  
- **UML** — Représentation du code via des diagrammes UML

<br>

## 🚀 Comment exécuter le projet

### Prérequis

- Java (JDK 8 ou supérieur) installé sur votre machine  
- IntelliJ IDEA ou tout autre IDE Java compatible  

### Installation

Clonez le dépôt sur votre machine :  

```bash
git clone https://github.com/Mcolerique/DOOjon-et-dragons.git
```
### Exécution dans IntelliJ IDEA

1. Ouvrez le projet dans IntelliJ IDEA

2. Naviguez vers src/Main.java

3. Clic droit sur Main.java → Run

4. e jeu se lancera dans la console intégrée d’IntelliJ

### Compilation et exécution manuelle

```bash
# Compiler le projet
javac -d out $(find src -name "*.java")

# Exécuter le jeu
java -cp out Main
```
