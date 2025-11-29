import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import { ArrowLeft, Save } from 'lucide-react';

export const CreateRequest = () => {
    const [section, setSection] = useState(''); // Pour stocker ce que l'utilisateur tape
    const [isSubmitting, setIsSubmitting] = useState(false); // Pour gérer l'état "chargement"
    const navigate = useNavigate(); // Pour changer de page automatiquement

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // Empêche la page de se recharger

        if (!section.trim()) return; // On ne fait rien si c'est vide

        setIsSubmitting(true);
        try {
            // 1. On appelle l'API Java
            await api.createRequest(section);

            // 2. Si ça marche, on retourne au tableau de bord
            navigate('/dashboard');
        } catch (error) {
            alert("Erreur lors de la création du dossier !");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="max-w-2xl mx-auto space-y-6">
            {/* Bouton retour */}
            <button
                onClick={() => navigate(-1)}
                className="text-gray-500 hover:text-gray-700 flex items-center transition"
            >
                <ArrowLeft className="w-4 h-4 mr-1" />
                Retour
            </button>

            <div className="bg-white p-8 rounded-xl shadow-sm border border-gray-200">
                <h1 className="text-2xl font-bold text-gray-900 mb-6">
                    Commencer un nouveau dossier
                </h1>

                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <label htmlFor="section" className="block text-sm font-medium text-gray-700 mb-1">
                            Section d'études
                        </label>
                        <input
                            type="text"
                            id="section"
                            placeholder="Ex: Bachelier en Informatique de Gestion"
                            value={section}
                            onChange={(e) => setSection(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition"
                            required
                        />
                        <p className="mt-2 text-sm text-gray-500">
                            Indiquez la section dans laquelle vous souhaitez demander des dispenses.
                        </p>
                    </div>

                    <button
                        type="submit"
                        disabled={isSubmitting}
                        className={`w-full flex justify-center items-center px-4 py-3 rounded-lg text-white font-medium transition
              ${isSubmitting
                            ? 'bg-indigo-400 cursor-not-allowed'
                            : 'bg-indigo-600 hover:bg-indigo-700 shadow-sm'
                        }`}
                    >
                        {isSubmitting ? (
                            <span>Création en cours...</span>
                        ) : (
                            <>
                                <Save className="w-4 h-4 mr-2" />
                                Créer le dossier
                            </>
                        )}
                    </button>
                </form>
            </div>
        </div>
    );
};