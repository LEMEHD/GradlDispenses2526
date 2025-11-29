import { useQuery } from '@tanstack/react-query';
import { api } from '../services/api';
// On garde BookOpen car on va l'utiliser
import { BookOpen, Search } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useState } from 'react';

export const UEList = () => {
    const [searchTerm, setSearchTerm] = useState('');

    const { data: ues, isLoading } = useQuery({
        queryKey: ['ues'],
        queryFn: api.getAllUEs
    });

    const filteredUes = ues?.filter(ue =>
        ue.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
        ue.code.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <div>
                    {/* MODIFICATION ICI : On ajoute l'icône BookOpen dans le titre */}
                    <h1 className="text-2xl font-bold text-gray-900 flex items-center">
                        <BookOpen className="w-8 h-8 mr-3 text-indigo-600" />
                        Catalogue des Cours (UE)
                    </h1>
                    <p className="text-gray-500 mt-1">Liste officielle des Unités d'Enseignement</p>
                </div>
                <Link to="/" className="text-indigo-600 hover:text-indigo-800 font-medium">
                    Retour à l'accueil
                </Link>
            </div>

            <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                <input
                    type="text"
                    placeholder="Rechercher un cours par nom ou code..."
                    className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none shadow-sm"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>

            {isLoading ? (
                <div className="flex justify-center py-12">
                    <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
                </div>
            ) : (
                <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Code</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nom de l'UE</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Périodes</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ECTS</th>
                        </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                        {filteredUes?.map((ue) => (
                            <tr key={ue.id} className="hover:bg-gray-50 transition-colors">
                                <td className="px-6 py-4 whitespace-nowrap font-mono text-sm text-indigo-600 font-medium">{ue.code}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{ue.nom}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{ue.nbPeriodes} p.</td>
                                <td className="px-6 py-4 whitespace-nowrap">
                    <span className="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                      {ue.ects} ECTS
                    </span>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};