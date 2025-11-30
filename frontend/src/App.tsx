import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom'; // J'ai retiré useLocation car on affiche la nav partout
import { Dashboard } from './pages/Dashboard';
import { CreateRequest } from './pages/CreateRequest';
import { LandingPage } from './pages/LandingPage';
import { UEList } from './pages/UEList';
import { Profile } from './pages/Profile';
import { UEDetail } from './pages/UEDetail';
import { UserCircle } from 'lucide-react'; // Nouvelle icône pour le profil

const queryClient = new QueryClient({
    defaultOptions: {
        queries: { retry: 1, refetchOnWindowFocus: false },
    },
});

const Navbar = () => {
    return (
        <nav className="bg-blue-900 text-white shadow-lg sticky top-0 z-50">
            {/* Modification : w-full et px-6 pour occuper plus d'espace horizontal */}
            <div className="w-full px-6 lg:px-8">
                <div className="flex justify-between h-20 items-center">

                    {/* Partie Gauche : Logo et Nom */}
                    <div className="flex items-center space-x-8">
                        <Link to="/" className="flex items-center space-x-3 group">
                            {/* LOGO DE L'ÉCOLE */}
                            <img
                                src="/images/logo_isfce.png"
                                alt="Logo ISFCE"
                                className="h-12 w-auto rounded p-1 transition-transform group-hover:scale-105"
                            />
                            <div className="flex flex-col">
                                <span className="font-bold text-xl tracking-tight leading-none">ISFCE</span>
                                <span className="text-xs text-blue-200 font-light">Portail des Dispenses</span>
                            </div>
                        </Link>

                        {/* Liens de navigation principaux */}
                        <div className="hidden md:flex space-x-6 ml-8">
                            <Link to="/dashboard" className="text-sm font-medium hover:text-blue-200 transition border-b-2 border-transparent hover:border-blue-400 py-1">
                                Mes Demandes
                            </Link>
                            <Link to="/ues" className="text-sm font-medium hover:text-blue-200 transition border-b-2 border-transparent hover:border-blue-400 py-1">
                                Catalogue UE
                            </Link>
                        </div>
                    </div>

                    {/* Partie Droite : Profil */}
                    <div className="flex items-center">
                        <Link to="/profile" className="flex items-center space-x-2 bg-blue-800 hover:bg-blue-700 px-4 py-2 rounded-full transition shadow-sm border border-blue-700">
                            <span className="text-sm font-medium">Mon Profil</span>
                            <UserCircle className="w-5 h-5" />
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
                <div className="min-h-screen bg-gray-50 font-sans text-gray-900 flex flex-col">
                    <Navbar />

                    {/* Le main prend toute la largeur aussi, avec un peu de padding */}
                    <div className="flex-grow w-full">
                        <Routes>
                            <Route path="/" element={<LandingPage />} />

                            {/* Wrapper pour les autres pages pour garder un peu de marge */}
                            <Route path="*" element={
                                <div className="max-w-[95%] mx-auto px-4 py-8">
                                    <Routes>
                                        <Route path="/dashboard" element={<Dashboard />} />
                                        <Route path="/new" element={<CreateRequest />} />
                                        <Route path="/ues" element={<UEList />} />
                                        <Route path="/profile" element={<Profile />} />
                                        <Route path="/ue/:code" element={<UEDetail />} />
                                    </Routes>
                                </div>
                            } />
                        </Routes>
                    </div>
                </div>
            </BrowserRouter>
        </QueryClientProvider>
    );
}

export default App;