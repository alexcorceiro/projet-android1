package form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojet.R
import com.example.myprojet.tasklist.Task
import java.util.*

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val titleEditText = findViewById<EditText>(R.id.label_1)
        val descEditText = findViewById<EditText>(R.id.label_2)
        val taskEdit = intent.getSerializableExtra("task") as? Task
        descEditText.setText(taskEdit?.description)
        titleEditText.setText(taskEdit?.title)
        val buttonclick = findViewById<Button>(R.id.button_click)
        buttonclick.setOnClickListener{
            val newTask =
                Task(id = taskEdit?.id ?:UUID.randomUUID().toString(),
                    title = titleEditText.text.toString(), description = descEditText.text.toString())
            intent.putExtra("task", newTask)
            setResult(RESULT_OK, intent)
            finish()

        }

    }
}