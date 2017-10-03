object AesEncryption{  
  def bytes2hex(bytes: Array[Byte]): String = {  
    val hex = new StringBuilder()  
    for (i<-0 to bytes.length - 1) {  
      val b = bytes(i)  
      var negative = false  
      if (b<0) {  
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
  @throws(classOf[Exception])  
  def genKeyAES(): String = {  
    val keyGen = KeyGenerator.getInstance("AES")  
    keyGen.init(128)  
    val key = keyGen.generateKey()  
    val base64Str = Base64.getEncoder.encodeToString(key.getEncoded())  
    base64Str  
  }  
  def loadKeyAES(base64Key: String): SecretKey = {  
    val bytes = Base64.getDecoder.decode(base64Key)  
    val key = new SecretKeySpec(bytes,"AES")  
    return key  
  }  
  def encrytAES(file: String, key: SecretKey): Array[Byte] = {  
    val cipher = Cipher.getInstance("AES")  
    cipher.init(Cipher.ENCRYPT_MODE, key)    
    val encryptBytes=Source.fromFile(file).mkString.getBytes  
    cipher.doFinal(encryptBytes)  
  }  
  def main(args:Array[String]):Unit= {   
    val keyAes = loadKeyAES(genKeyAes)  
    val encodeMsg = encrytAES(inputfile, keyAes)  
    val cipher=bytes2hex(encodeMsg)  
    println(cipher)  
  }  
}  
