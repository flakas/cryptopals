// 11. Write an oracle function and use it to detect ECB.
//
// Now that you have ECB and CBC working:
//
// Write a function to generate a random AES key; that's just 16 random
// bytes.
//
// Write a function that encrypts data under an unknown key --- that is,
// a function that generates a random key and encrypts under it.
//
// The function should look like:
//
// encryption_oracle(your-input)
//  => [MEANINGLESS JIBBER JABBER]
//
// Under the hood, have the function APPEND 5-10 bytes (count chosen
// randomly) BEFORE the plaintext and 5-10 bytes AFTER the plaintext.
//
// Now, have the function choose to encrypt under ECB 1/2 the time, and
// under CBC the other half (just use random IVs each time for CBC). Use
// rand(2) to decide which to use.
//
// Now detect the block cipher mode the function is using each time.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex11.Ex11
import crypto.algorithms.AES

class Ex11Spec extends FunSuite with DiagrammedAssertions {
  val plaintext = ("A"*128).getBytes() // "Hey, you there! Do you think this thing is working yet? Does it?".getBytes()

  test("It detects repeating blocks") {
    AES.isECB(plaintext) should equal(true)
  }

  test("Detects when cipher is using ECB mode") {
    Ex11.predictMode(Ex11.encryptECB(plaintext)) should equal("ECB")
  }

  test("Detects when cipher is using CBC mode") {
    Ex11.predictMode(Ex11.encryptCBC(plaintext)) should equal("CBC")
  }
}
