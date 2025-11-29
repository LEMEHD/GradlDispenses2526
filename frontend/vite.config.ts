import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    server: {
        port: 3000,
        // C'est ici la magie ! 👇
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // L'adresse de ton Java
                changeOrigin: true,
                secure: false,
            }
        }
    },
})