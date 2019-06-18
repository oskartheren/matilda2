package matilda2.contracts

import com.fasterxml.jackson.annotation.JsonProperty

data class ClockDTO(
        @JsonProperty val content: String = ""
)