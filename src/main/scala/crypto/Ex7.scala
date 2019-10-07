// 7. AES in ECB mode
//The Base64-encoded content in this file has been encrypted via AES-128 in ECB mode under the key

//"YELLOW SUBMARINE".
//(case-sensitive, without the quotes; exactly 16 characters; I like "YELLOW SUBMARINE" because it's exactly 16 bytes long, and now you do too).

//Decrypt it. You know the key, after all.

//Easiest way: use OpenSSL::Cipher and give it AES-128-ECB as the cipher.

package crypto.ex7

import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher

import crypto.utils._

object Ex7 {
  def main(args: Array[String]) = {
    val source = scala.io.Source.fromFile("data/ex7.txt")
    val line = source.getLines().toArray.head
    val bytes = Utils.b64Decode(line)
    val key = "YELLOW SUBMARINE"
    val resultBytes = this.decryptAES(bytes, key.getBytes())
    println(resultBytes.map(_.toChar).mkString)
  }

  def decryptAES(bytes: Array[Byte], key: Array[Byte]) = {
    val secretKey = new SecretKeySpec(key, "AES")
    val encipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    encipher.init(Cipher.DECRYPT_MODE, secretKey)
    encipher.doFinal(bytes)
  }
}
