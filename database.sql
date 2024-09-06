
CREATE DATABASE EcoMove;
\c EcoMove;
CREATE TABLE partenaires (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             nomCompagnie VARCHAR(255) NOT NULL,
                             contactCommercial VARCHAR(255),
                             typeTransport VARCHAR(50) CHECK (typeTransport IN ('avion', 'train', 'bus')),
                             zoneGeographique TEXT,
                             conditionsSpeciales TEXT,
                             statutPartenaire VARCHAR(50) CHECK (statutPartenaire IN ('actif', 'inactif', 'suspendu')),
                             dateCreation DATE
);

CREATE TABLE contrats (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          partenaire_id UUID REFERENCES partenaires(id) ON DELETE CASCADE,
                          dateDebut DATE,
                          dateFin DATE,
                          tarifSpecial DECIMAL,
                          conditionsAccord TEXT,
                          renouvelable BOOLEAN,
                          statutContrat VARCHAR(50) CHECK (statutContrat IN ('encours', 'termine', 'suspendu'))
);

CREATE TABLE promotions (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            nomOffre VARCHAR(255),
                            description TEXT,
                            dateDebut DATE,
                            dateFin DATE,
                            typeReduction VARCHAR(50) CHECK (typeReduction IN ('pourcentage', 'montant fixe')),
                            valeurReduction DECIMAL,
                            conditions TEXT,
                            statutOffre VARCHAR(50) CHECK (statutOffre IN ('active', 'expiree', 'suspendue'))
);

CREATE TABLE billets (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         typeTransport VARCHAR(50) CHECK (typeTransport IN ('avion', 'train', 'bus')),
                         prixAchat DECIMAL,
                         prixVente DECIMAL,
                         dateVente TIMESTAMP,
                         statutBillet VARCHAR(50) CHECK (statutBillet IN ('vendu', 'annule', 'en attente')),
                         contrat_id UUID REFERENCES contrats(id) ON DELETE CASCADE,
);
