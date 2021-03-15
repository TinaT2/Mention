package com.tt2.mention.util

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tt2.mention.IMention
import com.tt2.mention.MentionListAdapter
import java.util.regex.Pattern


class MentionUtil(
    private val input: EditText,
    private val button: Button,
    private val recyclerViewMentions: RecyclerView,
    private val memberList: List<IMention>,
    private val context: Context,
    private val callbackListener:ClickCallback,
    private val prefixConvert: String?=null,
    private val postfixConvert: String?=null,
    @DrawableRes private val placeHolder: Int
) {
    interface ClickCallback{
        fun callback(convertedText: String, boldText: String)
    }

    private lateinit var mentionAdapter: MentionListAdapter

    var lastMatchWord = ""
    var lastMatchWordIndex = 0
    var userVsProgrammaticallyChange = true

    init {
        initUiViews()
    }

    private fun initUiViews() {
        button.setOnClickListener { textToBackendFormat(prefixConvert,postfixConvert) }
        mentionListener()
        initMentionAdapter()
    }

    private fun initMentionAdapter() {
        mentionAdapter = MentionListAdapter(::onMentionItemClicked, placeHolder)
        recyclerViewMentions.layoutManager =
            LinearLayoutManager(context)
        recyclerViewMentions.adapter = mentionAdapter
        mentionAdapter.setAllData(memberList)
    }


    private fun mentionListener() {
        val mentionRegex = "\\B@[\\w\\s]*"
        try {
            input.doOnTextChanged { text, _, _, _ ->
                if (userVsProgrammaticallyChange) {
                    if (!text.isNullOrEmpty() && !text.isNullOrBlank()) {
                        val textToSearch = text.subSequence(0, input.selectionStart)
                        val lastMentionRegexPattern = Pattern.compile(mentionRegex)
                        val lastMentionRegexMatcher =
                            lastMentionRegexPattern.matcher(textToSearch)
                        val lastLinesMentionList = mutableListOf<String>()

                        while (lastMentionRegexMatcher.find()) {
                            lastLinesMentionList.add(lastMentionRegexMatcher.group())
                        }
                        if (lastLinesMentionList.size != 0) {
                            lastMatchWord = lastLinesMentionList.last()
                            lastMatchWordIndex = textToSearch.length - lastMatchWord.length
                            if (lastMatchWord.isNotEmpty() && textToSearch.subSequence(
                                    lastMatchWordIndex,
                                    textToSearch.length
                                ).toString() == lastMatchWord
                            ) {
                                setMentionListData()
                            } else
                                hideMentionList()
                        } else
                            hideMentionList()
                    } else
                        hideMentionList()
                    false
                }
                userVsProgrammaticallyChange = true
            }
        } catch (exception: StringIndexOutOfBoundsException) {
        }
    }

    private fun setMentionListData() {
        recyclerViewMentions.visibility = View.VISIBLE

        val matchList = mutableListOf<IMention>()
        val lastMatchWithNoSign = lastMatchWord.removePrefix("@").replace('_', ' ')
        mentionAdapter.getAllData()
            .forEach { if (it.name.contains(lastMatchWithNoSign, true)) matchList.add(it) }
        mentionAdapter.setData(
            matchList
        )
        colorIfComplete(lastMatchWithNoSign)
    }

    private fun colorIfComplete(lastMatchWithNoSign: String) {
        if (mentionAdapter.getData()
                .find { it.name.equals(lastMatchWithNoSign, ignoreCase = true) } != null
        )
            colorWord(lastMatchWithNoSign)
        else if (mentionAdapter.getData()
                .find { it.name.contains(lastMatchWithNoSign, ignoreCase = true) } != null
        )
            blackWord(lastMatchWithNoSign)
    }

    private fun hideMentionList() {
        recyclerViewMentions.visibility = View.GONE
        lastMatchWord = ""
    }

    private fun onMentionItemClicked(memberPresenter: IMention) {
        colorWord(memberPresenter.name)
        hideMentionList()
    }

    private fun blackWord(name: String) {
        val mentionName = "@$name".replace('_', ' ')
        val wordToSpan: Spannable =
            SpannableString(mentionName)

        wordToSpan.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            mentionName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        userVsProgrammaticallyChange = false
        input.editableText.replace(
            lastMatchWordIndex,
            lastMatchWordIndex + lastMatchWord.length,
            wordToSpan
        )
    }

    private fun colorWord(name: String) {
        val mentionName = "@$name".replace(' ', '_')
        val wordToSpan: Spannable =
            SpannableString(mentionName)

        wordToSpan.setSpan(
            ForegroundColorSpan(Color.BLUE),
            0,
            mentionName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (wordToSpan.contains('_')) {
            wordToSpan.setSpan(
                ForegroundColorSpan(Color.TRANSPARENT),
                mentionName.indexOf('_'),
                mentionName.indexOf('_') + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        Log.v("mentionWord", name)
        userVsProgrammaticallyChange = false
        input.editableText.replace(
            lastMatchWordIndex,
            lastMatchWordIndex + lastMatchWord.length,
            wordToSpan
        )
    }

    private fun textToBackendFormat(prefixConvert: String?, postfixConvert: String?) {
        var convertedText = input.text.toString()
        var patternToConvert = ""
        var boldText = input.text.toString()
        var showWord = ""

        mentionAdapter.getAllData().forEach { member ->
            val guess = "@" + member.name.replace(' ', '_').trim()
            var index: Int = convertedText.indexOf(guess, ignoreCase = true)
            while (index >= 0) {
                patternToConvert = prefixConvert + member.id + postfixConvert
                convertedText = convertedText.replaceRange(
                    index,
                    index + guess.length,
                    patternToConvert
                )
                index = convertedText.indexOf(guess, patternToConvert.length, true)
            }
        }
        mentionAdapter.getAllData().forEach { member ->
            val guess = "@" + member.name.replace(' ', '_').trim()
            var index: Int = boldText.indexOf(guess, ignoreCase = true)
            while (index >= 0) {
                showWord = " ***@${member.name}*** "
                boldText = boldText.replaceRange(
                    index,
                    index + guess.length,
                    showWord
                )
                index = boldText.indexOf(guess, index+showWord.length, true)
            }
        }
        hideMentionList()
        callbackListener.callback(convertedText, boldText)
        Log.v("processedText", "$convertedText blodText: $boldText")
    }
}