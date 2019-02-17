package crypto.ex7

import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher

import crypto.utils._

object Ex7 {
  def main(args: Array[String]) = {
    val source = scala.io.Source.fromFile("ex7.txt")
    val line = source.getLines().toArray.head
    val bytes = Utils.b64Decode(line)
    val key = "YELLOW SUBMARINE"
    val secretKey = new SecretKeySpec(key.getBytes(), "AES")
    val encipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    encipher.init(Cipher.DECRYPT_MODE, secretKey)
    val resultBytes = encipher.doFinal(bytes)
    println(resultBytes.map(_.toChar).mkString)
  }
}
