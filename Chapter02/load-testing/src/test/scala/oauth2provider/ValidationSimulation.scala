package oauth2provider

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class ValidationSimulation extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081")

  val authorizationScenarios = List(
    AuthorizationScenarios.scenario1.inject(rampUsers(100) over(10 seconds)))

  setUp(authorizationScenarios).protocols(httpConf)

}
