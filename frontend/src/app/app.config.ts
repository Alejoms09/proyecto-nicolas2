import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';

// Configuracion global de la aplicacion Angular.
export const appConfig: ApplicationConfig = {
  providers: [
    // Manejo global de errores del navegador.
    provideBrowserGlobalErrorListeners(),
    // Cliente HTTP para consumir backend.
    provideHttpClient()
  ]
};
