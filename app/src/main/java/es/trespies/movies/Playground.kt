package es.trespies.movies

class Bar {
    fun sayHello(): String = "Hello"
}

class Foo constructor(private val bar: Bar) {
    fun saySomething(): String = bar.sayHello()
}