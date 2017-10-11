package oauth2

import java.io._
import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.BasicResponseHandler

object OAuth {

  def getToken(): String = {
    val url = "http://localhost:8080/oauth/token"

    val auth = java.util.Base64
      .getEncoder()
      .encodeToString("clientapp:123456".getBytes("UTF-8"));

    val post = new HttpPost(url)
    post.addHeader("Content-Type", "application/x-www-form-urlencoded")
    post.addHeader("Authorization", "Basic " + auth)

    val client = new DefaultHttpClient

    val attributes = new ArrayList[NameValuePair](1)
    attributes.add(new BasicNameValuePair("grant_type", "password"))
    attributes.add(new BasicNameValuePair("username", "adolfo"))
    attributes.add(new BasicNameValuePair("password", "123"))
    attributes.add(new BasicNameValuePair("scope", "read_profile"))

    post.setEntity(new UrlEncodedFormEntity(attributes))
    val response = client.execute(post)
    
    val responseString = new BasicResponseHandler().handleResponse(response)

    responseString
  }
}