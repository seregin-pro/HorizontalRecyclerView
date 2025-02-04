# Horizontal RecyclerView

![GitHub](https://badgen.net/static/license/GPL3/orange)

A nested RecyclerView refers to the use of a RecyclerView inside another RecyclerView. 
This layout can be observed in various applications, such as the Play Store, where the parent RecyclerView is oriented 
vertically, while the child RecyclerViews are oriented horizontally.

## Demo application
You can download this project and run on Android Studio

## Setup

## Usage

For an example see [MainActivity](app/src/main/java/ru/zettatech/horizontalrecyclerview/MainActivity.kt) in demo project.

MainActivity.kt

``` kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarView)

        // Create parent list
        // Use a function that gets a list of parent elements
        val parents: ArrayList<Item> = getParentList()

        val parentItems = mutableListOf<ParentItem>().apply {
            for (i in parents.indices) {

                // Create child list
                // Use a function that gets a list of parent elements
                val childList: ArrayList<Item> = getChildList()

                val childItems = mutableListOf<ChildItem>().apply {
                    for (j in childList.indices) {
                        val resourceId: Int = getImageResourceId(childList.get(j).getImage(), R.drawable::class.java)
                        add(ChildItem(resourceId, childList.get(j).getTitle(), ""))
                    }
                }

                // Add the parent item with childs
                add(ParentItem(parents.get(i).getItemId(), parents.get(i).getTitle(), childItems))
            }
        }

        val parentItemClickListener: ParentAdapter.OnParentItemClickListener =
            object : ParentAdapter.OnParentItemClickListener {

                // Add click listener for parent items
                override fun onParentItemClick(parentItem: ParentItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "Parent Item clicked: " + parentItem.getTitle(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show();
                }

                // Add click listener for button forward
                override fun onForwardClick(parentItem: ParentItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "Forward button clicked: " + parentItem.getParentId(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show();
                }
            }

        val childItemClickListener: ChildAdapter.OnChildItemClickListener =
            object : ChildAdapter.OnChildItemClickListener {
                // Add click listener for child items
                override fun onChildItemClick(childItem: ChildItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "2 Child Item clicked: " + childItem.getTitle(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show();
                }
            }

        val parentDictionaryAdapter = ParentAdapter(parentItems, parentItemClickListener, childItemClickListener)
        binding.parent.setLayoutManager(LinearLayoutManager(applicationContext))
        binding.parent.setAdapter(parentDictionaryAdapter)
    }

    // Get resource id from image file
    fun getImageResourceId(resourceName: String, c: Class<*>): Int {
        try {
            val idField: Field = c.getDeclaredField(resourceName.substring(0, resourceName.lastIndexOf('.')))
            return idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            return ru.zettatech.hrv.R.drawable.no_image
        }
    }
}
```

activity_main.xml

``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/white"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar_view"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:elevation="4dp"
        app:titleTextColor="@android:color/white"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/parent"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="?attr/actionBarSize"
        android:layout_marginHorizontal="@dimen/activity_horizontal_margin"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Item.kt

``` kotlin
class Item (private var itemId: Int, private var image: String, private var title: String) {

    fun getItemId(): Int {
        return itemId
    }

    fun setItemId(_itemId: Int) {
        itemId = _itemId
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
```
