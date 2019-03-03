package com.example.chatdat.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.example.chatdat.R
import com.example.chatdat.Services.AuthService
import com.example.chatdat.Services.UserDataService
import com.example.chatdat.Utilities.BROADCAT_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View)
    {
        val random  = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color==0)
        {
            userAvatar = "light$avatar"
        }else
        {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)

    }

    fun generateColorClicked(view: View)
    {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble()/255
        val savedG = r.toDouble()/255
        val savedB = r.toDouble()/255

        avatarColor="[$savedR, $savedG, $savedB, 1]"
        

    }

    fun createUserClicked(view: View)
    {
        enableSpinner(true)
        val userName = createUserNameTxt.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
        {
            AuthService.registerUser(this, email, password){ registerSuccess ->
                if (registerSuccess)
                {
                    AuthService.loginUser(this, email, password){loginSuccess ->
                        if(loginSuccess)
                        {
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess)
                                {
                                    val userDataChange = Intent(BROADCAT_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }

                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }

        } else
        {
            Toast.makeText(this, "Make sure username, email and password are filled in.",Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }




    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong, please tyr again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable)
        {
            createSpinner.visibility = View.VISIBLE
        } else
        {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
