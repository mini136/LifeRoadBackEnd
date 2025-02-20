# LifeRoad Database API

##  Popis projektu

LifeRoad Database API je backendová aplikace napsaná ve Spring Boot, která poskytuje REST API pro správu uživatelských účtů, záznamy nálad uživatelů a správu "Fun Facts".

---

##  Instalace a spuštění

### Požadavky

- **Java 17+**
- **Maven 3.8+**
- **MySQL nebo jiná databáze**
- **Git**

### Klonování repozitáře

```sh
git clone https://github.com/uzivatel/liferoad-database-api.git
cd liferoad-database-api
```

### Nastavení databáze

Konfigurace se nachází v **`application.properties`**:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/liferoad_db
spring.datasource.username=root
spring.datasource.password=heslo
```
**Nezapomeň vytvořit databázi ****`liferoad_db`**** před spuštěním!**

### ️ Spuštění aplikace

```sh
mvn spring-boot:run
```

 Aplikace poběží na **`http://localhost:8080`**

---

##  API Endpoints

### ** Autentizace (****`/auth`****)**

| Metoda | Endpoint         | Popis                |
| ------ | ---------------- | -------------------- |
| `POST` | `/auth/register` | Registrace uživatele |
| `POST` | `/auth/login`    | Přihlášení uživatele |

### ** Správa Fun Facts (****`/api/funfacts`****)**

| Metoda | Endpoint        | Popis                   |
| ------ | --------------- | ----------------------- |
| `GET`  | `/api/funfacts` | Získání všech Fun Facts |
| `POST` | `/api/funfacts` | Přidání nového Fun Fact |

### ** Správa nálad (****`/api/mood`****)**

| Metoda | Endpoint    | Popis                              |
| ------ | ----------- | ---------------------------------- |
| `POST` | `/api/mood` | Uložit náladu (vyžaduje JWT token) |
| `GET`  | `/api/mood` | Získání seznamu nálad uživatele    |

---

##  Autorizace pomocí JWT

Některé API endpointy vyžadují **JWT token**. Po přihlášení získáš token, který je potřeba přidat do hlavičky `Authorization`:

```sh
Authorization: Bearer {tvuj-token}
```

---

##  Technologie

- **Spring Boot 3.0** (REST API, Security, JPA)
- **Spring Security + JWT** (autentizace a autorizace)
- **MySQL** (databáze)
- **Maven** (správa závislostí)

---

