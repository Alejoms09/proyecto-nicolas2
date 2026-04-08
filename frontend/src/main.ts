import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';

// Arranca la aplicacion Angular con la configuracion global.
bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
