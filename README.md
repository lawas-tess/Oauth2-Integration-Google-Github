Oauth2 Integration: Google & GitHub
This project helps you integrate Google and GitHub OAuth2 login for your Java/Spring applications.
Easily enable social login for your users with minimal setup!

Features
Sign in with Google Oauth2

Sign in with GitHub Oauth2

Automatically creates or updates user profiles using their Google/GitHub account info

Fetches primary email and avatar

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
2. Create Google and GitHub OAuth apps
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

On success, you’ll be signed in using your chosen account.

How It Works
Handles Google and GitHub login via OAuth2

Retrieves email, name, and avatar; creates/updates user profiles as needed

Special logic for GitHub (fetches email if not provided by default)

Assigns generic email format for users with missing emails

Troubleshooting
Double-check your OAuth client secrets and callback URLs

If your email isn’t returned from GitHub, the app will fetch it using the API
