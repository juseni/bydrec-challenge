package com.bydrecchallenge.myapplication.models

data class MatchRow(
    var type: RowType,
    var item: BydrecData?,
    var header: String?
)

enum class RowType {
    ITEM,
    HEADER
}

