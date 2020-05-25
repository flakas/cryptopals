package crypto.algorithms.aes

import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import crypto.utils.Utils

object AES {
  val BLOCK_SIZE = 16

  def encryptAES128ECB(bytes: Array[Byte], key: Array[Byte]) = {
    val secretKey = new SecretKeySpec(key, "AES")
    //val encipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val encipher = Cipher.getInstance("AES/ECB/NoPadding")
    encipher.init(Cipher.ENCRYPT_MODE, secretKey)
    //encipher.doFinal(Utils.pkcs7PadBlock(bytes, BLOCK_SIZE))
    def encrypt(b: Array[Byte]) : Array[Byte] = b match {
      case Array() => Array()
      case _ => {
        encipher.doFinal(Utils.pkcs7PadBlock(b.take(BLOCK_SIZE), BLOCK_SIZE)) ++ encrypt(b.drop(BLOCK_SIZE))
      }
    }
    encrypt(bytes)
  }

  def decryptAES128ECB(bytes: Array[Byte], key: Array[Byte]) = {
    val secretKey = new SecretKeySpec(key, "AES")
    val encipher = Cipher.getInstance("AES/ECB/NoPadding")
    encipher.init(Cipher.DECRYPT_MODE, secretKey)
    encipher.doFinal(bytes)
  }

  def encryptAES128CBC(bytes: Array[Byte], key: Array[Byte], iv: Array[Byte]) = {
    def encrypt(b: Array[Byte], ivec: Array[Byte]) : Array[Byte] = b match {
      case Array() => Array()
      case _ => {
        val iv2 = encryptAES128ECB(Utils.xorRepeating(b.take(BLOCK_SIZE), ivec), key)
        iv2 ++ encrypt(b.drop(BLOCK_SIZE), iv2)
      }
    }
    encrypt(bytes, iv)
  }

  def decryptAES128CBC(bytes: Array[Byte], key: Array[Byte], iv: Array[Byte]) : Array[Byte] = {
    Utils.xorRepeating(decryptAES128ECB(bytes.take(BLOCK_SIZE), Utils.pkcs7PadBlock(key, BLOCK_SIZE)), iv) ++ (bytes.sliding(32, BLOCK_SIZE).toArray.flatMap(b =>
        Utils.xorRepeating(decryptAES128ECB(b.drop(BLOCK_SIZE), Utils.pkcs7PadBlock(key, BLOCK_SIZE)), b.take(BLOCK_SIZE))))
  }

  // Contains repeating blocks
  def isECB(bytes: Array[Byte]) : Boolean = {
    Utils.findMostRepeatingBlockFrequency(bytes, BLOCK_SIZE) > 1
  }
}
