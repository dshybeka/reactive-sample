package by.reactive.sample.performance

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import scala.util.Random

class TestScenario extends Simulation {

    val feeder = csv("tests\\src\\test\\resources\\data\\hero.csv").circular

    val httpConf = http
        .baseURL("http://localhost:8080")
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .doNotTrackHeader("1")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .acceptEncodingHeader("gzip, deflate")
        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

    val scn = scenario("TestScenario")
        .feed(feeder)
        .exec(http("post_request")
                  .post("/")
                  .body(StringBody(session => getHeroBody(session)))
                  .check(status.is(200)))

    setUp(
        scn.inject(
//            https://gatling.io/docs/2.3/general/simulation_setup/?highlight=constant
//            atOnceUsers(1)
            constantUsersPerSec(100) during (120 seconds)
        )
    ).protocols(httpConf)

    val rnd = new Random()

    def getHeroBody(session: Session): String = {
        s"""
           |{
           |  "name": "${session("Name").as[String]}",
           |  "skills": [
           |    {
           |      "name": "Hit",
           |      "type": "${session("SkillType").as[String]}",
           |      "info": {
           |        "power": ${rnd.nextDouble() * 30},
           |        "source": "${"smth from movie " + rnd.nextInt(50)}"
           |      }
           |    }
           |  ],
           |  "lastSeen": "${System.currentTimeMillis()}"
           |}
         """.stripMargin
    }
}
