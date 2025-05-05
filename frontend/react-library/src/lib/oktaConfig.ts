export const oktaConfig = {
    clientId: '0oaoldy0qqybF2e5H5d7',
    issuer: 'https://dev-55264191.okta.com/oauth2/default',
    redirectUri: 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true
}