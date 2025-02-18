import { PLATFORM_ID, Provider } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthClientConfig } from '@auth0/auth0-angular';
import { authConfig } from '../auth.config';

export const authClientProvider: Provider = {
  provide: AuthClientConfig,
  useFactory: ( platformId: Object ) => {
    return new AuthClientConfig( isPlatformBrowser( platformId ) ? authConfig : {
      domain: '',
      clientId: '',
      authorizationParams: { redirect_uri: '' }
    });
  },
  deps: [PLATFORM_ID],
};
