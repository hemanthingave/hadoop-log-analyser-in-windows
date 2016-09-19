/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.EOFException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import org.apache.avro.Schema;
/*   8:    */ import org.apache.avro.io.DatumReader;
/*   9:    */ import org.apache.avro.io.Decoder;
/*  10:    */ import org.apache.avro.io.DecoderFactory;
/*  11:    */ import org.apache.avro.specific.SpecificDatumReader;
/*  12:    */ import org.apache.hadoop.classification.InterfaceAudience.Private;
/*  13:    */ import org.apache.hadoop.classification.InterfaceStability.Unstable;
/*  14:    */ import org.apache.hadoop.fs.FileSystem;
/*  15:    */ import org.apache.hadoop.fs.Path;
/*  16:    */ import org.apache.hadoop.mapreduce.CounterGroup;
/*  17:    */ import org.apache.hadoop.mapreduce.Counters;
/*  18:    */ import org.apache.hadoop.util.StringInterner;
/*  19:    */ 
/*  20:    */ @InterfaceAudience.Private
/*  21:    */ @InterfaceStability.Unstable
/*  22:    */ public class EventReader
/*  23:    */   implements Closeable
/*  24:    */ {
/*  25:    */   private String version;
/*  26:    */   private Schema schema;
/*  27:    */   private DataInputStream in;
/*  28:    */   private Decoder decoder;
/*  29:    */   private DatumReader reader;
/*  30:    */   
/*  31:    */   public EventReader(FileSystem fs, Path name)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 56 */     this(fs.open(name));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public EventReader(DataInputStream in)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 66 */     this.in = in;
/*  41: 67 */     this.version = in.readLine();
/*  42: 69 */     if (!"Avro-Json".equals(this.version)) {
/*  43: 70 */       throw new IOException("Incompatible event log version: " + this.version);
/*  44:    */     }
/*  45: 73 */     this.schema = Schema.parse(in.readLine());
/*  46: 74 */     this.reader = new SpecificDatumReader(this.schema);
/*  47: 75 */     this.decoder = DecoderFactory.get().jsonDecoder(this.schema, in);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public HistoryEvent getNextEvent()
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:    */     Event wrapper;
/*  54:    */     try
/*  55:    */     {
/*  56: 87 */       wrapper = (Event)this.reader.read(null, this.decoder);
/*  57:    */     }
/*  58:    */     catch (EOFException e)
/*  59:    */     {
/*  60: 89 */       return null;
/*  61:    */     }
/*  62:    */     HistoryEvent result;
/*  63: 92 */     switch (1.$SwitchMap$org$apache$hadoop$mapreduce$jobhistory$EventType[wrapper.type.ordinal()])
/*  64:    */     {
/*  65:    */     case 1: 
/*  66: 94 */       result = new JobSubmittedEvent(); break;
/*  67:    */     case 2: 
/*  68: 96 */       result = new JobInitedEvent(); break;
/*  69:    */     case 3: 
/*  70: 98 */       result = new JobFinishedEvent(); break;
/*  71:    */     case 4: 
/*  72:100 */       result = new JobPriorityChangeEvent(); break;
/*  73:    */     case 5: 
/*  74:102 */       result = new JobQueueChangeEvent(); break;
/*  75:    */     case 6: 
/*  76:104 */       result = new JobStatusChangedEvent(); break;
/*  77:    */     case 7: 
/*  78:106 */       result = new JobUnsuccessfulCompletionEvent(); break;
/*  79:    */     case 8: 
/*  80:108 */       result = new JobUnsuccessfulCompletionEvent(); break;
/*  81:    */     case 9: 
/*  82:110 */       result = new JobUnsuccessfulCompletionEvent(); break;
/*  83:    */     case 10: 
/*  84:112 */       result = new JobInfoChangeEvent(); break;
/*  85:    */     case 11: 
/*  86:114 */       result = new TaskStartedEvent(); break;
/*  87:    */     case 12: 
/*  88:116 */       result = new TaskFinishedEvent(); break;
/*  89:    */     case 13: 
/*  90:118 */       result = new TaskFailedEvent(); break;
/*  91:    */     case 14: 
/*  92:120 */       result = new TaskUpdatedEvent(); break;
/*  93:    */     case 15: 
/*  94:122 */       result = new TaskAttemptStartedEvent(); break;
/*  95:    */     case 16: 
/*  96:124 */       result = new MapAttemptFinishedEvent(); break;
/*  97:    */     case 17: 
/*  98:126 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/*  99:    */     case 18: 
/* 100:128 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 101:    */     case 19: 
/* 102:130 */       result = new TaskAttemptStartedEvent(); break;
/* 103:    */     case 20: 
/* 104:132 */       result = new ReduceAttemptFinishedEvent(); break;
/* 105:    */     case 21: 
/* 106:134 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 107:    */     case 22: 
/* 108:136 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 109:    */     case 23: 
/* 110:138 */       result = new TaskAttemptStartedEvent(); break;
/* 111:    */     case 24: 
/* 112:140 */       result = new TaskAttemptFinishedEvent(); break;
/* 113:    */     case 25: 
/* 114:142 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 115:    */     case 26: 
/* 116:144 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 117:    */     case 27: 
/* 118:146 */       result = new TaskAttemptStartedEvent(); break;
/* 119:    */     case 28: 
/* 120:148 */       result = new TaskAttemptFinishedEvent(); break;
/* 121:    */     case 29: 
/* 122:150 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 123:    */     case 30: 
/* 124:152 */       result = new TaskAttemptUnsuccessfulCompletionEvent(); break;
/* 125:    */     case 31: 
/* 126:154 */       result = new AMStartedEvent(); break;
/* 127:    */     default: 
/* 128:156 */       throw new RuntimeException("unexpected event type: " + wrapper.type);
/* 129:    */     }
/* 130:158 */     result.setDatum(wrapper.event);
/* 131:159 */     return result;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void close()
/* 135:    */     throws IOException
/* 136:    */   {
/* 137:168 */     if (this.in != null) {
/* 138:169 */       this.in.close();
/* 139:    */     }
/* 140:171 */     this.in = null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   static Counters fromAvro(JhCounters counters)
/* 144:    */   {
/* 145:175 */     Counters result = new Counters();
/* 146:176 */     for (JhCounterGroup g : counters.groups)
/* 147:    */     {
/* 148:177 */       group = (CounterGroup)result.addGroup(StringInterner.weakIntern(g.name.toString()), StringInterner.weakIntern(g.displayName.toString()));
/* 149:180 */       for (JhCounter c : g.counts) {
/* 150:181 */         group.addCounter(StringInterner.weakIntern(c.name.toString()), StringInterner.weakIntern(c.displayName.toString()), c.value);
/* 151:    */       }
/* 152:    */     }
/* 153:    */     CounterGroup group;
/* 154:185 */     return result;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.EventReader
 * JD-Core Version:    0.7.0.1
 */