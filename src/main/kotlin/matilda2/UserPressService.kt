package matilda2

import org.joda.time.DateTime

interface IUserPressService {
    fun userPressed(userId: String, time: DateTime): List<TopListItem>
    fun getTopList(): List<TopListItem>
}

class UserPressService : IUserPressService {
    private val topList = mutableListOf<TopListItem>()

    override fun userPressed(userId: String, time: DateTime): List<TopListItem> {
        if (!topList.any { item -> item.userId == userId })
            topList.add(TopListItem(userId, time))
        return topList
    }

    override fun getTopList(): List<TopListItem> {
        return topList
    }
}

data class TopListItem(
        val userId: String,
        val time: DateTime
)