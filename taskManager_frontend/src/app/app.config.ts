import { PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { AuthConfig } from '@auth0/auth0-angular';
import { authConfig } from './auth.config';

export function provideAuthConfig(): AuthConfig {
  const platformId = inject( PLATFORM_ID );
  return isPlatformBrowser( platformId )
    ? authConfig
    : { domain: '', clientId: '', authorizationParams: { redirect_uri: '' } };
}
