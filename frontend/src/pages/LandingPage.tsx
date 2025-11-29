import { Link } from 'react-router-dom';
import { FileText, PlusCircle, BookOpen, User, ArrowRight, GraduationCap } from 'lucide-react';

export const LandingPage = () => {
    return (
        <div className="min-h-screen bg-gray-50">
            {/* Hero Section (Bannière style ISFCE) */}
            <div className="bg-blue-900 text-white py-16">
                <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="flex items-center space-x-4 mb-6">
                        <div className="p-3 bg-white/10 rounded-lg">
                            <GraduationCap className="w-12 h-12 text-white" />
                        </div>
                        <div>
                            <h1 className="text-4xl font-bold tracking-tight">Portail Étudiant</h1>
                            <p className="text-blue-200 mt-1 text-lg">Institut Supérieur de Formation Continue d'Etterbeek</p>
                        </div>
                    </div>
                    <p className="max-w-2xl text-blue-100 text-lg">
                        Bienvenue sur votre plateforme de gestion des dispenses. Gerez vos demandes, consultez le programme des cours et suivez votre parcours académique.
                    </p>
                </div>
            </div>

            {/* Grille de Navigation */}
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 -mt-8">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">

                    {/* Carte : Mes Demandes */}
                    <Link to="/dashboard" className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1 group border-t-4 border-indigo-500">
                        <div className="flex justify-between items-start mb-4">
                            <div className="p-3 bg-indigo-50 rounded-lg group-hover:bg-indigo-100 transition">
                                <FileText className="w-6 h-6 text-indigo-600" />
                            </div>
                            <ArrowRight className="w-5 h-5 text-gray-300 group-hover:text-indigo-500 transition" />
                        </div>
                        <h3 className="text-lg font-bold text-gray-900 mb-2">Mes Demandes</h3>
                        <p className="text-sm text-gray-500">Suivre l'état de vos dossiers de dispenses en cours.</p>
                    </Link>

                    {/* Carte : Nouvelle Demande */}
                    <Link to="/new" className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1 group border-t-4 border-green-500">
                        <div className="flex justify-between items-start mb-4">
                            <div className="p-3 bg-green-50 rounded-lg group-hover:bg-green-100 transition">
                                <PlusCircle className="w-6 h-6 text-green-600" />
                            </div>
                            <ArrowRight className="w-5 h-5 text-gray-300 group-hover:text-green-500 transition" />
                        </div>
                        <h3 className="text-lg font-bold text-gray-900 mb-2">Nouvelle Demande</h3>
                        <p className="text-sm text-gray-500">Introduire un nouveau dossier pour une section.</p>
                    </Link>

                    {/* Carte : Liste des UE */}
                    <Link to="/ues" className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1 group border-t-4 border-blue-500">
                        <div className="flex justify-between items-start mb-4">
                            <div className="p-3 bg-blue-50 rounded-lg group-hover:bg-blue-100 transition">
                                <BookOpen className="w-6 h-6 text-blue-600" />
                            </div>
                            <ArrowRight className="w-5 h-5 text-gray-300 group-hover:text-blue-500 transition" />
                        </div>
                        <h3 className="text-lg font-bold text-gray-900 mb-2">Liste des UE</h3>
                        <p className="text-sm text-gray-500">Consulter le catalogue des cours et les ECTS associés.</p>
                    </Link>

                    {/* Carte : Mon Profil */}
                    <Link to="/profile" className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl transition transform hover:-translate-y-1 group border-t-4 border-orange-500">
                        <div className="flex justify-between items-start mb-4">
                            <div className="p-3 bg-orange-50 rounded-lg group-hover:bg-orange-100 transition">
                                <User className="w-6 h-6 text-orange-600" />
                            </div>
                            <ArrowRight className="w-5 h-5 text-gray-300 group-hover:text-orange-500 transition" />
                        </div>
                        <h3 className="text-lg font-bold text-gray-900 mb-2">Mon Profil</h3>
                        <p className="text-sm text-gray-500">Gérer vos informations personnelles et académiques.</p>
                    </Link>

                </div>
            </div>

            {/* Footer simple */}
            <footer className="mt-20 border-t border-gray-200 py-8 text-center text-gray-500 text-sm">
                &copy; {new Date().getFullYear()} ISFCE - Tous droits réservés.
            </footer>
        </div>
    );
};