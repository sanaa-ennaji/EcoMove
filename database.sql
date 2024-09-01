-- enums

CREATE TYPE typeTransport AS ENUM ('avion', 'train', 'bus');
CREATE TYPE statutPartenaire AS ENUM ('actif', 'inactif', 'suspendu');
CREATE TYPE statutContrat AS ENUM ('enCours', 'termine', 'suspendu');
CREATE TYPE typeReduction AS ENUM ('pourcentage', 'montant fixe');
CREATE TYPE statutOffre AS ENUM ('active', 'expirée', 'suspendue');
CREATE TYPE statutBillet AS ENUM ('vendu', 'annule', 'en attente');


CREATE TABLE partenaires (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             nomCompagnie VARCHAR(255) NOT NULL,
                             contactCommercial VARCHAR(255),
                             typeTransport type_transport,
                             zoneGeographique TEXT,
                             conditionsSpeciales TEXT,
                             statutPartenaire statutPartenaire,
                             dateCreation DATE
);
CREATE TABLE contrats (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          partenaireId UUID REFERENCES partenaires(id) ON DELETE CASCADE,
                          dateDebut DATE,
                          dateFin DATE,
                          tarifSpecial DECIMAL,
                          conditions_accord TEXT,
                          renouvelable BOOLEAN,
                          statutContrat statutContrat
);

CREATE TABLE promotions (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                        nomOffre VARCHAR(255),
                                        description TEXT,
                                        dateDebut DATE,
                                        dateFin DATE,
                                        typeReduction typeReduction,
                                        valeurReduction DECIMAL,
                                        conditions TEXT,
                                        statutOffre statutOffre
);

CREATE TABLE billets (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         typeTransport typeTransport,
                         prixAchat DECIMAL,
                         prixVente DECIMAL,
                         dateVente TIMESTAMP,
                         statutBillet statutBillet,
                         contratId UUID REFERENCES contrats(id) ON DELETE CASCADE,
                         offreId UUID REFERENCES promotions(id) ON DELETE SET NULL
);