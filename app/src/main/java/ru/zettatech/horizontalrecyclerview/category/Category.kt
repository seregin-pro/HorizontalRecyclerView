package ru.zettatech.horizontalrecyclerview.category

class Category (private var categoryId: Int, private var image: String, private var title: String) {

    fun getCategoryId(): Int {
        return categoryId
    }

    fun setCategoryId(_categoryId: Int) {
        categoryId = _categoryId
    }

    fun getImage(): String {
        return image
    }

    fun setImage(_image: String) {
        image= _image
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(_title: String) {
        title = _title
    }
}