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
 //将字节转化为十六进制
  def bytes2hex(bytes: Array[Byte]): String = {
    val hex = new StringBuilder()//StringBuilder将字符串连接在一起
    for (i <- 0 to bytes.length - 1) {
      val b = bytes(i)
      var negative = false
      if (b < 0) {
        negative = true
      }
      val inte = Math.abs(b)
      val temp = Integer.toHexString(inte & 0xFF)//显示一个byte型的单字节十六进制(两位十六进制表示)的编码
      if (temp.length() == 1) {
        hex.append("0")
      }
      // hex.append(temp.toLowerCase())
      hex.append(temp)
    }
    hex.toString
  }
 //对字符串型公钥进行解密
  def decodePublicKey(encodedKey: String):Option[PublicKey] = {
    this.decodePublicKey(
      (new Base64()).decode(encodedKey)
    )
  }
 //对字符数组型公钥进行解密
  def decodePublicKey(encodedKey: Array[Byte]): Option[PublicKey]= {
    scala.util.control.Exception.allCatch.opt {
      val spec = new X509EncodedKeySpec(encodedKey)
      val factory = KeyFactory.getInstance("RSA")
      factory.generatePublic(spec)
    }
  }
 //定义加密方法
  def encrypt(file: String,key:PublicKey): Array[Byte] = {
    val listFile=Source.fromFile(file).toList
   //本算法中采用的密钥长度为512bytes,rsa中能加密的最大字节数为密钥长度减11 
    val blocks=list.grouped(501).toArray
    cipher.init(Cipher.getInstance("RSA").ENCRYPT_MODE, key)
    blocks.flatMap{block0 =>
      val stringBlock=block0.mkString
      cipher.doFinal(stringBlock.getBytes)}
  }
  def main(args:Array[String]):Unit={
    val publicKey=decodePublicKey(publickey)//public可以由密钥生成工具生成
    val bytes2hexCipher=bytes2hex(encrypt(inputfile,publicKey.get))
    new PrintWriter(new File(outputfile)).write(bytes2hexCipher)//由于RSA加密速度慢,所以最好采用PrintWriter的方式输出
  }
}
