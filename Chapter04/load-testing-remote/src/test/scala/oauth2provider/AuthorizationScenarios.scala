package oauth2provider

import oauth2.OAuth
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

object AuthorizationScenarios {
  
  case class Token(access_token:String)

  implicit val formats = DefaultFormats
  val jsValue = parse(OAuth.getToken())
  val accessToken = "Bearer " + jsValue
    .extract[Token].access_token

  var withoutCache = scenario("Without cache")
      .exec(http("Validate access token without cache")
        .get("/api/profile")
        .header("Authorization", accessToken)
      )
      
  var withCache = scenario("Using cache")
      .exec(http("Validate access token with cache")
        .get("/api/profile")
        .header("Authorization", accessToken)
      )
      
}