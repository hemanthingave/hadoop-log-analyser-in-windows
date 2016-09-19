/*   1:    */ package org.apache.hadoop.mapreduce.jobhistory;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.avro.Schema;
/*   8:    */ import org.apache.avro.io.DatumWriter;
/*   9:    */ import org.apache.avro.io.Encoder;
/*  10:    */ import org.apache.avro.io.EncoderFactory;
/*  11:    */ import org.apache.avro.specific.SpecificDatumWriter;
/*  12:    */ import org.apache.avro.util.Utf8;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ import org.apache.hadoop.fs.FSDataOutputStream;
/*  16:    */ import org.apache.hadoop.io.IOUtils;
/*  17:    */ import org.apache.hadoop.mapreduce.Counter;
/*  18:    */ import org.apache.hadoop.mapreduce.CounterGroup;
/*  19:    */ import org.apache.hadoop.mapreduce.Counters;
/*  20:    */ 
/*  21:    */ class EventWriter
/*  22:    */ {
/*  23:    */   static final String VERSION = "Avro-Json";
/*  24:    */   private FSDataOutputStream out;
/*  25: 48 */   private DatumWriter<Event> writer = new SpecificDatumWriter(Event.class);
/*  26:    */   private Encoder encoder;
/*  27: 51 */   private static final Log LOG = LogFactory.getLog(EventWriter.class);
/*  28:    */   
/*  29:    */   EventWriter(FSDataOutputStream out)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 54 */     this.out = out;
/*  33: 55 */     out.writeBytes("Avro-Json");
/*  34: 56 */     out.writeBytes("\n");
/*  35: 57 */     out.writeBytes(Event.SCHEMA$.toString());
/*  36: 58 */     out.writeBytes("\n");
/*  37: 59 */     this.encoder = EncoderFactory.get().jsonEncoder(Event.SCHEMA$, out);
/*  38:    */   }
/*  39:    */   
/*  40:    */   synchronized void write(HistoryEvent event)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 63 */     Event wrapper = new Event();
/*  44: 64 */     wrapper.type = event.getEventType();
/*  45: 65 */     wrapper.event = event.getDatum();
/*  46: 66 */     this.writer.write(wrapper, this.encoder);
/*  47: 67 */     this.encoder.flush();
/*  48: 68 */     this.out.writeBytes("\n");
/*  49:    */   }
/*  50:    */   
/*  51:    */   void flush()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 72 */     this.encoder.flush();
/*  55: 73 */     this.out.flush();
/*  56: 74 */     this.out.hflush();
/*  57:    */   }
/*  58:    */   
/*  59:    */   void close()
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 79 */       this.encoder.flush();
/*  65: 80 */       this.out.close();
/*  66: 81 */       this.out = null;
/*  67:    */     }
/*  68:    */     finally
/*  69:    */     {
/*  70: 83 */       IOUtils.cleanup(LOG, new Closeable[] { this.out });
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74: 87 */   private static final Schema GROUPS = Schema.createArray(JhCounterGroup.SCHEMA$);
/*  75: 90 */   private static final Schema COUNTERS = Schema.createArray(JhCounter.SCHEMA$);
/*  76:    */   
/*  77:    */   static JhCounters toAvro(Counters counters)
/*  78:    */   {
/*  79: 94 */     return toAvro(counters, "COUNTERS");
/*  80:    */   }
/*  81:    */   
/*  82:    */   static JhCounters toAvro(Counters counters, String name)
/*  83:    */   {
/*  84: 97 */     JhCounters result = new JhCounters();
/*  85: 98 */     result.name = new Utf8(name);
/*  86: 99 */     result.groups = new ArrayList(0);
/*  87:100 */     if (counters == null) {
/*  88:100 */       return result;
/*  89:    */     }
/*  90:101 */     for (CounterGroup group : counters)
/*  91:    */     {
/*  92:102 */       JhCounterGroup g = new JhCounterGroup();
/*  93:103 */       g.name = new Utf8(group.getName());
/*  94:104 */       g.displayName = new Utf8(group.getDisplayName());
/*  95:105 */       g.counts = new ArrayList(group.size());
/*  96:106 */       for (Counter counter : group)
/*  97:    */       {
/*  98:107 */         JhCounter c = new JhCounter();
/*  99:108 */         c.name = new Utf8(counter.getName());
/* 100:109 */         c.displayName = new Utf8(counter.getDisplayName());
/* 101:110 */         c.value = counter.getValue();
/* 102:111 */         g.counts.add(c);
/* 103:    */       }
/* 104:113 */       result.groups.add(g);
/* 105:    */     }
/* 106:115 */     return result;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.jobhistory.EventWriter
 * JD-Core Version:    0.7.0.1
 */