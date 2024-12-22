# Identity Provider Project

## Documentation
- [TODO List](TODO.md)
- [Modèle Conceptuel des Données](MCDcloud.jpg)

---

## **Tests des Scénarios d'Utilisation**

### Scénario 1 : Inscription
#### Workflow
1. L'utilisateur s'inscrit avec un email et un mot de passe.
2. Un email de confirmation contenant un lien est envoyé à l'utilisateur.
3. L'utilisateur clique sur le lien pour vérifier son email.

#### Éventuelles Erreurs
**Avant l'envoi de l'email :**
1. Email déjà utilisé par un autre compte.
2. Erreur lors du chargement du modèle d'email HTML.
3. Erreur lors de l'envoi de l'email (problème SMTP).

**Après l'envoi de l'email :**
1. Token de validation invalide.
2. Token expiré (délai configurable).

---

### Scénario 2 : Authentification Multifacteur
#### Workflow
1. L'utilisateur se connecte avec un email et un mot de passe.
2. Un PIN (généré aléatoirement à chaque connexion) est envoyé par email.
3. L'utilisateur valide le PIN pour finaliser la connexion.

#### Éventuelles Erreurs
**Avant l'envoi de l'email :**
1. L'email n'est pas enregistré.
2. Le nombre de tentatives est insuffisant (inférieur à 1).
3. Mot de passe incorrect (le nombre de tentatives diminue).
4. Erreur lors du chargement du modèle d'email HTML.
5. Erreur lors de l'envoi de l'email (problème SMTP).

**Après l'envoi de l'email :**
1. Aucun PIN enregistré.
2. PIN incorrect.
3. PIN expiré.

---

### Scénario 3 : Réinitialisation des Tentatives
#### Workflow
1. L'utilisateur tente de se connecter avec un email et un mot de passe.
2. Un email de réinitialisation contenant un lien est envoyé à l'utilisateur.
3. L'utilisateur clique sur le lien pour réinitialiser le nombre de tentatives.

#### Éventuelles Erreurs
**Avant l'envoi de l'email :**
1. L'email n'est pas enregistré.
2. Mot de passe incorrect (le nombre de tentatives reste inchangé).
3. Erreur lors du chargement du modèle d'email HTML.
4. Erreur lors de l'envoi de l'email (problème SMTP).

**Après l'envoi de l'email :**
1. Token de validation invalide.
2. Token expiré (délai configurable).

---

### Scénario 4 : Gestion du Compte pour l’Utilisateur
#### Workflow
1. L'utilisateur modifie ses informations personnelles.

#### Éventuelles Erreurs
1. Erreur lors de la récupération de l'utilisateur dans la base de données.


---

## **Déploiement**
- Utilisation de Docker pour la gestion des containers (API REST, PostgreSQL, SMTP).
- Configuration des délais de validation et des paramètres de sécurité via le fichier [config.properties](src/main/resources/config.properties).

---

## **Liste des membres**
- MAHAFALIARIMBOLA Fanomezantsoa **ETU002439**
- RAKOTOARISON Aina Yohan **ETU002494**
- RAMANJATOMANITRA Ony Herilaza **ETU002541**
- RANDRIANARISOA Aro Itokiana **ETU002582**
---

Pour toute question ou suggestion, veuillez consulter le fichier TODO ou contacter l'équipe de développement.

