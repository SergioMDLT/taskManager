import { PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { authConfig } from './auth.config';
import { AuthConfig } from '@auth0/auth0-angular';

export function provideAuthConfig(): AuthConfig {
  const platformId = inject( PLATFORM_ID );
  return isPlatformBrowser( platformId )
    ? authConfig
    : { domain: '', clientId: '', authorizationParams: { redirect_uri: '' } };
}
