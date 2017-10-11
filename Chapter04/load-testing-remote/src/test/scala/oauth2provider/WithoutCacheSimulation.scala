package oauth2provider

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class WithoutCacheSimulation extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081")
  val withoutCacheScenario = List(
    AuthorizationScenarios.withoutCache.inject(rampUsers(100) over(10 seconds)))
  setUp(withoutCacheScenario).protocols(httpConf)

}
