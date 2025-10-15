Oauth2 Integration: Google & GitHub
Smooth handling even if emails are missing (especially with GitHub)

Getting Started
Prerequisites
Java 17+
Maven
Google Cloud Console account
GitHub OAuth App

Setup
1. Clone this repo
bash
git clone https://github.com/lawas-tess/Oauth2-Integration-Google-Github.git
cd Oauth2-Integration-Google-Github

2.Create Google and GitHub OAuth apps
Google:
Go to Google Cloud Console, create OAuth credentials, and get your client ID and secret.
GitHub:
Go to GitHub Settings > Developer settings > OAuth Apps, register a new app, and copy your client ID and secret.

3. Configure your application.properties or application.yml
Add your Google/GitHub client ID and secret:
text
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET

4. Run the application
bash
mvn spring-boot:run

5. Access and Test
Visit http://localhost:8080 and click Login with Google or Login with GitHub
On success, you'll be signed in using your chosen account.

## Architecture

This section provides a text-based diagram describing the OAuth2 integration flow:

```
┌──────┐      ┌─────────────┐      ┌──────────────────┐      ┌────────────────────┐
│ User │─────>│ Web Browser │─────>│ Spring Boot App  │─────>│ OAuth2 Providers   │
└──────┘      └─────────────┘      └──────────────────┘      │ - Google           │
    ^                ^                      ^                 │ - GitHub           │
    │                │                      │                 └────────────────────┘
    │                │                      │                          │
    │                │                      │<─────────────────────────┘
    │                │                      │
    │                │                      v
    │                │              ┌──────────────────┐
    └────────────────┴──────────────│ User Profile     │
                                    │ Module           │
                                    └──────────────────┘
```

### Component Descriptions:

1. **User**: The end-user who wants to authenticate using OAuth2
   - Initiates the login process
   - Receives the final authentication result and user profile

2. **Web Browser**: The client interface
   - Displays the login page with OAuth2 provider options (Google/GitHub)
   - Handles redirects to/from OAuth2 providers
   - Presents the authenticated user profile

3. **Spring Boot App**: The main application server
   - Receives login requests from the browser
   - Redirects users to selected OAuth2 provider (Google or GitHub)
   - Handles OAuth2 callback with authorization code
   - Exchanges authorization code for access token
   - Retrieves user information from OAuth2 provider
   - Processes and stores user data

4. **OAuth2 Providers (Google and GitHub)**:
   - **Google**: Provides authentication and user profile data (email, name, avatar)
   - **GitHub**: Provides authentication and user profile data (may require additional API call for email)
   - Both validate user credentials and return authorization codes/tokens

5. **User Profile Module**: Data management component
   - Creates new user profiles for first-time logins
   - Updates existing user profiles on subsequent logins
   - Handles missing email scenarios (especially for GitHub users)
   - Assigns generic email format when email is unavailable
   - Stores user information (email, name, avatar, provider)

### Authentication Flow:

1. User clicks login button in Web Browser
2. Browser sends request to Spring Boot App
3. Spring Boot App redirects to selected OAuth2 Provider (Google or GitHub)
4. OAuth2 Provider authenticates user and returns authorization code
5. Spring Boot App receives callback with authorization code
6. Spring Boot App exchanges code for access token with OAuth2 Provider
7. Spring Boot App retrieves user information using access token
8. Spring Boot App passes user data to User Profile Module
9. User Profile Module creates or updates user profile
10. Spring Boot App returns authenticated session to Web Browser
11. Web Browser displays user profile to User

How It Works
Handles Google and GitHub login via OAuth2
Retrieves email, name, and avatar; creates/updates user profiles as needed
Special logic for GitHub (fetches email if not provided by default)
Assigns generic email format for users with missing emails

Troubleshooting
Double-check your OAuth client secrets and callback URLs
If your email isn't returned from GitHub, the app will fetch it using the API
