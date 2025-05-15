package com.korniykom.todo_list.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class SampleTest  {
    @Test
    fun two_plus_two_is_four() {
        val sum = 2 + 2
        val result = 4

        assertThat(sum).isEqualTo(result)
    }
}