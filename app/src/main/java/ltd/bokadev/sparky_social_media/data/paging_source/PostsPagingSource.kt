package ltd.bokadev.sparky_social_media.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ltd.bokadev.sparky_social_media.data.remote.mapper.toPosts
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUsers
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.Post
import timber.log.Timber

class PostsPagingSource(
    private val sparkyService: SparkyService
) : PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val currentPage = params.key ?: 0
            val response = sparkyService.getFeedPosts(
                page = currentPage, pageCount = 20
            )
            val posts = response.body()

            LoadResult.Page(
                data = posts?.toPosts() ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (posts.isNullOrEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}