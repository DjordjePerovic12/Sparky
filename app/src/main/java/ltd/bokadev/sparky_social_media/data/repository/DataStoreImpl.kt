package ltd.bokadev.sparky_social_media.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import ltd.bokadev.sparky_social_media.core.utils.Constants.DATASTORE_NAME
import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.domain.model.UserData
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreImpl @Inject constructor(@ApplicationContext val context: Context) :
    DataStoreRepository {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val USERNAME = stringPreferencesKey("USERNAME")
        val USER_ID = stringPreferencesKey("USER_ID")
        val EXPIRATION = stringPreferencesKey("EXPIRATION")
    }

    override suspend fun saveToken(token: String) {
        context.datastore.edit { datastore ->
            datastore[ACCESS_TOKEN] = token
        }
    }

    override suspend fun saveUser(user: UserData) {
        context.datastore.edit { datastore ->
            datastore[USERNAME] = user.username
            datastore[USER_ID] = user.id
            datastore[ACCESS_TOKEN] = user.accessToken
            datastore[REFRESH_TOKEN] = user.refreshToken
            datastore[EXPIRATION] = user.accessTokenExpirationTimestamp
        }
    }

    override suspend fun getUser() = context.datastore.data.map { datastore ->
        UserData(
            id = datastore[USER_ID].toNonNull(),
            username = datastore[USERNAME].toNonNull(),
            accessToken = datastore[ACCESS_TOKEN].toNonNull(),
            refreshToken = datastore[REFRESH_TOKEN].toNonNull(),
            accessTokenExpirationTimestamp = datastore[EXPIRATION].toNonNull()
        )
    }


    //    override suspend fun setGToken(token: String) {
//        context.datastore.edit { datastore ->
//            datastore[ACCESS_TOKEN] = token
//        }
//    }
//
    override suspend fun getToken() =
        context.datastore.data.map { datastore -> datastore[ACCESS_TOKEN].toNonNull() }

    override suspend fun clearDatastore() {
        context.datastore.edit {
            it.remove(USERNAME)
            it.remove(USER_ID)
            it.remove(REFRESH_TOKEN)
            it.remove(EXPIRATION)
            it.remove(ACCESS_TOKEN)
        }
    }
}
