package ru.zettatech.hrv.item

class ParentItem(private var parentId: Int, private var title: String, private var childItemList: List<ChildItem>) {

    fun getParentId(): Int {
        return parentId
    }

    fun setParentId(_parentId: Int) {
        parentId = _parentId
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(_title: String) {
        title = _title
    }

    fun getChildItemList(): List<ChildItem> {
        return childItemList
    }

    fun setChildItemList(_childItemList: List<ChildItem>) {
        childItemList = _childItemList
    }
}