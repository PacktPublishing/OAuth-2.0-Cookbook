## Client example with Authorization Code grant type

Notice that the main class is implementing `ServletContextInitializer`
 to redefine JSESSION ID cookie name because of domain collision between the OAuth provider running locally.
  
### Dependencies

To run this project, you must start the application located at
`chapter-2/auth-code-server`
