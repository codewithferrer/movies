package es.trespies.movies

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class FooTest {

    @Test
    fun testSaySomethingOk() {
        val bar = Mockito.mock(Bar::class.java)

        Mockito.`when`(bar.sayHello()).thenReturn("goodbye")

        val foo = Foo(bar)

        val result = foo.saySomething()

        verify(bar, times(1)).sayHello()
        assertThat(result, CoreMatchers.`is`("goodbye"))
    }
}