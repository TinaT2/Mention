package com.tt2.mentionsample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tt2.mention.util.MentionUtil
import com.tt2.mentionsample.model.MentionSampleModel

class MainActivity : AppCompatActivity() {
    private lateinit var input:EditText
    private lateinit var result:TextView
    private lateinit var button: Button
    private lateinit var mentionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(MentionSampleModel("Tina","",0),MentionSampleModel("Sara","",1),MentionSampleModel("John","",2))
        initUiViews()
        initMention(list)
    }
    private fun initUiViews(){
        result = findViewById(R.id.textView_result)
        input = findViewById(R.id.input)
        button = findViewById(R.id.button)
        mentionRecyclerView = findViewById(R.id.recyclerView_mentionList)
    }
    private fun initMention(resultMemberList: List<MentionSampleModel>) {
        MentionUtil(
            clickCallback = ::backendTextCallBackToSend,
            input = input,
            button = button,
            recyclerViewMentions = mentionRecyclerView,
            memberList = resultMemberList,
            context = applicationContext,
            placeHolder = R.drawable.all_avatarplaceholder,
            prefixConvert = " ***{user_reference_id:",
            postfixConvert = "*** "
        )
    }

    private fun backendTextCallBackToSend(convertedText: String, boldText: String) {
        val text = "Processed text: $convertedText \n Unprocessed text: $boldText "
     result.text = text
    }
}