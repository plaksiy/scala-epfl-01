package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements presented in both sets") {
    val s1 = (x: Int) => (x > -1) && (x < 11)
    val s2 = (x: Int) => (x > 4) && (x < 16)
    val s = intersect(s1, s2)
    assert(contains(s, 5), "Intersect 5")
    assert(contains(s, 7), "Intersect 7")
    assert(contains(s, 10), "Intersect 10")
    assert(!contains(s, -5), "Intersect -5")
    assert(!contains(s, 4), "Intersect 4")
    assert(!contains(s, 11), "Intersect 11")
    assert(!contains(s, 15), "Intersect 15")
  }

  test("difference contains all elements presented in first set but not in second set") {
    val s1 = (x: Int) => (x > -1) && (x < 11)
    val s2 = (x: Int) => (x > 4) && (x < 16)
    val s = diff(s1, s2)
    assert(contains(s, 0), "Difference 0")
    assert(contains(s, 2), "Difference 2")
    assert(contains(s, 4), "Difference 4")
    assert(!contains(s, -5), "Difference -5")
    assert(!contains(s, 5), "Difference 5")
    assert(!contains(s, 7), "Difference 7")
    assert(!contains(s, 15), "Difference 15")
  }

  test("filter select only elements from set that are accepted by predicate") {
    val s1 = (x: Int) => (x > -1) && (x < 11)
    val s = filter(s1, x => x > 4)
    assert(contains(s, 5), "Filter 5")
    assert(contains(s, 7), "Filter 7")
    assert(contains(s, 10), "Filter 10")
    assert(!contains(s, -5), "Filter -5")
    assert(!contains(s, 3), "Filter 3")
    assert(!contains(s, 11), "Filter 11")
    assert(!contains(s, 15), "Filter 15")
  }

  test("forall check that whether all bounded integers within `s` satisfy `p`") {
    val s = (x: Int) => x % 500 == 0
    assert(forall(s, x => x % 100 == 0), "Forall 100")
    assert(!forall(s, x => x % 100 == 0 && x != 0), "Forall 100 except 0")
  }

  test("exists check that at least one element of bounded integers within `s` satisfy `p`") {
    val s = (x: Int) => x % 500 == 0
    assert(exists(s, x => x % 100 == 0 && x < -999), "Exists 999")
    assert(!exists(s, x => x % 100 == 0 && x > 1500), "Exists 1500 ")
  }

  test("map returns a set transformed by applying `f` to each element of `s`") {
    val s = (x: Int) => x % 500 == 0
    val sMap = map(s, x => x + 1)
//    println(FunSets.toString(s))
//    println(FunSets.toString(sMap))
    assert(contains(s, 500), "Contains 500")
    assert(!contains(s, 501), "NOT Contains 501")
    assert(contains(sMap, 501), "Transformed set Contains 501")
    assert(!contains(sMap, 500), "Transformed set NOT Contains 500")
  }

}
