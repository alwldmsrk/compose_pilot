package com.kt.startkit.ui.features.main.favorite.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kt.startkit.domain.entity.FavoriteData
import java.io.IOException


data class BookmarkPagingResult(
    val page: Int? = null,
    val value: List<FavoriteData>
)

//open class BookmarkPagingSource(
//    //private val favorite: NewsApiService,
//    //private val favoriteDataSource : FavoriteDataSource
//    private val apiCall: suspend (Int, Int) -> BookmarkPagingResult
//) : PagingSource<Int, FavoriteData>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteData> {
//        // LoadParams: 로드할 키와 항목 수, LoadResult: 로드 작업의 결과
//        // key가 정의되지 않았다면 첫번째 페이지에서 시작
//        val page = params.key ?: 1
//        return try {
//            val response = apiCall(3,5)
//            LoadResult.Page(
//                data = favoriteUseCase.getAllFavorites(),
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (response.value.isEmpty()) null else page + 1
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }

//    override fun getRefreshKey(state: PagingState<Int, FavoriteData>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}