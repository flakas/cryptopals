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
import crypto.ex7.Ex7
import crypto.utils.Utils

class Ex7Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex7.txt"

  test("Decrypts AES 128 EBC") {
    val source = scala.io.Source.fromFile("data/ex7.txt")
    val line = source.getLines().toArray.head
    val bytes = Utils.b64Decode(line)
    val key = "YELLOW SUBMARINE".getBytes()
    val target = "I'm back and I'm ringin' the bell"
    Ex7.decryptAES(bytes, key).map(_.toChar).mkString should startWith(target)
  }
}
