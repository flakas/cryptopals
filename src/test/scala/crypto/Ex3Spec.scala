// 3. Single-character XOR Cipher
//
// The hex encoded string:
//
// 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
//
// ... has been XOR'd against a single character. Find the key, decrypt
// the message.
//
// Write code to do this for you. How? Devise some method for "scoring" a
// piece of English plaintext. (Character frequency is a good metric.)
// Evaluate each output and choose the one with the best score.
//
// Tune your algorithm until this works.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex3.Ex3
import crypto.utils.Utils

class Ex3Spec extends FunSuite with DiagrammedAssertions {
  val initial = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"

  test("finds the correct key") {
    val correctKey = "X"
    Ex3.solveKey(Utils.hexToBin(initial)) should equal('X')
  }

  test("decodes the message") {
    Ex3.solveMessage(Utils.hexToBin(initial)) should equal("Cooking MC's like a pound of bacon")
  }
}
