package matilda2

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import matilda2.contracts.ClockDTO
import matilda2.contracts.HelloWorldDTO
import matilda2.contracts.TopListDTO
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
    ): TopListDTO {
        val dateTime = DateTime(DateTimeZone.UTC)

        val result = userPressService.userPressed(clockDTO.content, dateTime).map {
            it.toDTO()
        }

        return TopListDTO(result)
    }

    @GET
    fun getTopList(): TopListDTO {
        val result = userPressService.getTopList().map {
            it.toDTO()
        }
        return TopListDTO(result)
    }

    private fun TopListItem.toDTO(): TopListDTO.TopListItemDTO {
        return TopListDTO.TopListItemDTO(
                userId = this.userId,
                time = this.time
        )
    }

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
