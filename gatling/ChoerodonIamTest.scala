package choerodon

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class ChoerodonIamTest extends Simulation {

  private val title = "choerodon-test"

  //Project Domain
  private val postCreateProjectRequest = "POST create-project"
  private val getListAllProjectsRequest = "GET list-all-projects (mem hotspot)"
  private val getListEnabledProjectsRequest = "GET list-enabled-projects (mem hotspot)"
  private val putDisableProjectRequest = "PUT disable-project"
  private val putEnableProjectRequest = "PUT enable-project"

  //User Domain
  private val postCreateUserRequest = "POST create-user (cpu hotspot)"
  private val getListAllUsersRequest = "GET list-users"
  private val getOneUserRequest = "GET one-user"
  private val putDisableUserRequest = "PUT disable-user"
  private val putEnableUserRequest = "PUT enable-user"

  private val postCreateProject =
    http(postCreateProjectRequest)
      .post("/organizations/#{org-id}/projects")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """
          |{
          |    "code": "code#{randomInt}",
          |    "name": "name#{randomInt}",
          |    "categories": [{"code":"GENERAL"}]
          |}
        """.stripMargin))
      .check(substring("failed").notExists)
      .check(jmesPath("id").saveAs("proj-id"))

  private val getListAllProjects =
    http(getListAllProjectsRequest)
      .get("/organizations/#{org-id}/projects/all")
      .check(bodyBytes.exists)

  private val getListEnabledProjects =
    http(getListEnabledProjectsRequest)
      .get("/inner/projects/all?enabled=true")
      .check(bodyBytes.exists)

  private val putDisableProject =
    http(putDisableProjectRequest)
      .put("/organizations/#{org-id}/projects/#{proj-id}/disable")
      .check(substring("failed").notExists)

  private val putEnableProject =
    http(putEnableProjectRequest)
      .put("/organizations/#{org-id}/projects/#{proj-id}/enable")
      .check(substring("failed").notExists)

  private val circularUserValues = Array(
    Map("user-id" -> "572990852612493312"),
    Map("user-id" -> "572990928432926720"),
    Map("user-id" -> "572990960569683968"),
    Map("user-id" -> "572990994124115968"),
    Map("user-id" -> "572991031084322816"),
  ).circular

  private val postCreateUser =
    http(postCreateUserRequest)
      .post("/organizations/#{org-id}/users")
      .header("Content-Type", "application/json")
      .body(StringBody(
        """
          |{
          |    "realName": "realName#{randomInt}",
          |    "loginName": "loginName#{randomInt}",
          |    "email": "email#{randomInt}@email.com",
          |    "internationalTelCode": "+86",
          |    "phone": "86#{randomPhone}86",
          |    "roles": []
          |}
        """.stripMargin))
      .check(substring("failed").notExists)
      .check(jmesPath("id").saveAs("user-id"))

  private val getListAllUsers =
    http(getListAllUsersRequest)
      .get("/all/users?organization_id=#{org-id}")
      .check(bodyBytes.exists)

  private val getOneUser =
    http(getOneUserRequest)
      .get("/organizations/#{org-id}/users/#{user-id}")
      .check(bodyBytes.exists)

  private val putDisableUser =
    http(putDisableUserRequest)
      .put("/organizations/#{org-id}/users/#{user-id}/disable")
      .check(substring("failed").notExists)

  private val putEnableUser =
    http(putEnableUserRequest)
      .put("/organizations/#{org-id}/users/#{user-id}/enable")
      .check(substring("failed").notExists)

  private val choerodon: ScenarioBuilder = scenario(title)
    .group("Project") {
      exec(session => {
        session.set("org-id", "1")
      })
        .exec(session => {
          val randomInt: String = scala.util.Random.nextInt(1000000000).toString
          session.set("randomInt", randomInt)
        })
        .exec(postCreateProject)
        .pause(2)
        .exec(getListAllProjects)
        .exec(getListEnabledProjects)
        .exec(putDisableProject)
        .pause(1)
        .exec(putEnableProject)
    }
    .group("User") {
      exec(session => {
        session.set("org-id", "1")
      })
        .exec(session => {
          val randomInt: String = scala.util.Random.nextInt(1000000000).toString
          session.set("randomInt", randomInt)
        })
        .exec(session => {
          val randomPhone: String = f"${scala.util.Random.nextInt(10000000)}%07d"
          session.set("randomPhone", randomPhone)
        })
        .feed(circularUserValues)
        .exec(postCreateUser)
        .pause(2)
        //.exec(getListAllUsers)
        .exec(getOneUser)
        .exec(putDisableUser)
        .pause(1)
        .exec(putEnableUser)
    }

  val protocolReqres: HttpProtocolBuilder = http
    .baseUrl("http://127.0.0.1:8030/choerodon/v1")
    .disableCaching
  setUp(
    choerodon
      .inject(
        rampUsers(120) during (60 seconds),
        constantUsersPerSec(5) during (540 seconds)
      )
  ).protocols(protocolReqres)
    .assertions(
      global.successfulRequests.percent.gte(99)
    )
}

// sbt clean compile
// sbt "gatling:testOnly gatling.ChoerodonIamTest"
