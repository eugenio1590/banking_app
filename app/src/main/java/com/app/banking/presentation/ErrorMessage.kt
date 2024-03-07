package com.app.banking.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Represents an error message with associated resources for message text and image.
 *
 * @param message The resource ID for the error message text.
 * @param image The resource ID for the error image.
 */
sealed class ErrorMessage(@StringRes val message: Int, @DrawableRes val image: Int) {
}
