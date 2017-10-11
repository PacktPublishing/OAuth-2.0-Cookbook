import oauth2.OAuth
import org.json4s._
import org.json4s.native.JsonMethods._

object exercise {

  case class Token(access_token:String)

  implicit val formats = DefaultFormats
  val jsValue = parse(OAuth.getToken())
  val accessToken = jsValue
    .extract[Token].access_token

}