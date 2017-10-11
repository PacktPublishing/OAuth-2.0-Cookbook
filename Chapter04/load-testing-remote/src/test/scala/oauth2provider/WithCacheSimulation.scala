package oauth2provider

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class WithCacheSimulation extends Simulation {
  
  val httpConfCache = http.baseURL("http://localhost:8082")
  val withCacheScenario = List(
    AuthorizationScenarios.withCache.inject(rampUsers(100) over(10 seconds)))
  setUp(withCacheScenario).protocols(httpConfCache)

}
