package ru.zettatech.hrv.item

class ChildItem (private var resourceId: Int, private var title: String, private var description: String) {
    fun getResourceId(): Int {
        return resourceId
    }

    fun setResourceId(_resourceId: Int) {
        resourceId = _resourceId
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(_title: String) {
        title = _title
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(_description: String) {
        description = _description
    }
}