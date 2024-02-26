package ltd.bokadev.sparky_social_media.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUsers
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.User
import timber.log.Timber

class UsersPagingSource(
    private val sparkyService: SparkyService, private val searchQuery: String
) : PagingSource<Int, User>() {


    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 0
            val response = sparkyService.searchProfiles(
                searchQuery = searchQuery, page = currentPage, pageCount = 20
            )
            val users = response.body()
            Timber.e("PAGING USERS ${users?.toUsers()}")
            Timber.e("PAGING USERS ${users}")
            Timber.e("PAGING USERS ${response.body()}")

            LoadResult.Page(
                data = users?.toUsers() ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (users.isNullOrEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}