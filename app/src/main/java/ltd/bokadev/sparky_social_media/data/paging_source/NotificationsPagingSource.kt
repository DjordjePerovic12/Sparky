package ltd.bokadev.sparky_social_media.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ltd.bokadev.sparky_social_media.data.remote.mapper.toNotificationCreatedAt
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper

class NotificationsPagingSource(
    private val sparkyService: SparkyService
) : PagingSource<Int, NotificationWrapper>() {
    override fun getRefreshKey(state: PagingState<Int, NotificationWrapper>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationWrapper> {
        return try {
            val currentPage = params.key ?: 0
            val response = sparkyService.getNotifications(
                page = currentPage, pageCount = 20
            )
            val notifications = response.body()

            LoadResult.Page(
                data = notifications?.toNotificationCreatedAt() ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (notifications?.toNotificationCreatedAt()
                        .isNullOrEmpty()
                ) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
