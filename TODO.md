# Todo List - Projet Fournisseur d'Identité

## **1. Configuration de l'Environnement**
- [x][Fanomezantsoa] **Initialiser le projet Spring Boot**
    - Utilisation de Spring Initializr
    - Ajout des dépendances :
      - Spring Web
      - Spring Data JPA
      - Spring Boot Starter Mail
      - Spring Boot Starter Security
      - Spring Boot Starter Thymeleaf
      - PostgreSQL Driver
      - Spring Boot Starter Validation
      - JWT (Java JWT library)
- [ ][Fanomezantsoa] **Configurer Docker Compose pour PostgreSQL et l'application**
    - [ ]Créer un fichier `docker-compose.yaml` avec PostgreSQL et SMTP
    - [ ]Configurer les ports exposés
- [x][Yohan] **Configurer l'application Spring Boot**
    - [x]Fichier `application.properties` pour :
      - Configuration de la base de données PostgreSQL
      - Configuration SMTP (envoi d'email)
      - Configuration JWT (clé secrète, expiration)

---

## **2. Base de Données**
- [x][Itokiana] **Modélisation de la base de données**
    - Création des entités :
        - `session_users` : Gère temporairement les utilisateurs pendant l'inscription
        - `Users` : Gère les utilisateurs 
        - `token_inscription` : Gère les tokens pour la validation d'email
        - `historique_tentative` : Gère les tentatives de connexion
        - `pin_verification` : Stocke les PIN pour la validation multifactorielle
        - `token_reset_pin` : Gère les tokens pour la reinisialisation du nombre de tentative par email
        - `historique_connexion` : Gère les tentatives de connexion
- [x][Fanomezantsoa & Yohan] **Création des répositories JPA**
    - [x]`SessionUserRepository`
    - [x]`UsersRepository`
    - [x]`TokenInscriptionRepository`
    - [x]`HistoriqueTentativeRepository`
    - [x]`PinVerificationRepository`
    - [x]`TokenResetPinRepository`
    - [x]`HistoriqueConnexionRepository`
- [x][Itokiana] **Configurer les scripts d'initialisation de la base de données**

---

## **3. Fonctionnalités Backend**

### **Inscription et Validation d'Email**
- [x][Yohan] Endpoint **POST /api/auth/register**
    - Créer un utilisateur avec mot de passe hashé (BCrypt)
    - Générer un token de validation (avec expiration)
    - Envoyer un email avec un lien de validation contenant le token
- [x][Yohan] Endpoint **GET /api/auth/verify**
    - Valider l'email à partir du token reçu dans le lien
    - Gérer les **tokens expirés** pour la validation d'email
    - Marquer l'email comme vérifié

### **Authentification Multifacteur**
- [x][Fanomezantsoa] Endpoint **POST /api/login/send**
    - Valider les identifiants utilisateur
    - Générer un PIN aléatoire (aléatoire à chaque tentative) et l'envoyer par email
    - Stocker le PIN temporairement avec une expiration
- [x][Fanomezantsoa] Endpoint **POST /api/login/verify**
    - Valider le PIN entré par l'utilisateur
    - Retourner un JWT si la validation réussit
- [x][Fanomezantsoa] Gestion des **tentatives de connexion**
    - Compter les échecs
    - Bloquer le compte après 3 échecs
    - Envoyer un email avec un lien de validation contenant le token
    - Endpoint **POST /api/user/resettentative/send** : Réinitialiser les tentatives par email
    - Endpoint **GET /api/user/resettentative/verify** : valider l'email a l'aide d'un token

### **Gestion de Compte Utilisateur**
- [ ][Ony] Endpoint **GET /api/user/profile** : Récupérer les informations de l'utilisateur
- [x][Ony] Endpoint **PUT /api/user/update** : Modifier les informations utilisateur (nom, prénom)
- [x][Ony] Empêcher la modification de l'email

### **Gestion des Sessions**
- [ ][Ony] Utiliser **JWT** pour gérer les sessions
    - Durée de vie paramétrable
    - Invalidation du token à la déconnexion
- [ ][Ony] Endpoint **POST /api/auth/logout** : Invalider le token JWT

### **Sécurisation des Mots de Passe**
- [x][Yohan] Hashing des mots de passe avec **BCrypt**

---

## **4. Emails au Format HTML**
- [X][Itokiana] Création des templates HTML pour :
    - Validation d'email : `verification_email.html`
    - Envoi de PIN : `pin_verification_email.html`
- [x][Fanomezantsoa] Intégration des templates
- [x][Yohan] Envoi des emails via SMTP
- [x][Fanomezantsoa] Génération d'un **PIN aléatoire** à chaque nouvelle tentative d'authentification
- [x][Yohan] Gérer les **tokens expirés** pour la validation d'email

---

## **5. Documentation API avec Swagger**
- [x][Ony] Ajouter la dépendance Swagger
- [ ][Ony] Configurer Swagger UI accessible à `/swagger-ui/`
- [ ][Ony] Documenter tous les endpoints :
    - Inscription
    - Authentification
    - Validation de PIN
    - Gestion de compte

---

## **6. Tests et Validation**
- [x][Fanomezantsoa & Yohan] **Tests d'Intégration**
    - Tester les endpoints via Postman
- [x][Fanomezantsoa & Yohan] **Vérification de l'envoi d'emails**
- [x][Fanomezantsoa & Yohan] **Tests des scénarios d'erreurs**
    - Tentatives de connexion échouées
    - Validation d'email expirée
    - PIN incorrect

---

## **7. Collection Postman**
- [x][Itokiana] Créer une collection Postman pour tester :
    - [x]Inscription
    - [x]Validation d'email
    - [x]Authentification
    - [x]Validation de PIN
    - [x]Gestion du compte utilisateur
- [ ][Itokiana] Inclure des scénarios d'erreur dans la collection

---

## **8. Déploiement**
- [ ][Fanomezantsoa] Créer une image Docker de l'application
