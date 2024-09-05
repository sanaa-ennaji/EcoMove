
CREATE DATABASE EcoMove;
\c EcoMove;
CREATE TABLE partenaires (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             nom_compagnie VARCHAR(255) NOT NULL,
                             contact_commercial VARCHAR(255),
                             type_transport VARCHAR(50) CHECK (type_transport IN ('avion', 'train', 'bus')),
                             zone_geographique TEXT,
                             conditions_speciales TEXT,
                             statut_partenaire VARCHAR(50) CHECK (statut_partenaire IN ('actif', 'inactif', 'suspendu')),
                             date_creation DATE
);

CREATE TABLE contrats (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          partenaire_id UUID REFERENCES partenaires(id) ON DELETE CASCADE,
                          date_debut DATE,
                          date_fin DATE,
                          tarif_special DECIMAL,
                          conditions_accord TEXT,
                          renouvelable BOOLEAN,
                          statut_contrat VARCHAR(50) CHECK (statut_contrat IN ('enCours', 'termine', 'suspendu'))
);

CREATE TABLE promotions (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            nom_offre VARCHAR(255),
                            description TEXT,
                            date_debut DATE,
                            date_fin DATE,
                            type_reduction VARCHAR(50) CHECK (type_reduction IN ('pourcentage', 'montant fixe')),
                            valeur_reduction DECIMAL,
                            conditions TEXT,
                            statut_offre VARCHAR(50) CHECK (statut_offre IN ('active', 'expiree', 'suspendue'))
);

CREATE TABLE billets (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         type_transport VARCHAR(50) CHECK (type_transport IN ('avion', 'train', 'bus')),
                         prix_achat DECIMAL,
                         prix_vente DECIMAL,
                         date_vente TIMESTAMP,
                         statut_billet VARCHAR(50) CHECK (statut_billet IN ('vendu', 'annule', 'en attente')),
                         contrat_id UUID REFERENCES contrats(id) ON DELETE CASCADE,
);
