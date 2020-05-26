package crypto.utils

import java.util.Base64
import scala.util.Random
import scala.collection.immutable.Range
import crypto.algorithms.AES

object Oracle {
  val BLOCK_SIZE = 16

  def getRandomKey = Utils.randomBytes(BLOCK_SIZE)
  def encryptAES128ECB(data: Array[Byte], key: Array[Byte] = getRandomKey) = AES.encryptAES128ECB(data, key)
  def encryptAES128CBC(data: Array[Byte], key: Array[Byte] = getRandomKey, iv: Array[Byte] = getRandomKey) = AES.encryptAES128CBC(data, key, iv)

  def randomPadPlaintext(data: Array[Byte], beforeBytes: Range, afterBytes: Range): Array[Byte] = {
    val random = new Random
    val prefix = new Array[Byte](random.shuffle(beforeBytes.toList).head)
    val suffix = new Array[Byte](random.shuffle(afterBytes.toList).head)
    Random.nextBytes(prefix)
    Random.nextBytes(suffix)
    prefix ++ data ++ suffix
  }
}
