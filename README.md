Projet ABC - Android Books Client
=================================

L'objectif de ce projet est de réaliser un client mobile pour l'API Books développée dans le module W41, dont [une correction minimale est disponible ici](https://git.unistra.fr/w412/w41/-/tree/main/correction).

Préparation du dépôt
--------------------

1. Créez un groupe Gitlab nommé **`nom1-nom2`** où `nom1` et `nom2` sont les noms de famille des deux membres du binôme.
1. Forkez le dépôt https://git.unistra.fr/p42/p42-abc dans le groupe créé ci-dessus **en conservant l'URL p42-abc**.
1. **Passez ce dépôt en privé.**
1. Ajoutez votre enseignant de TD/TP comme Reporter de votre dépôt.

Rendu
-----

- Quand ? **le 8 avril à 0h00** (ou le 7 avril à 24h00, au choix)
- Où ? Sur votre dépôt Git, fork de https://git.unistra.fr/p42/abc
- Quoi ?
    - Le code de l'application
    - Un rapport expliquant votre architecture, ce qui est censé fonctionner et ne pas fonctionner, les difficultés que vous avez rencontré.

Fonctionnalités
---------------

L'application doit proposer un certain nombre de fonctionnalités :

- **Afficher la liste des livres** : lorsqu'un livre est sélectionné, la description de ce livre doit s'afficher, dont les tags, la note moyenne et les commentaires.
- **Afficher la liste des auteurs** : lorsqu'un auteur est sélectionné, la liste des titres des livres qu'il a écrit doit s'afficher. Un clic sur l'un des livres doit afficher ses détails.
- **Créer un livre** à partir d'un formulaire
- **Supprimer un livre** depuis sa page de description
- **Créer un auteur** à partir d'un formulaire
- **Supprimer un auteur** (et les livres associés) depuis sa page de description

Bonus : L'application peut permettre d'**associer une couverture à un livre**, mais uniquement localement. L'image est stockée sur le téléphone, pas sur le serveur.

Interface
---------

1. L'application est composée d'**une seule activité principale**. Cette activité contiendra une **[`Bottom Navigation Activity`](https://developer.android.com/studio/projects/templates#BottomNavActivity)**.
1. L'écran d'accueil de l'application affiche la liste des livres.
1. Les listes des livres et des auteurs sont affichées dans des **`RecyclerView`**.
1. Lors d'un **clic sur un livre**, ses informations sont affichées dans un nouveau fragment.
1. Lors d'un **clic sur un auteur**, les livres de cet auteur sont affichés dans un nouveau fragment.
1. La **création des livres et des auteurs** doit être proposée à partir de [FABs](https://developer.android.com/develop/ui/views/components/floating-action-button) présents respectivement sur la liste des livres et des auteurs.

Pour gérer les **clics sur les items d'un `RecyclerView`**, vous pouvez vous référer à [ce site](https://www.codexpedia.com/android/defining-item-click-listener-for-recyclerview-in-android/) ou [celui-ci](https://dev.to/theplebdev/adding-onclicklistener-to-recyclerview-in-android-3amb).

Conseils
--------

- Travaillez dans un premier temps avec **des données "en dur"**, sans communication avec l'API
- C'est un projet assez conséquent, l'essentiel n'est pas d'implémenter toutes les fonctionnalités mais d'**implémenter "proprement"** celles que vous aurez le temps de faire.
