package core.data

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import com.russhwolf.settings.Settings
import core.domain.SettingFunctions

class SettingFunctionsImpl(
    private val settings: Settings,
) : SettingFunctions {
    override suspend fun updateUser(user: UserData) {
        /*This updates the entire user*/
        settings.putString("nickname", user.nickname)
        settings.putString("email", user.email)
        settings.putString("id", user.id)
        settings.putInt("experience", user.experience)
        settings.putInt("current_level", user.currentLevel)
        settings.putString("icon", IconResource.tagFromResource(user.icon))
        settings.putString("achievements", user.achievements.joinToString(separator = "|&|"))
        settings.putString("levels", user.selectedLevels.joinToString(separator = "|&|"))
        settings.putString("sets", user.sets.joinToString(separator = "|&|"))
    }

    override suspend fun updateUserName(name: String) {
        settings.putString("nickname", name)

    }

    override suspend fun updateUserEmail(email: String) {
        settings.putString("email", email)

    }

    override suspend fun updateUserId(id: String) {
        settings.putString("id", id)

    }

    override suspend fun updateUserExperience(experience: Int) {
        settings.putInt("experience", experience)

    }

    override suspend fun updateUserCurrentLevel(currentLevel: Int) {
        settings.putInt("current_level", currentLevel)
    }

    override suspend fun updateUserIcon(iconTag: String) {
        settings.putString("icon", iconTag)
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        settings.putString("achievements", achievements.joinToString("|&|"))
    }

    override suspend fun updateUserSelectedLevels(levels: List<Int>) {
        settings.putString("achievements", levels.joinToString("|&|"))

    }

    override suspend fun updateUserSets(sets: List<String>) {
        settings.putString("achievements", sets.joinToString("|&|"))

    }
    override suspend fun logoutUser() {
        settings.clear()
    }

    override suspend fun createUser(email: String, id: String) {
        /*TODO - sets, achievements, levels. Have to figure a way to select these and alter them properly.*/
        settings.putString("email", email)
        settings.putString("id", id)
    }

    override fun getUser(): UserData {
        return UserData(
            nickname = settings.getString("nickname", ""),
            email = settings.getString("email", ""),
            id = settings.getString("id", ""),
            experience = settings.getInt("nickname", 0),
            currentLevel = settings.getInt("nickname", 0),
            icon = IconResource.resourceFromTag(settings.getString("icon", "bear 1")),
            achievements = settings.getString("achievements", "").split("|&|"),
            selectedLevels = settings.getString("selected_levels", "").split("|&|")
                .map { it.toInt() },
            sets = settings.getString("sets", "").split("|&|")

        )
    }


}