import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { PrimeReactProvider } from 'primereact/api';
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <PrimeReactProvider>
                 <App />
          </PrimeReactProvider>
      </BrowserRouter>
  </StrictMode>,
)
