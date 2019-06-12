package matilda2

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*
import javax.servlet.DispatcherType
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("hello-world")
@Produces(APPLICATION_JSON)
class HelloWorldResource {
    @GET
    fun getHelloWorld(): HelloWorldDTO {
        return HelloWorldDTO("Hello, world!")
    }

    data class HelloWorldDTO(
            val content: String
    )
}

@Path("clock")
@Produces(APPLICATION_JSON)
class ClockResource(
        private val userPressService: IUserPressService
) {

    @POST
    @Consumes(APPLICATION_JSON)
    fun clock(
            @Valid clockDTO: ClockDTO
    ): ReturnDTO {
        println("We got the request $clockDTO")
        val dateTime = DateTime(DateTimeZone.UTC)
        println("We got the request in the time: $dateTime")

        userPressService.userPressed(clockDTO.content, dateTime)

        return ReturnDTO(time = dateTime, clockDTO = clockDTO)
    }

    @GET
    fun getTopList(): TopListDTO {
        return TopListDTO(userPressService.getTopList())
    }

    data class ClockDTO(
            @JsonProperty val content: String = ""
    )

    data class ReturnDTO(
            val clockDTO: ClockDTO,
            val time: DateTime
    )

    data class TopListDTO(
            val topList: List<TopListItem>
    )
}

fun configureCORS(env: Environment) {
    val cors = env.servlets().addFilter("CORS", CrossOriginFilter::class.java)

    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*")
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
            "X-Requested-With,Content-Type,Accept,Origin,Authorization")
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
            "OPTIONS,GET,PUT,POST,DELETE,HEAD")
    cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true")

   cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
}

class HWApplication : Application<Configuration>() {

    override fun getName() = "hello-world"

    override fun initialize(bootstrap: Bootstrap<Configuration>) {
        super.initialize(bootstrap)
        with(bootstrap.objectMapper) {
            registerModule(KotlinModule())
        }
    }

    override fun run(conf: Configuration, env: Environment) {
        configureCORS(env)
        env.jersey().register(JsonProcessingExceptionMapper(true))
        val clockResource = ClockResource(UserPressService())
        env.jersey().register(clockResource)

        env.jersey().register(HelloWorldResource())
    }
}

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        HWApplication().run(*args)
    }

}
