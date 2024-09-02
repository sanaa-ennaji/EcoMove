# EcoMove
Travail demandé:
Développer une version console qui se contente d'implémenter les fonctionnalités du
système de partenariat en prenant en considération les précisions suivantes:
Création et gestion des profils de partenaires :
● Permettre aux administrateurs d’ajouter, de modifier et de supprimer des
partenaires de transport (compagnies aériennes, compagnies de bus, compagnies
ferroviaires, etc.).
● Chaque partenaire doit avoir un profil complet comprenant des informations telles
que :
○ id (UUID): Identifiant unique du partenaire.
○ nom_compagnie (String): Nom de la compagnie partenaire.
○ contact_commercial (String): Nom et informations de contact du
responsable commercial.
○ type_transport (Enum): Type de transport proposé (ex : avion, train, bus).
○ zone_geographique (String):Zones couvertes par le partenaire (ex : Europe,
Amérique du Nord).
○ conditions_speciales (Text): Conditions particulières du partenariat (ex :
politique d’annulation).
○ statut_partenaire (Enum): Statut du partenariat (actif, inactif, suspendu).
○ date_creation(Date)
Gestion des contrats :
● La plateforme doit permettre de gérer les contrats signés avec les compagnies
partenaires. Ces contrats peuvent inclure des conditions particulières, telles que
des quotas d'achat, des périodes promotionnelles, ou des accords sur des tarifs
réduits.
● Chaque contrat doit être associé à des données spécifiques:
○ id (UUID): Identifiant unique du contrat.
○ date_debut (Date): Date de début du contrat.
○ date_fin (Date): Date de fin du contrat (ou null si indéfinie).
○ tarif_special (Decimal): Tarif réduit ou accord spécial appliqué.
○ conditions_accord (Text): Conditions spécifiques du contrat (ex : minimum
de vente, engagement, etc.).
○ renouvelable (Boolean): Indicateur si le contrat est renouvelable
automatiquement ou non.
○ statut_contrat(Enum): Statut du contrat (en cours, terminé, suspendu).
Gestion des offres promotionnelles:
● Intégrer des fonctionnalités permettant de créer et gérer des offres spéciales en
collaboration avec les partenaires. Par exemple, des promotions saisonnières ou
des offres limitées sur certains trajets.
● Ces promotions peuvent être configurées pour s'appliquer automatiquement lors
de la sélection de certains segments de voyage.
● Une promotions est caractérisée par:
○ id (UUID): Identifiant unique de l'offre promotionnelle.
○ nom_offre (String): Nom de l'offre promotionnelle.
○ description (Text): Description de l'offre.
○ date_debut (Date): Date de début de l'offre promotionnelle.
○ date_fin (Date): Date de fin de l'offre promotionnelle.
○ type_reduction (Enum): Type de réduction (pourcentage, montant fixe,
etc.).
○ valeur_reduction (Decimal): Valeur de la réduction offerte.
○ conditions (Text): Conditions d’application de l’offre (ex : trajets spécifiques,
dates, etc.).
○ statut_offre (Enum): Statut de l'offre (active, expirée, suspendue).
Suivi des Billets Partenaires
● Permettre de suivre les billets émis pour le compte de chaque partenaire, en
gardant une trace des transactions financières et des statistiques de vente.
● Génération automatique de rapports financiers, qui montrent combien chaque
partenaire a gagné via la plateforme sur une période donnée.
● Un billet est caractérisé par:
○ id (UUID): Identifiant unique du billet vendu par un partenaire.
○ type_transport (Enum): Type de transport pour ce billet (avion, train, bus,
etc.).
○ prix_achat (Decimal): Prix d'achat du billet auprès du partenaire.
○ prix_vente (Decimal): Prix de vente du billet à l'utilisateur final.
○ date_vente (DateTime): Date et heure de la vente du billet.
○ statut_billet (Enum): Statut du billet (vendu, annulé, en attente)
Les technologies et concepts à utiliser et à connaître/Maîtriser :
● JVM, JDK, JRE
● UML
● Java POO
● Base de données postgresql
● JDBC

Critères de performance:
Classes java avec des constructeurs/setters/getters/…
Implémentation du design pattern singleton
Respect des conventions d'appellation des attribut et méthodes
Validation des données
Respect maximal des principes SOLID
Bon maitrise et compréhension des concepts implémentés

[![Typing SVG](https://readme-typing-svg.herokuapp.com/?color=E80956&size=35&center=true&vCenter=true&width=1000&lines=Hi,+I'm+Sanaa+Ennaji;I'm+a+Full+Stack+Developer+:%29)](https://git.io/typing-svg)
