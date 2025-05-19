# MyShop Monorepo

**Application e‑commerce full‑stack** développée avec Spring Boot (backend) et Angular + Tailwind (frontend) selon l’approche **smart–dumb**, sécurisée par **JWT**, et documentée avec **Swagger UI**.

---

## Fonctionnalités principales

* **Gestion des produits** CRUD via API REST
* **Authentification JWT** pour sécuriser l’API : création de compte (`/account`), obtention de token (`/token`)
* **Contrôle d’accès** : seul `admin@admin.com` peut créer, modifier ou supprimer un produit
* **Panier d’achats** par utilisateur (CRUD, quantités ajustables)
* **Liste d’envie** par utilisateur (ajout / suppression)
* **Filtrage & pagination** sur la liste de produits
* **Frontend Angular** :

  * Smart containers + composants dumb
  * Tailwind CSS pour le layout et le style
  * Affichage dynamique des badges et contrôles de quantité
* **Backend Spring Boot** :

  * Spring Data JPA avec base **H2 in‑memory** (DDL auto `update`)
  * Service / impl / controller dédiés
* **Documentation & tests** :

  * **Swagger UI** à `/swagger-ui/index.html`
  * Tests unitaires (JUnit5 + Mockito)
  * Tests End‑to‑End Postman

---

## 📁 Structure du dépôt

```
my-shop/
├── my-shop-backend/        # Application Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── application.yml  # config H2, JWT
│
├── my-shop-frontend/       # Application Angular standalone + Tailwind
│   ├── src/
│   ├── package.json
│   └── tailwind.config.js
│
├── .gitignore
└── README.md       # ← ce fichier
```

---

## ⚙️ Prérequis

* **Java 18**
* **Maven** (ou `./mvnw` fourni)
* **Node.js 16+** et **npm**
* **Angular CLI** (optionnel, sinon `npm run start`)
* Navigateur moderne pour Swagger UI et frontend

---

## 🚀 Installation & démarrage

### 1) Backend

```bash
cd my-shop/backend
# Configurer JWT dans application.yml :
# jwt.secret: une clé Base64 de 64 octets
# jwt.expiration-ms: durée de vie en ms (ex 3600000)

# Lancer l’application Spring Boot
./mvnw spring-boot:run
```

* API REST disponible sur `http://localhost:8080`
* Console H2 accessible à `http://localhost:8080/h2-console` (URL JDBC : `jdbc:h2:mem:myshopdb`)
* Swagger UI : `http://localhost:8080/swagger-ui/index.html`

### 2) Frontend

```bash
cd my-shop/frontend
npm install
npm run start
```

* Frontend Angular disponible sur `http://localhost:4200`

---

## 🔐 Authentification & JWT

1. Créer un compte : `POST /account`

   ```json
   {
     "username": "admin",
     "firstname": "Super",
     "email": "admin@admin.com",
     "password": "secret123"
   }
   ```
2. Obtenir un token : `POST /token`

   ```json
   {
     "email": "admin@admin.com",
     "password": "secret123"
   }
   ```

   > Réponse : `{ "token": "<JWT>" }`
3. Inclure le header : `Authorization: Bearer <JWT>`

**Règles d’accès** :

* **Public** : `/account`, `/token`, Swagger UI, H2 console
* **Protégé** : toutes les autres routes nécessitent un JWT
* **Admin only** : POST/PATCH/DELETE `/api/products` (SpEL : `principal.username=='admin@admin.com'`)

---

## 📑 Endpoints principaux

| Ressource               | Méthode | Description                                   |
| ----------------------- | ------- | --------------------------------------------- |
| `/account`              | POST    | Créer un compte utilisateur                   |
| `/token`                | POST    | Authentifier et obtenir un JWT                |
| `/api/products`         | GET     | Lister tous les produits                      |
| `/api/products`         | POST    | Créer un produit (admin only)                 |
| `/api/products/{id}`    | GET     | Détails d’un produit                          |
| `/api/products/{id}`    | PATCH   | Mise à jour partielle (admin only)            |
| `/api/products/{id}`    | DELETE  | Supprimer un produit (admin only)             |
| `/api/cart`             | GET     | Récupérer panier de l’utilisateur             |
| `/api/cart/{productId}` | POST    | Ajouter un produit au panier                  |
| `/api/cart/{productId}` | PATCH   | Mettre à jour qty d’un produit dans le panier |
| `/api/cart/{productId}` | DELETE  | Retirer un produit du panier                  |
| `/api/wishlist`         | GET     | Récupérer liste d’envie                       |
| `/api/wishlist/{id}`    | POST    | Ajouter un produit à la wishlist              |
| `/api/wishlist/{id}`    | DELETE  | Retirer un produit de la wishlist             |

---

## 🧪 Stratégie de tests

1. **Unitaires** (JUnit 5 + Mockito) pour services (`ProductServiceImpl`)
3. **E2E** (Postman/Newman ou cURL) pour dérouler les scénarios : enregistrement, login, CRUD sécurisé, panier & wishlist

---

## ⚙️ Scripts utiles

* **Backend** : `./mvnw clean verify, ./mvnw spring-boot:run`
* **Frontend** : `npm run lint` / `npm run test` / `npm run build`
* **Swagger** : `http://localhost:8080/swagger-ui/index.html`

---



---

© 2025 MyShop Ehmt Monorepo
