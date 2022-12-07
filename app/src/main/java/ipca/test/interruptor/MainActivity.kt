package ipca.test.interruptor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewWarning = findViewById<TextView>(R.id.textViewWarning)
        textViewWarning.text = "Ligado"

        val verticalSlider = findViewById<VerticalSlider>(R.id.verticalSlider)



        verticalSlider.setOnValueChangeListener {
            if (it >= 75F || verticalSlider.percent >= 75F) {
                textViewWarning.text = "Ligado"
            }
            if (it < 75F || verticalSlider.percent < 75F){
                textViewWarning.text = "Desligado"
            }
        }
    }
}