package crypto.utils

import java.util.Base64
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher

object Utils {
  def hexToBin(s: String) = s.sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)

  def binToHex(b: Array[Byte]) = b.map("%02x".format(_)).mkString

  def b64Encode(b: Array[Byte]) = Base64.getEncoder().encodeToString(b)

  def b64Decode(s: String) : Array[Byte] = Base64.getDecoder().decode(s)

  def score(bytes: Array[Byte]) = {
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

  def encodeAES128ECB(bytes: Array[Byte], key: Array[Byte]) = {
    val secretKey = new SecretKeySpec(key, "AES")
    //val encipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val encipher = Cipher.getInstance("AES/ECB/NoPadding")
    encipher.init(Cipher.ENCRYPT_MODE, secretKey)
    encipher.doFinal(bytes)
  }

  def decodeAES128ECB(bytes: Array[Byte], key: Array[Byte]) = {
    val secretKey = new SecretKeySpec(key, "AES")
    val encipher = Cipher.getInstance("AES/ECB/NoPadding")
    encipher.init(Cipher.DECRYPT_MODE, secretKey)
    encipher.doFinal(bytes)
  }
}
