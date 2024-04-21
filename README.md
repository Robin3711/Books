## Presentation du Projet : 

Le projet consiste en trois applications : 

- API en Node.js 
- client web en React
- client mobile avec Android Studio Java

l'objectif était de pouvoir manipuler des auteurs et des livres stockés dans une base de donnée depuis un naviguateur ou un smartphone. 

### API / Application côté serveur :

#### présentation :

serveur HTTP qui expose des routes (une API) permettant à des clients de manipuler, au travers de requêtes HTTP, des données stockées dans une base de données.
L'environnement de développement utilisé est Node.js avec le langage TypeScript.

Les bibliothèques principales utilisées sont :

- `express` pour créer le serveur HTTP, déclarer et définir ses routes
- `Prisma` pour gérer les données en lien avec la base de données

#### structure :

- `index.ts` contient les routes utilisables par les clients
- le répertoire `requestHandlers` contient des fichiers typescript ( un par table de la base ) avec les fonctions pour lire, modifier, créer ou supprimer des lignes dans les tables.
- le répertoire `prisma` contient le shéma de la base, ainsi que le script de seeding et ceux de migrations.
- le répertoire `validation` contient les constantes utilisées pour les comparaisons des entrées via `superstruct`.

### client Web : 

#### présentation : 

client HTTP qui intéragit avec l'API pour pouvoir manipuler les données stockées dans la base de données en envoyant des requêtes à l'API depuis un naviguateur web.
nous avons pour cela utilisé la bibliothèque React avec le langage TypeScript à nouveau.

la bibliothèque principale utilisées est `React Router` pour naviguer dans des routes côté client.

#### structure : 

- `main.tsx` déclare les routes utilisées dans l'application.
- `api.tsx` contient tout les appels à l'API. ce sont ensuite les fonctions de cette classe qui sont utilisées pour effectuer des requêtes à l'API.
- le répertoire `routes` contients les fichiers décrivant les pages de l'application auquelles mènent les routes.

### client Android : 

#### présentation :

application mobile développé en Java avec Android Studio pour pouvoir manipuler les donnés stockées dans la base de données depuis un smartphone.

#### structure : 

l'architecture de l'application est un `view-model-viewmodel` avec :

- `view` étant les fragments affichés sur l'écran de l'utilisateur.
- `model` les class décrivantes les données récupérée à l'API.
- `viewmodel` les viewmodels utilisé pour stockés les données.
- `db` s'ajoute par dessus pour communiquer avec l'API.
  




