// 6. Break repeating-key XOR
// The buffer at the following location:
// https://gist.github.com/3132752
// is base64-encoded repeating-key XOR. Break it.
// Here's how:
// a. Let KEYSIZE be the guessed length of the key; try values from 2 to
// (say) 40.
// b. Write a function to compute the edit distance/Hamming distance
// between two strings. The Hamming distance is just the number of
// differing bits. The distance between:
// this is a test
// and:
// wokka wokka!!!
// is 37.
// c. For each KEYSIZE, take the FIRST KEYSIZE worth of bytes, and the
// SECOND KEYSIZE worth of bytes, and find the edit distance between
// them. Normalize this result by dividing by KEYSIZE.
// d. The KEYSIZE with the smallest normalized edit distance is probably
// the key. You could proceed perhaps with the smallest 2-3 KEYSIZE
// values. Or take 4 KEYSIZE blocks instead of 2 and average the
// distances.
// e. Now that you probably know the KEYSIZE: break the ciphertext into
// blocks of KEYSIZE length.
// f. Now transpose the blocks: make a block that is the first byte of
// every block, and a block that is the second byte of every block, and
// so on.
// g. Solve each block as if it was single-character XOR. You already
// have code to do this.
// e. For each block, the single-byte XOR key that produces the best
// looking histogram is the repeating-key XOR key byte for that
// block. Put them together and you have the key.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex6.Ex6
import crypto.utils.Utils

class Ex6Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex6.txt"

  test("Finds the right key") {
    val correctKey = "Terminator X: Bring the noise"
    val key = Ex6.findKey(loadData())
    key should equal(correctKey)
  }

  test("Correctly decrypts the message") {
    val partialMessage = "I'm back and I'm ringin' the bell"
    val correctKey = "Terminator X: Bring the noise"
    val data = loadData()
    Ex6.decryptMessage(data, correctKey) should startWith(partialMessage)
  }

  def loadData() : Array[Byte] = {
    val source = scala.io.Source.fromFile("data/ex6.txt")
    val data = source.mkString.trim
    val buffer = Utils.b64Decode(data)
    source.close()
    buffer
  }
}
