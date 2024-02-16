package core.data.settings

import com.jaegerapps.malmali.components.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import com.russhwolf.settings.Settings
import core.domain.SettingFunctions

class SettingFunctionsImpl(
    private val settings: Settings,
) : SettingFunctions {
    override suspend fun updateUser(user: UserData) {
        settings.putString(SettingKeys.USERNAME, user.nickname)
        settings.putString(SettingKeys.EMAIL, user.email)
        settings.putInt(SettingKeys.EXPERIENCE, user.experience)
        settings.putInt(SettingKeys.CURRENT_LEVEL, user.currentLevel)
        settings.putString(SettingKeys.ICON, IconResource.tagFromResource(user.icon))
        settings.putString(SettingKeys.ACHIEVEMENTS, user.achievements.joinToString(separator = "|&|"))
        settings.putString(
            SettingKeys.LEVELS,
            user.selectedLevels.joinToString(separator = "|&|")
        )
        settings.putString(SettingKeys.SETS, user.sets.joinToString(separator = "|&|"))
    }

    override suspend fun updateUserName(name: String) {
        settings.putString(SettingKeys.USERNAME, name)

    }

    override suspend fun updateUserEmail(email: String) {
        settings.putString(SettingKeys.EMAIL, email)

    }

    override suspend fun updateUserId(id: String) {
        settings.putString(SettingKeys.ID, id)

    }

    override suspend fun updateUserExperience(experience: Int) {
        settings.putInt(SettingKeys.EXPERIENCE, experience)

    }

    override suspend fun updateUserCurrentLevel(currentLevel: Int) {
        settings.putInt(SettingKeys.CURRENT_LEVEL, currentLevel)
    }

    override suspend fun updateUserIcon(iconTag: String) {
        settings.putString(SettingKeys.ICON, iconTag)
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        settings.putString(SettingKeys.ACHIEVEMENTS, achievements.joinToString("|&|"))
    }

    override suspend fun updateUserSelectedLevels(levels: List<Int>) {
        settings.putString(SettingKeys.LEVELS, levels.joinToString("|&|"))

    }

    override suspend fun updateUserSets(sets: List<String>) {
        settings.putString(SettingKeys.SETS, sets.joinToString("|&|"))

    }

    override suspend fun logoutUser() {
        settings.clear()
    }

    override suspend fun createUser(email: String, id: String) {
        /*TODO - sets, achievements, levels. Have to figure a way to select these and alter them properly.*/
        settings.putString(SettingKeys.EMAIL, email)
        settings.putString(SettingKeys.ID, id)
    }

    override fun getUser(): UserData {
        return UserData(
            nickname = settings.getString(SettingKeys.USERNAME, ""),
            email = settings.getString(SettingKeys.EMAIL, ""),
            experience = settings.getInt(SettingKeys.EXPERIENCE, 0),
            currentLevel = settings.getInt(SettingKeys.CURRENT_LEVEL, 0),
            icon = IconResource.resourceFromTag(settings.getString(SettingKeys.ICON, "bear 1")),
            achievements = settings.getString(SettingKeys.ACHIEVEMENTS, "").split("|&|"),
            selectedLevels = settings.getString(SettingKeys.LEVELS, "1").split("|&|")
                .map { it.toInt() },
            sets = settings.getString(SettingKeys.SETS, "").split("|&|")

        )
    }

    override fun getOnboardingBoolean(): Boolean {
        return settings.getBoolean(SettingKeys.ONBOARDING, true)
    }

    override suspend fun changeOnboardingBoolean() {
        settings.putBoolean(SettingKeys.ONBOARDING, false)
    }


}