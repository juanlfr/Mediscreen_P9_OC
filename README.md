## Mediscreen app

C'est un outil pour aider nos médecins à identifier les patients les
plus à risques de diabètes, en réalisant un suivi et en generant un 
rapport selon plusieurs critères du dossier du patient

### Prerequisites

* Maven 3.X.X
* Docker

### Installation

1.  Cloner le repo
   ```sh
   git clone https://github.com/juanlfr/Mediscreen_P9_OC.git
   ```
2. Pour avoir les jar des microservices exécuter:
   ```sh
   mvn clean package -DskipTests
   ```
3. Docker
   ```sh
   docker-compose up -d
   ```
### Utilisation

Ouvrir le navigateur http://localhost:8080/ et explorer l'interface avec ses différentes fonctionnalités
