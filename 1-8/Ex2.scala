// 2. Fixed XOR
//
// Write a function that takes two equal-length buffers and produces
// their XOR sum.
//
// The string:
//
// 1c0111001f010100061a024b53535009181c
//
// ... after hex decoding, when xor'd against:
//
// 686974207468652062756c6c277320657965
//
// ... should produce:
//
// 746865206b696420646f6e277420706c6179

package crypto.ex2

import crypto.utils._

object Ex2 {
  def main(args: Array[String]) = {
    val start = Utils.hexToBin("1c0111001f010100061a024b53535009181c")
    val against = Utils.hexToBin("686974207468652062756c6c277320657965")
    val target = "746865206b696420646f6e277420706c6179"
    val result = xorBytes(start, against)
    println(target)
    println(Utils.binToHex(result))
  }

  def xorBytes(b1: Array[Byte], b2: Array[Byte]) : Array[Byte] = {
    b1.zip(b2).map(x => (x._1 ^ x._2).toByte)
  }
}
