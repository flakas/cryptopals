import org.scalatest._
import org.scalatest.Matchers._
import crypto.utils.Utils

class UtilsSpec extends FunSuite with DiagrammedAssertions {

  test("randomBytes returns an array of bytes with the specified size") {
    val expectedSize = 16
    Utils.randomBytes(expectedSize).size should equal(expectedSize)
  }
}
