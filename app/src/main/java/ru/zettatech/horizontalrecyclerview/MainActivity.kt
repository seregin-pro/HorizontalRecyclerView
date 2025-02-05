package ru.zettatech.horizontalrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.zettatech.horizontalrecyclerview.category.Category
import ru.zettatech.horizontalrecyclerview.constant.Constant
import ru.zettatech.horizontalrecyclerview.databinding.ActivityMainBinding
import ru.zettatech.horizontalrecyclerview.db.DBHelper
import ru.zettatech.hrv.adapter.ChildAdapter
import ru.zettatech.hrv.adapter.ParentAdapter
import ru.zettatech.hrv.item.ChildItem
import ru.zettatech.hrv.item.ParentItem
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarView)

        // Create parent category list
        val filterData = HashMap<String, String>()
        filterData.put(Constant.PARENT_ID, "0")
        val parents: ArrayList<Category> = DBHelper.getInstance(applicationContext)!!.getCategories(filterData)

        val parentItems = mutableListOf<ParentItem>().apply {
            for (i in parents.indices) {

                // Create child category list
                filterData.put(Constant.PARENT_ID, parents.get(i).getCategoryId().toString())
                val childList: ArrayList<Category> = DBHelper.getInstance(applicationContext)!!.getCategories(filterData)

                val childItems = mutableListOf<ChildItem>().apply {
                    for (j in childList.indices) {
                        val resourceId: Int = getImageResourceId(childList.get(j).getImage(), R.drawable::class.java)
                        add(ChildItem(resourceId, childList.get(j).getTitle(), ""))
                    }
                }

                // Add the parent item with childs
                add(ParentItem(parents.get(i).getCategoryId(), parents.get(i).getTitle(), childItems))
            }
        }

        val parentItemClickListener: ParentAdapter.OnParentItemClickListener =
            object : ParentAdapter.OnParentItemClickListener {

                // Add click listener for parent items
                override fun onParentItemClick(parentItem: ParentItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "Parent Item clicked: " + parentItem.getTitle(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show()
                }

                // Add click listener for button forward
                override fun onForwardClick(parentItem: ParentItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "Forward button clicked: " + parentItem.getParentId(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }

        val childItemClickListener: ChildAdapter.OnChildItemClickListener =
            object : ChildAdapter.OnChildItemClickListener {
                // Add click listener for child items
                override fun onChildItemClick(childItem: ChildItem, position: Int) {
                    val snackbar : Snackbar = Snackbar.make(
                        binding.root, "Child Item clicked: " + childItem.getTitle(),
                        Snackbar.LENGTH_LONG)
                    snackbar.show()
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