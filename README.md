# OAuth 2.0 Cookbook
This is the code repository for [OAuth 2.0 Cookbook](https://www.packtpub.com/virtualization-and-cloud/oauth-20-cookbook?utm_source=github&utm_medium=repository&utm_campaign=9781788295963), published by [Packt](https://www.packtpub.com/?utm_source=github). It contains all the supporting project files necessary to work through the book from start to finish.
## About the Book
OAuth 2.0 is a standard protocol for authorization and focuses on client development simplicity while providing specific authorization flows for web applications, desktop applications, mobile phones, and so on. This book also provides useful recipes for solving real-life problems using Spring Security and creating Android applications.

The book starts by presenting you how to interact with some public OAuth 2.0 protected APIs such as Facebook, LinkedIn and Google. You will also be able to implement your own OAuth 2.0 provider with Spring Security OAuth2. Next, the book will cover practical scenarios regarding some important OAuth 2.0 profiles such as Dynamic Client Registration, Token Introspection and how to revoke issued access tokens. You will then be introduced to the usage of JWT, OpenID Connect, and how to safely implement native mobile OAuth 2.0 Clients.

By the end of this book, you will be able to ensure that both the server and client are protected against common vulnerabilities.

## Instructions and Navigation
All of the code is organized into folders. Each folder starts with a number followed by the application name. For example, Chapter02.



The code will look like the following:
```
public class Entry {
    private String value;
    public Entry(String value) 
     { this.value = value; }
    public String getValue() 
     { return value; }
}
```

To run the recipes presented in this book, you will basically need JDK 8, Maven, MySQL, and Redis. JDK 8 can be downloaded at http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html . You can download and read the installation instructions for Maven at https://maven.apache.org/download.cgi. To install MySQL, download the community version for your Operational System (OS) at https://dev.mysql.com/downloads/. Some recipes rely on Redis, which can be downloaded here: https://redis.io/download. To interact with the applications that will be created during the recipes, you also need a tool to send HTTP requests to the APIs presented. The recommended tools are CURL, which can be downloaded at https://curl.haxx.se/download.html and PostMan which can be downloaded at https://www.getpostman.com/.

In addition, so that you can write the code presented throughout the recipes, you will also need a Java IDE and Android Studio for native mobile Client recipes.

## Related Products
* [Mastering OAuth 2.0](https://www.packtpub.com/application-development/mastering-oauth-2?utm_source=github&utm_medium=repository&utm_campaign=9781784395407)

* [OAuth 2.0 Identity and Access Management Patterns](https://www.packtpub.com/application-development/oauth-20-identity-and-access-management-patterns?utm_source=github&utm_medium=repository&utm_campaign=9781783285594)

* [Deep Inside osCommerce: The Cookbook](https://www.packtpub.com/web-development/deep-inside-oscommerce-cookbook?utm_source=github&utm_medium=repository&utm_campaign=9781847190901)

