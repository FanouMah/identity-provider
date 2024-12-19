# Todo List - Projet Fournisseur d'Identité

## **1. Configuration de l'Environnement**
- [x][Fanou] **Initialiser le projet Spring Boot**
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
- [ ][Fanou] **Configurer Docker Compose pour PostgreSQL et l'application**
    - Créer un fichier `docker-compose.yaml` avec PostgreSQL et SMTP
    - Configurer les ports exposés
- [x][Yohan] **Configurer l'application Spring Boot**
    - Fichier `application.properties` pour :
      - Configuration de la base de données PostgreSQL
      - Configuration SMTP (envoi d'email)
      - Configuration JWT (clé secrète, expiration)
- [ ] **Configurer MailHog pour les tests d'email locaux**

---

## **2. Base de Données**
- [x][Itokiana] **Modélisation de la base de données**
    - Création des entités :
        - `User` : Gère les utilisateurs (id, email, password, nom, prenom, emailVerified, failedLoginAttempts, sessionExpiry)
        - `VerificationToken` : Gère les tokens pour la validation d'email (avec date d'expiration)
        - `LoginAttempt` : Gère les tentatives de connexion
        - `PinVerification` : Stocke les PIN temporaires pour la validation multifactorielle
- [x][Fanou & Yohan] **Création des répositories JPA**
    - `UserRepository`
    - `VerificationTokenRepository`
    - `LoginAttemptRepository`
    - `PinVerificationRepository`
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
- [x][Fanou] Endpoint **POST /api/login/send**
    - Valider les identifiants utilisateur
    - Générer un PIN aléatoire (aléatoire à chaque tentative) et l'envoyer par email
    - Stocker le PIN temporairement avec une expiration
- [x][Fanou] Endpoint **POST /api/login/verify**
    - Valider le PIN entré par l'utilisateur
    - Retourner un JWT si la validation réussit
- [x][Fanou] Gestion des **tentatives de connexion**
    - Compter les échecs
    - Bloquer le compte après 3 échecs
    - Envoyer un email avec un lien de validation contenant le token
    - Endpoint **POST /api/user/resettentative/send** : Réinitialiser les tentatives par email
    - Endpoint **GET /api/user/resettentative/verify** : valider l'email a l'aide d'un token

### **Gestion de Compte Utilisateur**
- [ ][Ony] Endpoint **GET /api/user/profile** : Récupérer les informations de l'utilisateur
- [ ][Ony] Endpoint **PUT /api/user/update** : Modifier les informations utilisateur (nom, prénom)
- [ ][Ony] Empêcher la modification de l'email

### **Gestion des Sessions**
- [ ][Ony] Utiliser **JWT** pour gérer les sessions
    - Durée de vie paramétrable
    - Invalidation du token à la déconnexion
- [ ][Ony] Endpoint **POST /api/auth/logout** : Invalider le token JWT

### **Sécurisation des Mots de Passe**
- [ ][Ony] Hashing des mots de passe avec **BCrypt**
- [ ][Ony] Implémenter une politique de mot de passe fort

---

## **4. Emails au Format HTML**
- [ ] Création des templates HTML pour :
    - Validation d'email : `verification_email.html`
    - Envoi de PIN : `pin_verification_email.html`
- [ ] Intégration des templates avec Thymeleaf
- [ ] Envoi des emails via SMTP
- [ ] Génération d'un **PIN aléatoire** à chaque nouvelle tentative d'authentification
- [ ] Gérer les **tokens expirés** pour la validation d'email

---

## **5. Documentation API avec Swagger**
- [ ] Ajouter la dépendance Swagger
- [ ] Configurer Swagger UI accessible à `/swagger-ui/`
- [ ] Documenter tous les endpoints :
    - Inscription
    - Authentification
    - Validation de PIN
    - Gestion de compte

---

## **6. Tests et Validation**
- [ ] **Tests Unitaires**
    - Créer des tests pour les services (JUnit 5 / Mockito)
- [ ] **Tests d'Intégration**
    - Tester les endpoints via Postman ou MockMvc
- [ ] **Vérification de l'envoi d'emails**
- [ ] **Tests des scénarios d'erreurs**
    - Tentatives de connexion échouées
    - Validation d'email expirée
    - PIN incorrect

---

## **7. Collection Postman**
- [ ] Créer une collection Postman pour tester :
    - Inscription
    - Validation d'email
    - Authentification
    - Validation de PIN
    - Gestion du compte utilisateur
- [ ] Inclure des scénarios d'erreur dans la collection

---

## **8. Tests des Scénarios d'Utilisation**

### Scénario 1 : Inscription
1. Utilisateur s'inscrit avec email et mot de passe.
2. Reçoit un email de confirmation avec un lien.
3. Clique sur le lien pour vérifier l'email.
4. Si le token a expiré, recevoir un nouveau lien d'activation.

### Scénario 2 : Authentification Multifacteur
1. Utilisateur se connecte avec email et mot de passe.
2. Reçoit un PIN par email (aléatoire à chaque connexion).
3. Valide le PIN pour se connecter.

### Scénario 3 : Gestion des échecs de connexion
1. Tentatives multiples de connexion avec mot de passe incorrect.
2. Blocage du compte après 3 tentatives.
3. Réinitialisation des tentatives via email.

---

## **9. Améliorations Futures**
- [ ] Ajouter un système de réinitialisation de mot de passe.
- [ ] Implémenter une authentification OAuth2.
- [ ] Ajouter des logs d'activité utilisateur.
- [ ] Mettre en place un système de captcha pour l'inscription.
- [ ] Gérer les tokens expirés pour la validation d'email (déjà ajouté).

---

## **10. Déploiement**
- [ ] Créer une image Docker de l'application
- [ ] Pousser l'image sur Docker Hub
- [ ] Déployer sur un serveur cloud (AWS, GCP, Azure)
