/*   1:    */ package org.apache.hadoop.mapreduce.security;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.net.URL;
/*   8:    */ import javax.crypto.SecretKey;
/*   9:    */ import javax.servlet.http.HttpServletRequest;
/*  10:    */ import org.apache.commons.codec.binary.Base64;
/*  11:    */ import org.apache.commons.logging.Log;
/*  12:    */ import org.apache.commons.logging.LogFactory;
/*  13:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  14:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  15:    */ import org.apache.hadoop.mapreduce.security.token.JobTokenSecretManager;
/*  16:    */ import org.apache.hadoop.record.Utils;
/*  17:    */ 
/*  18:    */ @InterfaceAudience.Private
/*  19:    */ @InterfaceStability.Unstable
/*  20:    */ public class SecureShuffleUtils
/*  21:    */ {
/*  22: 47 */   private static final Log LOG = LogFactory.getLog(SecureShuffleUtils.class);
/*  23:    */   public static final String HTTP_HEADER_URL_HASH = "UrlHash";
/*  24:    */   public static final String HTTP_HEADER_REPLY_URL_HASH = "ReplyHash";
/*  25:    */   
/*  26:    */   public static String generateHash(byte[] msg, SecretKey key)
/*  27:    */   {
/*  28: 57 */     return new String(Base64.encodeBase64(generateByteHash(msg, key)), Charsets.UTF_8);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private static byte[] generateByteHash(byte[] msg, SecretKey key)
/*  32:    */   {
/*  33: 67 */     return JobTokenSecretManager.computeHash(msg, key);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static boolean verifyHash(byte[] hash, byte[] msg, SecretKey key)
/*  37:    */   {
/*  38: 76 */     byte[] msg_hash = generateByteHash(msg, key);
/*  39: 77 */     return Utils.compareBytes(msg_hash, 0, msg_hash.length, hash, 0, hash.length) == 0;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static String hashFromString(String enc_str, SecretKey key)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 89 */     return generateHash(enc_str.getBytes(Charsets.UTF_8), key);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void verifyReply(String base64Hash, String msg, SecretKey key)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:100 */     byte[] hash = Base64.decodeBase64(base64Hash.getBytes(Charsets.UTF_8));
/*  52:    */     
/*  53:102 */     boolean res = verifyHash(hash, msg.getBytes(Charsets.UTF_8), key);
/*  54:104 */     if (res != true) {
/*  55:105 */       throw new IOException("Verification of the hashReply failed");
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String buildMsgFrom(URL url)
/*  60:    */   {
/*  61:115 */     return buildMsgFrom(url.getPath(), url.getQuery(), url.getPort());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static String buildMsgFrom(HttpServletRequest request)
/*  65:    */   {
/*  66:123 */     return buildMsgFrom(request.getRequestURI(), request.getQueryString(), request.getLocalPort());
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static String buildMsgFrom(String uri_path, String uri_query, int port)
/*  70:    */   {
/*  71:133 */     return String.valueOf(port) + uri_path + "?" + uri_query;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static String toHex(byte[] ba)
/*  75:    */   {
/*  76:143 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  77:144 */     PrintStream ps = new PrintStream(baos);
/*  78:145 */     for (byte b : ba) {
/*  79:146 */       ps.printf("%x", new Object[] { Byte.valueOf(b) });
/*  80:    */     }
/*  81:148 */     return baos.toString();
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.SecureShuffleUtils
 * JD-Core Version:    0.7.0.1
 */