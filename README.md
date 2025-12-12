# 📦 Mini Gestion Commerciale – Java (Console)

## 📚 Présentation du projet
Ce projet est une application **Java en mode console** permettant de gérer une **mini gestion commerciale**.  
Il a été réalisé dans le cadre d'un **Programmation Orientée Objet (POO)** et respecte les consignes fournies.

L’application permet :
- la gestion des **clients**, **produits** et **factures**,
- la **persistance des données** via des fichiers texte CSV,
- la **génération de factures au format PDF** à l’aide de la librairie **iText**.

---

## 🎯 Objectifs pédagogiques
- Mettre en pratique la **programmation orientée objet en Java**
- Manipuler les **collections Java** (`List`, `ArrayList`, `Map`)
- Lire et écrire des **fichiers texte CSV**
- Implémenter un **menu interactif en console**
- Générer un **PDF de facture formaté** avec **iText**

---

## 🧩 Fonctionnalités

### 👤 Gestion des clients
- Ajouter un client  
- Afficher la liste des clients  
- Rechercher un client par ID  
- Supprimer un client  
- Charger les clients depuis un fichier CSV  
- Enregistrer les clients dans un fichier CSV  

### 📦 Gestion des produits
- Ajouter un produit  
- Afficher la liste des produits  
- Rechercher un produit par ID  
- Supprimer un produit  
- Charger les produits depuis un fichier CSV  
- Enregistrer les produits dans un fichier CSV  

### 🧾 Gestion des factures
- Créer une facture associée à un client  
- Ajouter des produits à une facture avec quantité  
- Afficher le détail d’une facture et son total  
- Supprimer une facture  
- Générer une facture au format **PDF**  
- Charger les factures depuis un fichier CSV  
- Enregistrer les factures dans un fichier CSV  

---

## 🖥️ Interface utilisateur
L’application fonctionne **exclusivement en mode console** et propose :
- un **menu principal**
- des **sous-menus** pour les clients, produits et factures
- des saisies utilisateur sécurisées (contrôles sur les IDs, quantités, formats de date)

---

## 🗂️ Structure du projet

```
Mini Gestion Commerciale
├── data/
│ ├── clients.csv
│ ├── produits.csv
│ └── factures.csv
├── src/
│ ├── gestion/
│ │ ├── GestionClient.java
│ │ ├── GestionProduit.java
│ │ ├── GestionFacture.java
│ │ └── GestionFichier.java
│ ├── models/
│ │ ├── Client.java
│ │ ├── Produit.java
│ │ └── Facture.java
│ ├── util/
│ │ └── GeneratorPDF.java
│ └── Main.java
├── lib/
│ └── itextpdf-5.5.13.3.jar
├── .gitignore
└── README.md
```

---

## 🗃️ Format des fichiers CSV

### `clients.csv`
idclient;nom;prenom;email;adresse;codepostal;ville

### `produits.csv`
idproduit;description;prix

### `factures.csv`
idfacture;datefacture;idclient;idProduit|quantite,idProduit|quantite

---

## 📄 Génération de PDF
La génération des factures PDF est réalisée avec la librairie **iText 5** (`com.itextpdf.text.*`).

Chaque facture PDF contient :
- le numéro de facture
- la date
- les informations du client
- un tableau listant les produits, quantités, prix et sous-totaux
- le **total TTC**

Les fichiers PDF sont générés dans le dossier `data/`.

---

## ⚙️ Contraintes techniques respectées
- Java (mode console)
- Programmation orientée objet
- Utilisation des collections Java
- Lecture / écriture de fichiers texte CSV
- Génération de PDF avec **iText**
- Code structuré et modulaire

---

## 🧪 Lancement du projet
1. Ouvrir le projet dans **IntelliJ IDEA**
2. Vérifier que `itextpdf-5.5.13.3.jar` est bien ajouté aux dépendances
3. Lancer la classe :
Main.java
