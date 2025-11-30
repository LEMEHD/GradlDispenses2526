import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter, Routes, Route, Link, useLocation } from 'react-router-dom';
import { Dashboard } from './pages/Dashboard';
import { CreateRequest } from './pages/CreateRequest';
import { LandingPage } from './pages/LandingPage';
import { UEList } from './pages/UEList';
import { Profile } from './pages/Profile';
// CORRECTION : Ajout de l'import manquant
import { UEDetail } from './pages/UEDetail';
import { Home } from 'lucide-react';

const queryClient = new QueryClient({
    defaultOptions: {
        queries: { retry: 1, refetchOnWindowFocus: false },
    },
});

// Petit composant pour la Navbar qui s'affiche sur toutes les pages sauf la LandingPage
const Navbar = () => {
    const location = useLocation();
    // On ne veut pas la navbar classique sur la page d'accueil car elle a sa propre bannière
    if (location.pathname === '/') return null;

    return (
        <nav className="bg-blue-900 shadow-md text-white">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between h-16">
                    <div className="flex items-center space-x-8">
                        <Link to="/" className="font-bold text-xl tracking-tight flex items-center">
                            <Home className="w-5 h-5 mr-2" /> ISFCE
                        </Link>
                        <div className="hidden md:flex space-x-4">
                            <Link to="/dashboard" className="hover:text-blue-200 transition">Mes Demandes</Link>
                            <Link to="/ues" className="hover:text-blue-200 transition">Catalogue UE</Link>
                        </div>
                    </div>
                    <div className="flex items-center">
                        <Link to="/profile" className="text-sm bg-blue-800 hover:bg-blue-700 px-3 py-1 rounded-full transition">
                            Mon Profil
                        </Link>
                    </div>
                </div>
            </div>
        </nav>
    );
};

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <div className="min-h-screen bg-gray-50 font-sans text-gray-900">
                    <Navbar />

                    {/* Sur la page d'accueil, pas de margin. Sur les autres, un peu d'espace. */}
                    <Routes>
                        <Route path="/" element={<LandingPage />} />
                    </Routes>

                    <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                        <Routes>
                            {/* Les autres routes s'affichent ici, dans le container */}
                            <Route path="/dashboard" element={<Dashboard />} />
                            <Route path="/new" element={<CreateRequest />} />
                            <Route path="/ues" element={<UEList />} />
                            <Route path="/profile" element={<Profile />} />
                            <Route path="/ue/:code" element={<UEDetail />} />
                        </Routes>
                    </main>
                </div>
            </BrowserRouter>
        </QueryClientProvider>
    );
}

export default App;