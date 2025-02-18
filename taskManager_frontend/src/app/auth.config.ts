export const getRedirectUri = (): string => {
  return typeof window !== 'undefined' ? window.location.origin : 'http://localhost:4200';
};

export const authConfig = {
  domain: 'dev-mfyvuhxidicv37hb.eu.auth0.com',
  clientId: '99U4ldvYAJmmRmJ41OmT2ygdh1nKDOxz',
  authorizationParams: {
    redirect_uri: getRedirectUri(),
    audience: 'https://taskmanager.api',
    scope: 'openid profile email read:tasks write:tasks delete:tasks update:tasks',
  }
};
