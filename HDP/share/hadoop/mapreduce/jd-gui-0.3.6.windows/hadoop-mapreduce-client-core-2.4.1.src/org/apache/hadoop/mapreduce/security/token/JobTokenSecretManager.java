/*   1:    */ package org.apache.hadoop.mapreduce.security.token;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.TreeMap;
/*   5:    */ import javax.crypto.SecretKey;
/*   6:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*   7:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*   8:    */ import org.apache.hadoop.io.Text;
/*   9:    */ import org.apache.hadoop.security.token.SecretManager;
/*  10:    */ import org.apache.hadoop.security.token.SecretManager.InvalidToken;
/*  11:    */ import org.apache.hadoop.security.token.Token;
/*  12:    */ 
/*  13:    */ @InterfaceAudience.Private
/*  14:    */ @InterfaceStability.Unstable
/*  15:    */ public class JobTokenSecretManager
/*  16:    */   extends SecretManager<JobTokenIdentifier>
/*  17:    */ {
/*  18:    */   private final SecretKey masterKey;
/*  19:    */   private final Map<String, SecretKey> currentJobTokens;
/*  20:    */   
/*  21:    */   public static SecretKey createSecretKey(byte[] key)
/*  22:    */   {
/*  23: 46 */     return SecretManager.createSecretKey(key);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static byte[] computeHash(byte[] msg, SecretKey key)
/*  27:    */   {
/*  28: 56 */     return createPassword(msg, key);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public JobTokenSecretManager()
/*  32:    */   {
/*  33: 63 */     this.masterKey = generateSecret();
/*  34: 64 */     this.currentJobTokens = new TreeMap();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public byte[] createPassword(JobTokenIdentifier identifier)
/*  38:    */   {
/*  39: 74 */     byte[] result = createPassword(identifier.getBytes(), this.masterKey);
/*  40: 75 */     return result;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void addTokenForJob(String jobId, Token<JobTokenIdentifier> token)
/*  44:    */   {
/*  45: 84 */     SecretKey tokenSecret = createSecretKey(token.getPassword());
/*  46: 85 */     synchronized (this.currentJobTokens)
/*  47:    */     {
/*  48: 86 */       this.currentJobTokens.put(jobId, tokenSecret);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void removeTokenForJob(String jobId)
/*  53:    */   {
/*  54: 95 */     synchronized (this.currentJobTokens)
/*  55:    */     {
/*  56: 96 */       this.currentJobTokens.remove(jobId);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SecretKey retrieveTokenSecret(String jobId)
/*  61:    */     throws SecretManager.InvalidToken
/*  62:    */   {
/*  63:107 */     SecretKey tokenSecret = null;
/*  64:108 */     synchronized (this.currentJobTokens)
/*  65:    */     {
/*  66:109 */       tokenSecret = (SecretKey)this.currentJobTokens.get(jobId);
/*  67:    */     }
/*  68:111 */     if (tokenSecret == null) {
/*  69:112 */       throw new SecretManager.InvalidToken("Can't find job token for job " + jobId + " !!");
/*  70:    */     }
/*  71:114 */     return tokenSecret;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public byte[] retrievePassword(JobTokenIdentifier identifier)
/*  75:    */     throws SecretManager.InvalidToken
/*  76:    */   {
/*  77:126 */     return retrieveTokenSecret(identifier.getJobId().toString()).getEncoded();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public JobTokenIdentifier createIdentifier()
/*  81:    */   {
/*  82:135 */     return new JobTokenIdentifier();
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.security.token.JobTokenSecretManager
 * JD-Core Version:    0.7.0.1
 */