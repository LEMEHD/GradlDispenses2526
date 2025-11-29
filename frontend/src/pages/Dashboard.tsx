import { useQuery } from '@tanstack/react-query';
import { api } from '../services/api';
import { Link } from 'react-router-dom';
import { Plus, FileText, Calendar, AlertCircle } from 'lucide-react';
import type { StatutDemande } from '../types';

// Petite fonction pour afficher un joli badge selon le statut
const getStatusBadge = (status: StatutDemande) => {
    const styles: Record<string, string> = {
        DRAFT: 'bg-gray-100 text-gray-800 border-gray-200',
        SUBMITTED: 'bg-blue-50 text-blue-700 border-blue-200',
        UNDER_REVIEW: 'bg-yellow-50 text-yellow-700 border-yellow-200',
        NEED_INFO: 'bg-orange-50 text-orange-700 border-orange-200',
        APPROVED: 'bg-green-50 text-green-700 border-green-200',
        PARTIALLY_APPROVED: 'bg-teal-50 text-teal-700 border-teal-200',
        REJECTED: 'bg-red-50 text-red-700 border-red-200',
    };

    const labels: Record<string, string> = {
        DRAFT: 'Brouillon',
        SUBMITTED: 'Soumis',
        UNDER_REVIEW: 'En cours',
        NEED_INFO: 'Info requise',
        APPROVED: 'Approuvé',
        PARTIALLY_APPROVED: 'Partiellement Approuvé',
        REJECTED: 'Rejeté',
    };

    return (
        <span className={`px-3 py-1 rounded-full text-xs font-medium border ${styles[status] || styles.DRAFT}`}>
      {labels[status] || status}
    </span>
    );
};

export const Dashboard = () => {
    // C'est ici la magie ! React Query appelle l'API et gère le chargement tout seul.
    const { data: requests, isLoading, isError } = useQuery({
        queryKey: ['myRequests'],
        queryFn: api.getMyRequests
    });

    if (isLoading) return (
        <div className="flex justify-center p-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
        </div>
    );

    if (isError) return (
        <div className="bg-red-50 p-4 rounded-lg flex items-center text-red-700 border border-red-200">
            <AlertCircle className="w-5 h-5 mr-2" />
            Impossible de charger vos demandes. Vérifiez que le backend Java tourne bien sur le port 8080.
        </div>
    );

    return (
        <div className="space-y-6">
            {/* En-tête avec bouton Créer */}
            <div className="flex justify-between items-center">
                <div>
                    <h1 className="text-2xl font-bold text-gray-900">Mes Demandes</h1>
                    <p className="text-gray-500 mt-1">Gérez vos dossiers de dispenses</p>
                </div>
                <Link
                    to="/new"
                    className="inline-flex items-center px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition shadow-sm font-medium"
                >
                    <Plus className="w-4 h-4 mr-2" />
                    Nouvelle Demande
                </Link>
            </div>

            {/* Si aucune demande n'existe */}
            {requests?.length === 0 && (
                <div className="text-center py-12 bg-white rounded-xl shadow-sm border border-gray-200">
                    <div className="bg-indigo-50 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                        <FileText className="w-8 h-8 text-indigo-600" />
                    </div>
                    <h3 className="text-lg font-medium text-gray-900">Aucune demande</h3>
                    <p className="text-gray-500 mt-1">Commencez par créer votre premier dossier.</p>
                </div>
            )}

            {/* Liste des demandes (Cartes) */}
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {requests?.map((req) => (
                    <Link
                        key={req.id}
                        to={`/request/${req.id}`}
                        className="block bg-white p-6 rounded-xl shadow-sm border border-gray-200 hover:shadow-md hover:border-indigo-300 transition group"
                    >
                        <div className="flex justify-between items-start mb-4">
                            {getStatusBadge(req.statut)}
                            <span className="text-xs text-gray-400 font-mono">#{req.id.substring(0, 8)}</span>
                        </div>

                        <h3 className="font-semibold text-lg text-gray-900 group-hover:text-indigo-600 transition">
                            Section {req.section}
                        </h3>

                        <div className="mt-4 pt-4 border-t border-gray-100 flex items-center text-sm text-gray-500">
                            <Calendar className="w-4 h-4 mr-2" />
                            {new Date(req.createdAt).toLocaleDateString('fr-FR', {
                                day: 'numeric', month: 'long', year: 'numeric'
                            })}
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
};