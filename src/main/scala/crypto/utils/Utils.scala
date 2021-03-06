package crypto.utils

import java.util.Base64
import scala.util.Random

object Utils {
  def hexToBin(s: String) = s.sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)

  def binToHex(b: Array[Byte]) = b.map("%02x".format(_)).mkString

  def b64Encode(b: Array[Byte]) = Base64.getEncoder().encodeToString(b)

  def b64Decode(s: String) : Array[Byte] = Base64.getDecoder().decode(s)

  def scoreByVowelFrequency(bytes: Array[Byte]) = {
    val vowels = " aeiou".getBytes()
    bytes.count((b: Byte) => vowels.contains(b)).toFloat / bytes.length
  }

  def xorSingle(bytes: Array[Byte], byte: Byte) = {
    bytes.map((b: Byte) => (b ^ byte).toByte)
  }

  def xorRepeating(bytes: Array[Byte], key: Array[Byte]) = {
    bytes.grouped(key.length).flatMap((piece: Array[Byte]) => piece.zip(key).map((b: (Byte, Byte)) => (b._1 ^ b._2).toByte)).toArray
  }

  def hammingDistance(b1: Array[Byte], b2: Array[Byte]) = {
    def numberOfBitsSet(b: Byte) = (0 to 7).map((i : Int) => (b >>> i) & 1).sum
    (b1.zip(b2).map((x: (Byte, Byte)) => numberOfBitsSet((x._1 ^ x._2).toByte))).sum
  }

  def hammingDistance2(b1: Array[Byte], b2: Array[Byte]) = {
    b1.zip(b2).count(x => x._1 != x._2)
  }

  def pkcs7PadBlock(bytes: Array[Byte], length: Int): Array[Byte] =
    bytes.padTo(length, (length - bytes.length).toByte)

  def randomBytes(length: Int) : Array[Byte] = {
    val array = new Array[Byte](length)
    Random.nextBytes(array)
    array
  }

  def findMostRepeatingBlockFrequency(bytes: Array[Byte], blockSize: Int): Float = {
    val blocks = bytes.grouped(blockSize).toArray
    blocks.map(block => blocks.count(b => b.sameElements(block))).max
  }
}
