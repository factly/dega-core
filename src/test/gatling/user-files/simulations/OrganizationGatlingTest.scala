import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Organization entity.
 */
class OrganizationGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-XSRF-TOKEN" -> "${xsrf_token}"
    )

    val keycloakHeaders = Map(
        "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Upgrade-Insecure-Requests" -> "1"
    )

    val scn = scenario("Test the Organization entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .get("/login")
        .headers(keycloakHeaders)
        .check(css("#kc-form-login", "action").saveAs("kc-form-login"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authenticate")
        .post("${kc-form-login}")
        .headers(keycloakHeaders)
        .formParam("username", "admin")
        .formParam("password", "admin")
        .formParam("submit", "Login")
        .check(status.is(200))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all organizations")
            .get("/core/api/organizations")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new organization")
            .post("/core/api/organizations")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "name":"SAMPLE_TEXT", "email":"SAMPLE_TEXT", "phone":"SAMPLE_TEXT", "siteTitle":"SAMPLE_TEXT", "tagLine":"SAMPLE_TEXT", "description":"SAMPLE_TEXT", "logoURL":"SAMPLE_TEXT", "logoURLMobile":"SAMPLE_TEXT", "favIconURL":"SAMPLE_TEXT", "mobileIconURL":"SAMPLE_TEXT", "baiduVerificationCode":"SAMPLE_TEXT", "bingVerificationCode":"SAMPLE_TEXT", "googleVerificationCode":"SAMPLE_TEXT", "yandexVerificationCode":"SAMPLE_TEXT", "facebookURL":"SAMPLE_TEXT", "twitterURL":"SAMPLE_TEXT", "instagramURL":"SAMPLE_TEXT", "linkedInURL":"SAMPLE_TEXT", "pinterestURL":"SAMPLE_TEXT", "youTubeURL":"SAMPLE_TEXT", "googlePlusURL":"SAMPLE_TEXT", "githubURL":"SAMPLE_TEXT", "facebookPageAccessToken":"SAMPLE_TEXT", "gaTrackingCode":"SAMPLE_TEXT", "githubClientId":"SAMPLE_TEXT", "githubClientSecret":"SAMPLE_TEXT", "twitterClientId":"SAMPLE_TEXT", "twitterClientSecret":"SAMPLE_TEXT", "facebookClientId":"SAMPLE_TEXT", "facebookClientSecret":"SAMPLE_TEXT", "googleClientId":"SAMPLE_TEXT", "googleClientSecret":"SAMPLE_TEXT", "linkedInClientId":"SAMPLE_TEXT", "linkedInClientSecret":"SAMPLE_TEXT", "instagramClientId":"SAMPLE_TEXT", "instagramClientSecret":"SAMPLE_TEXT", "mailchimpAPIKey":"SAMPLE_TEXT", "siteLanguage":"SAMPLE_TEXT", "timeZone":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_organization_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created organization")
                .get("/core${new_organization_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created organization")
            .delete("/core${new_organization_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
