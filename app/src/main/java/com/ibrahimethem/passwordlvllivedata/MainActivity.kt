package com.ibrahimethem.passwordlvllivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var color : Int = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sınıfımızdan bir nesne oluşturduk
        val passwordStrengthCalculator = PasswordStrengthCalculator()
        //dinlenecek edittext'e TextWatcher den kalıtım alan sınıfımızın nesnesini verdik
        password_input.addTextChangedListener(passwordStrengthCalculator)

        //observers-gözlemlemeler
        //dinlenen edittext'imiz oluşturduğumuz sınıfta MutableLiveData değişkenlerimize değerler vericek
        //değerlere göre burada görünüm değiştirilecek
        passwordStrengthCalculator.strengthLevel.observe(this){ strengthLevel ->
            displayStrengthLevel(strengthLevel)
        }

        passwordStrengthCalculator.strengthColor.observe(this){strengthColor ->
            color = strengthColor
        }

        passwordStrengthCalculator.lowerCase.observe(this){value ->
            displayPasswordSuggestions(value,lowerCase_img,lowerCase_txt)
        }
        passwordStrengthCalculator.upperCase.observe(this){value ->
            displayPasswordSuggestions(value,upperCase_img,upperCase_txt)
        }

        passwordStrengthCalculator.digit.observe(this){value ->
            displayPasswordSuggestions(value,digit_img,digit_txt)
        }

        passwordStrengthCalculator.specialChar.observe(this){value ->
            displayPasswordSuggestions(value,specialChar_img,specialChar_txt)
        }

    }

    private fun displayPasswordSuggestions(
        value: Int,
        specialcharImg: ImageView,
        specialcharTxt: TextView
    ) {
        if (value == 1){
            specialcharImg.setColorFilter(ContextCompat.getColor(this,R.color.bulletproof))
            specialcharTxt.setTextColor(ContextCompat.getColor(this,R.color.bulletproof))
        }else{
            specialcharImg.setColorFilter(ContextCompat.getColor(this,R.color.darkGray))
            specialcharTxt.setTextColor(ContextCompat.getColor(this,R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        button.isEnabled = strengthLevel == StrengthLevel.BULLETPROOF

        strength_level_txt.text = strengthLevel.name
        strength_level_txt.setTextColor(ContextCompat.getColor(this,color))
        strength_level_indicator.setBackgroundColor(ContextCompat.getColor(this,color))
    }
}