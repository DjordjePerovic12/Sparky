package ltd.bokadev.sparky_social_media.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUsers
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

class UsersPagingSource(
    private val sparkyService: SparkyService,
    private val searchQuery: String
) : PagingSource<Int, UserDetails>() {


    override fun getRefreshKey(state: PagingState<Int, UserDetails>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDetails> {
        return try {
            val currentPage = params.key ?: 0
            val response = sparkyService.searchProfiles(
                searchQuery = searchQuery,
                page = currentPage,
                pageCount = 20
            )
            val users = response.body()

            LoadResult.Page(
                data = users?.toUsers() ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (users?.isNotEmpty() == true) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}