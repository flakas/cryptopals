//12. Byte-at-a-time ECB decryption (Simple)
//Copy your oracle function to a new function that encrypts buffers under ECB mode using a consistent but unknown key (for instance, assign a single random key, once, to a global variable).

//Now take that same function and have it append to the plaintext, BEFORE ENCRYPTING, the following string:

//Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg
//aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq
//dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg
//YnkK
//Spoiler alert.
//Do not decode this string now. Don't do it.

//Base64 decode the string before appending it. Do not base64 decode the string by hand; make your code do it. The point is that you don't know its contents.

//What you have now is a function that produces:

//AES-128-ECB(your-string || unknown-string, random-key)
//It turns out: you can decrypt "unknown-string" with repeated calls to the oracle function!

//Here's roughly how:

//Feed identical bytes of your-string to the function 1 at a time --- start with 1 byte ("A"), then "AA", then "AAA" and so on. Discover the block size of the cipher. You know it, but do this step anyway.
//Detect that the function is using ECB. You already know, but do this step anyways.
//Knowing the block size, craft an input block that is exactly 1 byte short (for instance, if the block size is 8 bytes, make "AAAAAAA"). Think about what the oracle function is going to put in that last byte position.
//Make a dictionary of every possible last byte by feeding different strings to the oracle; for instance, "AAAAAAAA", "AAAAAAAB", "AAAAAAAC", remembering the first block of each invocation.
//Match the output of the one-byte-short input to one of the entries in your dictionary. You've now discovered the first byte of unknown-string.
//Repeat for the next byte.
//Congratulations.
//This is the first challenge we've given you whose solution will break real crypto. Lots of people know that when you encrypt something in ECB mode, you can see penguins through it. Not so many of them can decrypt the contents of those ciphertexts, and now you can. If our experience is any guideline, this attack will get you code execution in security tests about once a year.

package crypto.ex12

import scala.util.Random
import crypto.utils._
import crypto.algorithms.AES

object Ex12 {
  def main(args: Array[String]) = {
    val key = Oracle.getRandomKey
    val secret = Utils.b64Decode("Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkgaGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBqdXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUgYnkK")
    val oracle : Array[Byte] => Array[Byte] = buildOracle(secret, key)
    val blockSize = findBlockSize(oracle)
    assert(AES.isECB(oracle(("A"*128).getBytes)))
    val decryptedSecret = breakSecret(oracle, blockSize)
    println(decryptedSecret.map(_.toChar).mkString)
  }

  def buildOracle(secret: Array[Byte], key: Array[Byte])(data: Array[Byte]) = {
    Oracle.encryptAES128ECB(data ++ secret, key)
  }

  def breakSecret(oracle: Array[Byte] => Array[Byte], blockSize: Int): Array[Byte] = {
    def break(targetByte: Int, secretLength: Int, decryptedSoFar: Array[Byte]): Array[Byte] = {
      // Add enough padding to align the target byte as the last byte in the block
      val paddingLength = blockSize - targetByte % blockSize - 1
      val alignmentPadding = ("A" * paddingLength).getBytes

      // Make a dictionary by encrypting known bytes and 1 guessed byte with the unknown key.
      val allKnownBytes = ("A"*(blockSize - 1)).getBytes ++ decryptedSoFar
      val knownTargetBytes = allKnownBytes.slice(targetByte, targetByte+blockSize)
      val dictionary = buildDictionary(knownTargetBytes, blockSize, oracle)

      // Get the actual target block bytes using alignment padding.
      // No need to pass in decryptedSoFar bytes, as they're already part of the secret
      val blockStartOffset = targetByte - targetByte % blockSize
      val targetBlock = oracle(alignmentPadding).slice(blockStartOffset, blockStartOffset + blockSize)

      val decryptedByte = dictionary.find(_._1.sameElements(targetBlock)).get._2
      val plaintext = decryptedSoFar :+ decryptedByte

      if (plaintext.length >= secretLength) {
        plaintext
      } else {
        break(targetByte + 1, secretLength, plaintext)
      }
    }

    val secretLength = findUnpaddedSecretLength(oracle)._1
    break(0, secretLength, Array())
  }

  def findBlockSize(oracle: Array[Byte] => Array[Byte]) = {
    // Pad the secret up to the first known full block, otherwise we might find
    // a shorter (and wrong) block size
    val (secretLength, initialPaddingLength) = findUnpaddedSecretLength(oracle)
    val startLength = oracle(("A"*(initialPaddingLength + 1)).getBytes).length

    // How many more bytes does it take for the ciphertext's length to increase?
    val blockSize = (1 to 128)
      .takeWhile((x: Int) => oracle(Array().padTo(initialPaddingLength + x, 0)).length <= startLength)
      .last
    blockSize
  }

  // Adds up to blockSize bytes until resulting cipher increases in length,
  // indicating that data length rolled over to the next block
  def findUnpaddedSecretLength(oracle: Array[Byte] => Array[Byte]): (Int, Int) = {
    val startLength = oracle(Array()).length
    val paddingLength = (0 to 128)
      .takeWhile((x: Int) => oracle(Array().padTo(x, 0)).length <= startLength)
      .last
    (startLength - paddingLength, paddingLength)
  }

  // Uses ECB statelessness to encrypt the first fully known block using the unknown key,
  // as it will be exactly the same as the block later in the ciphertext
  def buildDictionary(base: Array[Byte], blockSize: Int, oracle: Array[Byte] => Array[Byte]) = {
    (0 to Byte.MaxValue)
      .map(_.toByte)
      .map((lastByte: Byte) => (oracle(base.appended(lastByte)).slice(0, blockSize), lastByte))
  }

}
