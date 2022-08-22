package com.challenge.carapp.components

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText

class FilterManager(
    private val makeView: EditText,
    private val modelView: EditText,
    private val listener: FilterChangeListener
) {

    init {
        makeView.setTextChangeListener()
        makeView.setActionListener()
        modelView.setTextChangeListener()
        modelView.setActionListener()
    }

    private fun EditText.setTextChangeListener() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(modelText: Editable) {
                listener.onSearchClicked(
                    make = makeView.text.toString(),
                    model = modelView.text.toString()
                )
            }
        })
    }

    private fun EditText.setActionListener() {
        setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener.onSearchClicked(
                    make = makeView.text.toString(),
                    model = modelView.text.toString(),
                    closeKeyboard = true
                )
                v.clearFocus()
                true
            } else false
        }
    }

    interface FilterChangeListener {
        fun onSearchClicked(make: String, model: String, closeKeyboard: Boolean = false)
    }
}