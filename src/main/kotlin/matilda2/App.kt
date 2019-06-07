package matilda2

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Environment
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.NotEmpty
import java.util.*
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Path("/clock")
@Produces(APPLICATION_JSON)
class ClockResource() {

    @POST
    @Consumes(APPLICATION_JSON)
    fun clock(
        @Valid clockDTO: ClockDTO
    ): ReturnDTO {

        println("We got the request $clockDTO")
        val dateTime = DateTime(DateTimeZone.UTC)
        println("We got the request in the time: $dateTime")
        val returnDTO = ReturnDTO(time = dateTime, clockDTO = clockDTO) 
        return returnDTO
    }

    data class ClockDTO(
        @JsonProperty val content: String = ""
    )

    data class ReturnDTO(
        val clockDTO: ClockDTO,
        val time: DateTime
    )
}

class HWApplication : Application<Configuration>() {

    override fun getName() = "hello-world"

    override fun run(conf: Configuration, env: Environment) {
        val clockResource = ClockResource()
        env.jersey().register(clockResource)
    }
}

fun main(args: Array<String>) = HWApplication().run(*args)