package matilda2

import org.joda.time.DateTime

interface IUserPressService {
    fun userPressed(userId: String, time: DateTime)
    fun getTopList(): List<TopListItem>
}

class UserPressService : IUserPressService {
    private val topList = mutableListOf<TopListItem>()

    override fun userPressed(userId: String, time: DateTime) {
        if (!topList.any { item -> item.userId == userId })
            topList.add(TopListItem(userId, time))
    }

    override fun getTopList(): List<TopListItem> {
        return topList
    }
}

data class TopListItem(
        val userId: String,
        val time: DateTime
)