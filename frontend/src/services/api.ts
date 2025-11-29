import axios from 'axios';
import type {
    ExemptionRequest,
    CreateRequestDTO,
    AddExternalCourseDTO,
    AddDocumentDTO,
    UE,
    ExternalCourse,
    SupportingDocument
} from '../types';

// Configuration de base
const apiClient = axios.create({
    baseURL: '/api', // ✅ On met juste le chemin relatif
    headers: {
        'Content-Type': 'application/json',
    },
    auth: {
        username: 'etudiant@isfce.be',
        password: 'pass'
    }
});

// Intercepteur pour voir les erreurs dans la console du navigateur
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        console.error("Erreur API:", error.response?.data || error.message);
        return Promise.reject(error);
    }
);

export const api = {
    // --- Gestion des Demandes ---

    // Créer une nouvelle demande
    createRequest: async (section: string): Promise<ExemptionRequest> => {
        const payload: CreateRequestDTO = { section };
        const response = await apiClient.post<ExemptionRequest>('/requests', payload);
        return response.data;
    },

    // Récupérer mes demandes
    getMyRequests: async (): Promise<ExemptionRequest[]> => {
        const response = await apiClient.get<ExemptionRequest[]>('/requests/mine');
        return response.data;
    },

    // Récupérer une demande par ID
    getRequestById: async (id: string): Promise<ExemptionRequest> => {
        const response = await apiClient.get<ExemptionRequest>(`/requests/${id}`);
        return response.data;
    },

    // Soumettre la demande
    submitRequest: async (id: string): Promise<ExemptionRequest> => {
        const response = await apiClient.post<ExemptionRequest>(`/requests/${id}/submit`);
        return response.data;
    },

    // --- Gestion du contenu de la demande ---

    // Utilise AddExternalCourseDTO et ExternalCourse (Supprime l'erreur TS6196)
    addCourse: async (requestId: string, data: AddExternalCourseDTO): Promise<ExternalCourse> => {
        const response = await apiClient.post<ExternalCourse>(`/requests/${requestId}/courses`, data);
        return response.data;
    },

    // Utilise AddDocumentDTO et SupportingDocument (Supprime l'erreur TS6196)
    addDocument: async (requestId: string, data: AddDocumentDTO): Promise<SupportingDocument> => {
        const response = await apiClient.post<SupportingDocument>(`/requests/${requestId}/documents`, data);
        return response.data;
    },

    // --- Catalogue UE ---

    // Utilise UE (Supprime l'erreur TS6196)
    getAllUEs: async (): Promise<UE[]> => {
        const response = await apiClient.get<UE[]>('/ue');
        return response.data;
    },

    getOneUE: async (code: string): Promise<UE> => {
        const response = await apiClient.get<UE>(`/ue/${code}`);
        return response.data;
    }
};