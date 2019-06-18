package matilda2.contracts

import org.joda.time.DateTime

data class TopListDTO(
        val topList: List<TopListItemDTO>
) {

    data class TopListItemDTO(
            val userId: String,
            val time: DateTime
    )
}