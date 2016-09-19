/*  1:   */ package org.apache.hadoop.mapreduce.v2.security.client;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import org.apache.hadoop.conf.Configuration;
/*  5:   */ import org.apache.hadoop.mapreduce.v2.api.HSClientProtocolPB;
/*  6:   */ import org.apache.hadoop.security.KerberosInfo;
/*  7:   */ import org.apache.hadoop.security.SecurityInfo;
/*  8:   */ import org.apache.hadoop.security.token.TokenIdentifier;
/*  9:   */ import org.apache.hadoop.security.token.TokenInfo;
/* 10:   */ import org.apache.hadoop.security.token.TokenSelector;
/* 11:   */ 
/* 12:   */ public class ClientHSSecurityInfo
/* 13:   */   extends SecurityInfo
/* 14:   */ {
/* 15:   */   public KerberosInfo getKerberosInfo(Class<?> protocol, Configuration conf)
/* 16:   */   {
/* 17:36 */     if (!protocol.equals(HSClientProtocolPB.class)) {
/* 18:38 */       return null;
/* 19:   */     }
/* 20:40 */     new KerberosInfo()
/* 21:   */     {
/* 22:   */       public Class<? extends Annotation> annotationType()
/* 23:   */       {
/* 24:44 */         return null;
/* 25:   */       }
/* 26:   */       
/* 27:   */       public String serverPrincipal()
/* 28:   */       {
/* 29:49 */         return "mapreduce.jobhistory.principal";
/* 30:   */       }
/* 31:   */       
/* 32:   */       public String clientPrincipal()
/* 33:   */       {
/* 34:54 */         return null;
/* 35:   */       }
/* 36:   */     };
/* 37:   */   }
/* 38:   */   
/* 39:   */   public TokenInfo getTokenInfo(Class<?> protocol, Configuration conf)
/* 40:   */   {
/* 41:61 */     if (!protocol.equals(HSClientProtocolPB.class)) {
/* 42:63 */       return null;
/* 43:   */     }
/* 44:65 */     new TokenInfo()
/* 45:   */     {
/* 46:   */       public Class<? extends Annotation> annotationType()
/* 47:   */       {
/* 48:69 */         return null;
/* 49:   */       }
/* 50:   */       
/* 51:   */       public Class<? extends TokenSelector<? extends TokenIdentifier>> value()
/* 52:   */       {
/* 53:75 */         return ClientHSTokenSelector.class;
/* 54:   */       }
/* 55:   */     };
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-common-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.v2.security.client.ClientHSSecurityInfo
 * JD-Core Version:    0.7.0.1
 */