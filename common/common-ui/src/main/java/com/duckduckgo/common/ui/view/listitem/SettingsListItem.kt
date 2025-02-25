/*
 * Copyright (c) 2024 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.common.ui.view.listitem

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.duckduckgo.common.ui.view.StatusIndicator
import com.duckduckgo.common.ui.view.gone
import com.duckduckgo.common.ui.view.show
import com.duckduckgo.common.ui.view.text.DaxTextView
import com.duckduckgo.common.ui.viewbinding.viewBinding
import com.duckduckgo.mobile.android.R
import com.duckduckgo.mobile.android.databinding.ViewSettingsListItemBinding

class SettingsListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.oneLineListItemStyle,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSettingsListItemBinding by viewBinding()

    val primaryText: DaxTextView
        get() = binding.primaryText
    val leadingIcon: ImageView
        get() = binding.leadingIcon
    val betaPill: ImageView
        get() = binding.betaPill
    val statusIndicator: StatusIndicator
        get() = binding.statusIndicator

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.SettingsListItem,
            0,
            R.style.Widget_DuckDuckGo_OneLineListItem,
        ).apply {

            primaryText.text = getString(R.styleable.SettingsListItem_primaryText)

            val leadingIconRes = getResourceId(R.styleable.SettingsListItem_leadingIcon, 0)
            if (leadingIconRes != 0) {
                leadingIcon.setImageResource(leadingIconRes)
                leadingIcon.show()
            } else {
                leadingIcon.gone()
            }

            setPillVisible(getBoolean(R.styleable.SettingsListItem_showBetaPill, false))

            val isOn = getBoolean(R.styleable.SettingsListItem_isOn, false)
            statusIndicator.setStatus(isOn)

            recycle()
        }
    }

    /** Sets the item click listener */
    fun setClickListener(onClick: () -> Unit) {
        binding.root.setOnClickListener { onClick() }
    }

    fun setStatus(isOn: Boolean) {
        statusIndicator.setStatus(isOn)
    }

    private fun setPillVisible(isVisible: Boolean) {
        betaPill.isVisible = isVisible
    }
}
