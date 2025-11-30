// src/types.ts

// --- ENUMS ---
export type StatutDemande =
    | 'DRAFT'
    | 'SUBMITTED'
    | 'UNDER_REVIEW'
    | 'NEED_INFO'
    | 'APPROVED'
    | 'PARTIALLY_APPROVED'
    | 'REJECTED';

export type DecisionItem =
    | 'PENDING'
    | 'AUTO_ACCEPTED'
    | 'NEEDS_REVIEW'
    | 'ACCEPTED'
    | 'REJECTED';

export type TypeDocument =
    | 'BULLETIN'
    | 'PROGRAMME'
    | 'MOTIVATION'
    | 'AUTRE';

// --- SOUS-OBJETS ---
// Manquant précédemment : Acquis pour les UE
export interface Acquis {
    id?: string;
    description: string;
    pourcentage: number;
}

// Manquant précédemment : UE (Cours ISFCE)
export interface UE {
    id: string;
    code: string;
    ref?: string;
    nom: string;
    nbPeriodes: number;
    ects: number;
    niveau: number;
    prgm?: string;
    acquis?: Acquis[];
    sections: Section[];
}

// --- DTOs de Lecture (Ce que le serveur envoie) ---

export interface ExternalCourse {
    id: string;
    etablissement: string;
    code: string;
    libelle: string;
    ects: number;
    urlProgramme?: string;
}

export interface SupportingDocument {
    id: string;
    type: string;
    urlStockage: string;
}

export interface ExemptionItem {
    id: string;
    ueCode: string;
    ueLibelle: string;
    ueEcts: number;
    totalEctsMatches: boolean;
    decision: string;
    noteSur20?: number;
}

export interface ExemptionRequest {
    id: string;
    section: string;
    statut: StatutDemande;
    createdAt: string;
    updatedAt?: string;
    externalCourses: ExternalCourse[];
    documents: SupportingDocument[];
    items: ExemptionItem[];
}

// --- DTOs d'Écriture (Ce que tu envoies au serveur) ---

// Manquant précédemment : Pour ajouter un cours
export interface AddExternalCourseDTO {
    etablissement: string;
    code: string;
    libelle: string;
    ects: number;
    urlProgramme?: string;
}

// Manquant précédemment : Pour ajouter un document
export interface AddDocumentDTO {
    type: TypeDocument; // On force l'enum ici pour aider l'autocomplétion
    url: string;
}

export interface Section {
    code: string;
    label: string;
}

export interface CreateRequestDTO {
    section: string;
}

export interface KbSchool {
    id: number;
    code: string;
    etablissement: string;
    urlProgramme?: string;
}