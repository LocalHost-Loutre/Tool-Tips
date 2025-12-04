# 📚 ToolTips Backend

Ce projet est le service backend de l'application ToolTips, développé avec Spring Boot et utilisant MySQL comme base de données.

## 🚀 Démarrage du Projet

### Prérequis
Assurez-vous d'avoir installé les éléments suivants :

- Java 25 (version recommandée pour Spring Boot 4)
- Apache Maven 
- MySQL Server

## 1. Configuration de la Base de Données (MySQL)
   Le backend nécessite une base de données MySQL nommée ToolTips avec un utilisateur dédié.

### Ouvrez un client MySQL (ou votre terminal) et exécutez les commandes SQL suivantes pour la création :

```SQL

-- Créer la base de données principale
CREATE DATABASE ToolTips;

-- Créer un utilisateur dédié
CREATE USER 'dev_user_test'@'localhost' IDENTIFIED BY 'password123';

-- Donner à cet utilisateur tous les droits sur la base de données ToolTips
GRANT ALL PRIVILEGES ON ToolTips.* TO 'dev_user_test'@'localhost';

-- Appliquer les changements de privilèges
FLUSH PRIVILEGES;

-- Vérifier que tout fonctionne
SHOW DATABASES;

-- Quitter MySQL
EXIT;
``` 
#### ⚠️ ATTENTION : Les identifiants (dev_user_test, password123) sont utilisés pour le développement local. Ne les utilisez jamais pour la production.

## 2. Configuration des Propriétés

Le projet utilise un fichier de configuration pour les détails de la base de données et la sécurité.

Créez un fichier nommé database.properties dans le répertoire src/main/resources/ (à côté de application.properties) et remplissez-le avec les informations suivantes :

```
# Configuration DataSource MariaDB/MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/ToolTips
spring.datasource.username=dev_user_test
spring.datasource.password=password123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuration JPA/Hibernate (Permet la création/mise à jour automatique des tables)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Configuration du pool de connexions HikariCP (optionnel)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000

# Configuration JWT (Clé secrète et temps d'expiration en ms)
security.jwt.secret-key=3myd76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
security.jwt.expiration-time=86400000

# Désactiver H2 console (au cas où)
spring.h2.console.enabled=false

```

## 3. Construction et Lancement
Le projet est construit et géré via Maven.

Construction (Build)
Pour compiler le projet et valider les dépendances :

```
mvn clean install
```

Lancement de l'Application

Le service est configuré pour écouter sur le port 8081.

## ⚠️ ATTENTION : Le port par défaut est le 8081. Si ce port est déjà utilisé, vous rencontrerez une erreur.

Lancez l'application via Maven :
```
mvn spring-boot:run
```

L'application est prête lorsque vous voyez le message de démarrage de Spring Boot.

## 🌐 Accès à l'API (Swagger UI):

Une fois le backend lancé, vous pouvez consulter la documentation de l'API REST via l'interface Swagger UI :

Ouvrez votre navigateur web.

Accédez à l'URL suivante :

http://localhost:8081/swagger-ui/index.html#

Cette interface vous permet d'explorer et de tester tous les endpoints disponibles.