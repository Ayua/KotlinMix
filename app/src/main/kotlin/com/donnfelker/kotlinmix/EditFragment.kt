package com.donnfelker.kotlinmix

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.donnfelker.kotlinmix.models.Todo
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*


class EditFragment : Fragment() {

    val realm: Realm = Realm.getDefaultInstance()

    companion object {
        fun newInstance(): EditFragment {
            return EditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                padding = dip(30)
                var title = editText {
                    hintResource = R.string.title_hint
                }
                var desc = editText {
                    hintResource = R.string.description_hint
                }
                button {
                    textResource = R.string.add_todo
                    onClick { view -> createTodoFrom(title, desc) }
                }
            }
        }.view
    }

    /**
     *  A private function to create a TODO item in the database (Realm).
     *
     *  @param title the title edit text.
     *  @param desc the description edit text.
     */
    private fun createTodoFrom(title: EditText, desc: EditText) {
        realm.beginTransaction()
        var todo = realm.createObject(Todo::class.java)
        todo.id = UUID.randomUUID().toString()
        todo.title = title.text.toString()
        todo.description = desc.text.toString()
        realm.commitTransaction()

        // Go back to previous activity
        activity.supportFragmentManager.popBackStack();
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
