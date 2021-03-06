package ex16

import java.util.function.Predicate

// import java.util.function.Predicate

// 고차 함수(Higher order function)
// : 함수의 객체를 인자로 받거나 함수를 반환하는 함수

// Why?
// 1) 다양한 시나리오에서 동작하는 함수의 코드 중복을 없앨 수 있다.
//   => 함수를 정책으로 사용할 수 있다.


// 고차 함수의 방식을 자바에서 구현하는 방법
//  => 동작 파라미터화 설계


// Kotlin은 Collection의 인터페이스에 변경이 있습니다.
//          List<E>: Immutable Collection Interface
//   MutableList<E>: Mutable
//   ArrayList
//   LinkedList

//          Set<E>: Immutable
//   MutableSet<E>: Mutable
//   TreeSet
//   HashSet

//           Map<K, V>: Immutable
//    MutableMap<K, V>: Mutable
//   TreeMap
//   HashMap

// 선형적인 자료구조에서 filter의 알고리즘은 변하지 않는다.
// 사용자마다 filter 하고자 하는 정책은 변한다.
//  => 변하지 않는 알고리즘에서 변하는 정책은 분리되어야 한다.
//  => '공통성' 과 '가변성'은 분리되어야 한다.
/*
fun filterEven(data: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (e in data) {
        if (e % 2 == 0)
            result.add(e)
    }

    return result
}

fun filterOdd(data: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (e in data) {
        if (e % 2 == 1)
            result.add(e)
    }

    return result
}


fun main(args: Array<String>) {
    val data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // 짝수 filtering
    var result = filterEven(data)
    println(result)

    // 홀수 filtering
    result = filterOdd(data)
    println(result)
}
*/

// Java는 정책을 인터페이스 기반의 정책 클래스를 통해 분리한다.
//  => Design Pattern: Sterategy Pattern
//   1) Interface - 동작 파라미터화 설계

//interface Predicate<E> {
//    fun test(e: E): Boolean
//}

fun filter(data: List<Int>, predicate: Predicate<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (e in data) {
        if (predicate.test(e))
            result.add(e)
    }

    return result
}

// 새로운 정책 클래스를 만들어야 합니다.
/*
class EvenPredicate : Predicate<Int> {
    override fun test(e: Int) = e % 2 == 0
}
*/

object EvenPredicate : Predicate<Int> {
    override fun test(e: Int) = e % 2 == 0
}

class OddPredicate : Predicate<Int> {
    override fun test(e: Int) = e % 2 == 1
}

fun filterEven(data: List<Int>) = filter(data, EvenPredicate)
fun filterOdd(data: List<Int>) = filter(data, OddPredicate())

fun main(args: Array<String>) {
    val data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // 짝수 filtering
    var result = filterEven(data)
    println(result)

    // 홀수 filtering
    result = filterOdd(data)
    println(result)

    // e 가 5 이상만 filter 하고 싶다.
    // - Kotlin에서 인터페이스 기반의 정책을 사용하면, 람다를 사용할 수 없습니다.

    // 만약 코틀린에서 자바의 Functional Interface를 정책으로 사용하고 있다면,
    // '람다'를 사용하는 것을 허용합니다.
    //  => SAM(Single Abstract Method)

    result = filter(data, object: Predicate<Int> {
        override fun test(e: Int) = e >= 5
    })

    // SAM 변환
    result = filter(data, Predicate { e -> e >= 5 })

    // result = filter(data) { it >= 5 }

    println(result)
}







