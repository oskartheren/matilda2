package matilda2

import org.joda.time.DateTime

interface IUserPressService {
    fun userPressed(userId: String, time: DateTime)
    fun getTopList(): Map<String, DateTime>
}

class UserPressService: IUserPressService {
    private val topList = HashMap<String, DateTime>()

    override fun userPressed(userId: String, time: DateTime) {
        if (!topList.containsKey(userId))
            topList[userId] = time
    }

    override fun getTopList(): Map<String, DateTime> {
        return HashMap(topList)
    }
}