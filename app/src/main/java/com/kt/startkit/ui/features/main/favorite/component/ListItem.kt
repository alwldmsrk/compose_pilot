package com.kt.startkit.ui.features.main.favorite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kt.startkit.R


@Composable
fun ResultDataItem(
    bookMarkIcon: Int,
    columnContent: @Composable ColumnScope.() -> Unit,
    onClickBookmark: (() -> Unit)? = null,
) {
    Column(
        Modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(bookMarkIcon),
                modifier = Modifier
                    .padding(start = 10.dp, end = 14.dp),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                columnContent()
            }

            onClickBookmark?.let {
                IconButton(
                    onClick = { it() },
                ) {
                    Icon(
                        painterResource(id = R.drawable.outline_delete_black_24),
                        contentDescription = null
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = colorResource(id = R.color.grey_900)
        )
    }
}

@Composable
fun PlaceColumnContent(
    title: String,
    address: String
) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = address,
        color = colorResource(id = R.color.grey_900)
    )
}