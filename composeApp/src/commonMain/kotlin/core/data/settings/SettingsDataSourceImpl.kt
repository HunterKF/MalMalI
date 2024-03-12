package core.data.settings

import com.jaegerapps.malmali.common.models.IconResource
import com.jaegerapps.malmali.login.domain.UserData
import com.russhwolf.settings.Settings
import core.domain.SettingsDataSource
import core.util.toDbString
import core.util.toListString

class SettingsDataSourceImpl(
    private val settings: Settings,
) : SettingsDataSource {
    override suspend fun updateUser(user: UserData) {
        settings.putString(SettingKeys.USERNAME, user.nickname)
        settings.putInt(SettingKeys.EXPERIENCE, user.experience)
        settings.putInt(SettingKeys.CURRENT_LEVEL, user.currentLevel)
        settings.putString(SettingKeys.ICON, IconResource.tagFromResource(user.icon))
        settings.putString(SettingKeys.ACHIEVEMENTS, user.achievements.toDbString())
        settings.putString(
            SettingKeys.LEVELS,
            user.selectedLevels.toDbString()
        )
        settings.putString(SettingKeys.SETS, user.sets.toDbString())
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

    override suspend fun updateUserIcon(icon: String) {
        settings.putString(SettingKeys.ICON, icon)
    }

    override suspend fun updateUserAchievements(achievements: List<String>) {
        settings.putString(SettingKeys.ACHIEVEMENTS, achievements.toDbString())
    }

    override suspend fun updateUserSelectedLevels(levels: List<Int>) {
        settings.putString(SettingKeys.LEVELS, levels.toDbString())

    }

    override suspend fun updateUserSets(sets: List<String>) {
        settings.putString(SettingKeys.SETS, sets.toDbString())

    }

    override suspend fun logoutUser() {
        settings.clear()
    }

    override suspend fun createUser(email: String, id: String) {
        /*TODO - sets, achievements, levels. Have to figure a way to select these and alter them properly.*/
        settings.putString(SettingKeys.EMAIL, email)
        settings.putString(SettingKeys.ID, id)
        settings.putString(SettingKeys.LEVELS, "1")
    }

    override suspend fun getUser(): UserData {
        return UserData(
            nickname = settings.getString(SettingKeys.USERNAME, ""),
            experience = settings.getInt(SettingKeys.EXPERIENCE, 0),
            currentLevel = settings.getInt(SettingKeys.CURRENT_LEVEL, 0),
            icon = IconResource.resourceFromTag(settings.getString(SettingKeys.ICON, "bear 1")),
            achievements = settings.getString(SettingKeys.ACHIEVEMENTS, "").toListString(),
            selectedLevels = settings.getString(SettingKeys.LEVELS, "Level 1").toListString(),
            sets = settings.getString(SettingKeys.SETS, "-1").toListString()

        )
    }

    override suspend fun getOnboardingBoolean(): Boolean {
        return settings.getBoolean(SettingKeys.ONBOARDING, true)
    }

    override suspend fun changeOnboardingBoolean() {
        settings.putBoolean(SettingKeys.ONBOARDING, false)
    }

    override suspend fun saveToken(token: String) {
        settings.putString(SettingKeys.ACCESS_TOKEN, token)
    }
    override suspend fun getToken(): String? {
        return settings.getStringOrNull(SettingKeys.ACCESS_TOKEN)
    }



}