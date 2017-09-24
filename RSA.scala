package com.dt.encryption

/**
  * Created by Jason Shu on 2017/8/5.
  */
import java.security.PublicKey
import java.io.File
import java.security._
import java.security.spec.X509EncodedKeySpec
import scala.io.Source
import org.apache.commons.codec.binary.Base64
import javax.crypto._

object RSA {
  def bytes2hex(bytes: Array[Byte]): String = {
    val hex = new StringBuilder()
    for (i <- 0 to bytes.length - 1) {
      val b = bytes(i)
      var negative = false
      if (b < 0) {
        negative = true
      }
      val inte = Math.abs(b)
      val temp = Integer.toHexString(inte & 0xFF)
      if (temp.length() == 1) {
        hex.append("0")
      }
      // hex.append(temp.toLowerCase())
      hex.append(temp)
    }
    hex.toString
  }
  def decodePublicKey(encodedKey: String):Option[PublicKey] = {
    this.decodePublicKey(
      (new Base64()).decode(encodedKey)
    )
  }
  def decodePublicKey(encodedKey: Array[Byte]): Option[PublicKey]= {
    scala.util.control.Exception.allCatch.opt {
      val spec = new X509EncodedKeySpec(encodedKey)
      val factory = KeyFactory.getInstance("RSA")
      factory.generatePublic(spec)
    }
  }

  def encrypt(file: String,key:PublicKey): Array[Byte] = {

    val listFile=Source.fromFile(file).toList

    val blocks=list.grouped(501).toArray

    val cipher = Cipher.getInstance("RSA")

    cipher.init(Cipher.ENCRYPT_MODE, key)

    blocks.flatMap{block0 =>
      val stringBlock=block0.mkString
      cipher.doFinal(stringBlock.getBytes)}

  }
  def main(args:Array[String]):Unit={

    val publicKey=decodePublicKey(publickey)

    val cipher = encrypt(inputfile,publicKey.get)

    val bytes2hexCipher=bytes2hex(cipher)
    
    println(bytes2hexCipher)
  }
}
