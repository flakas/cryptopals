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

package crypto.ex6

import crypto.utils._
import crypto.ex3._

object Ex6 {
  def main(args: Array[String]) = {
    val source = scala.io.Source.fromFile("data/ex6.txt")
    val data = source.mkString.trim
    val buffer = Utils.b64Decode(data)
    val key = findKey(buffer)
    println("KEY: " + key)
    val messageString = decryptMessage(buffer, key)
    println("MESSAGE:\n" + messageString)
    source.close()
  }

  def findKey(bytes: Array[Byte]) = {
    val lengths = (2 to 40).map(n => (n, calculateDistance(bytes, n, 3))).sortBy(x => x._2)
    val key = lengths.take(3).map(_._1).map(length => solveKeyByLength(bytes, length)).maxBy(key => Utils.score(Utils.xorRepeating(bytes, key)))
    key.map(_.toChar).mkString
  }

  def decryptMessage(bytes: Array[Byte], key: String) = {
    val message = Utils.xorRepeating(bytes, key.getBytes())
    message.map(_.toChar).mkString
  }

  def solveKeyByLength(bytes: Array[Byte], keyLength: Int) = {
    val blocks = bytes.grouped(keyLength)
    val partitionedBlocks = blocks.toArray.transpose
    partitionedBlocks.map(block => Ex3.solveSingleCharacterXor(block)._1)
  }

  def calculateDistance(bytes: Array[Byte], keyLength: Int, numOfLengths: Int) = {
    (1 to numOfLengths).map(n => Utils.hammingDistance(bytes.drop((n-1)*keyLength).take(keyLength), bytes.drop(n*keyLength).take(keyLength))).sum.toFloat / (numOfLengths*keyLength)
  }
}

