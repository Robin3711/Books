### P42 : rapport :

##### description du projet :

- Nous avons choisi d'utilisé une architecture view-model-viewmodel.
- L'architecture globale est basé sur l'approche Modern Android Developpement, avec une seul activitée principale `MainActivity` contenant un `FragmentContainer`.
- la naviguation à lieu grâce à un `BottomNavigationView` et à un graphe de naviguation.
- la connexion à l'API est effectué grâce à Volley et à trois Repository : `AuthorRepositor`, `BookRepository` et `TagRepository`, ainsi qu'avec le singleton `VolleyQueueRequest`.
- Le modele est constitué de 5 classes représententes les 5 type de données manipulées par l'API : `Book`,`Author`,`Tag`, `Comment` et `Rating`.
- les`viewModel` sont aux nombres 7 : 1 pour stocker une note, 1 pour stocker un commentaire, 1 pour stocker la liste de tout les tags, et 1 version individuelle et 1 version en liste d'auteur et de livres.
- les vues sont les differents fragments d'ajouts ou de parcours de livres et d'auteurs, comprenant 2 `recyclerView`, et les `Holder` et `Adapter` qui vont avec.


##### ce qui marche : 

On peut : 

- parcourir la liste des livres et celle des auteurs.
- filtrer les livres par titre, auteur et tag et les trier par titre ou date de publication.
- accéder au titre, à l'auteur, à l'année de publication, à la note moyenne, à la liste des tags et à celle des commentaires d'un livre.
- accéder aux nom et prénom d'un auteur, et à sa liste des livres.
- créer un livre en choisissant son titre, son auteur parmis ceux dans la base, sa date de publication et un tag.
- créer un auteur en choisissant son nom et son prénom.
- supprimer un livre ou un auteur.
- ajouter un commentaire ou une évaluation à un livre.
- depuis la page d'un livre, naviguer vers celle de son auteur et inversement, créer un livre depuis la page de son auteur.


##### ce qui marche pas : 

##### remarques et difficultées rencontrées :
