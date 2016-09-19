/*   1:    */ package org.apache.hadoop.mapred;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.SortedSet;
/*   8:    */ import java.util.TreeSet;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ import org.apache.hadoop.io.Writable;
/*  12:    */ 
/*  13:    */ class SortedRanges
/*  14:    */   implements Writable
/*  15:    */ {
/*  16: 40 */   private static final Log LOG = LogFactory.getLog(SortedRanges.class);
/*  17:    */   private TreeSet<Range> ranges;
/*  18:    */   private long indicesCount;
/*  19:    */   
/*  20:    */   SortedRanges()
/*  21:    */   {
/*  22: 43 */     this.ranges = new TreeSet();
/*  23:    */   }
/*  24:    */   
/*  25:    */   synchronized SkipRangeIterator skipRangeIterator()
/*  26:    */   {
/*  27: 52 */     return new SkipRangeIterator(this.ranges.iterator());
/*  28:    */   }
/*  29:    */   
/*  30:    */   synchronized long getIndicesCount()
/*  31:    */   {
/*  32: 60 */     return this.indicesCount;
/*  33:    */   }
/*  34:    */   
/*  35:    */   synchronized SortedSet<Range> getRanges()
/*  36:    */   {
/*  37: 68 */     return this.ranges;
/*  38:    */   }
/*  39:    */   
/*  40:    */   synchronized void add(Range range)
/*  41:    */   {
/*  42: 81 */     if (range.isEmpty()) {
/*  43: 82 */       return;
/*  44:    */     }
/*  45: 85 */     long startIndex = range.getStartIndex();
/*  46: 86 */     long endIndex = range.getEndIndex();
/*  47:    */     
/*  48: 88 */     SortedSet<Range> headSet = this.ranges.headSet(range);
/*  49: 89 */     if (headSet.size() > 0)
/*  50:    */     {
/*  51: 90 */       Range previousRange = (Range)headSet.last();
/*  52: 91 */       LOG.debug("previousRange " + previousRange);
/*  53: 92 */       if (startIndex < previousRange.getEndIndex())
/*  54:    */       {
/*  55: 95 */         if (this.ranges.remove(previousRange)) {
/*  56: 96 */           this.indicesCount -= previousRange.getLength();
/*  57:    */         }
/*  58: 99 */         startIndex = previousRange.getStartIndex();
/*  59:100 */         endIndex = endIndex >= previousRange.getEndIndex() ? endIndex : previousRange.getEndIndex();
/*  60:    */       }
/*  61:    */     }
/*  62:105 */     Iterator<Range> tailSetIt = this.ranges.tailSet(range).iterator();
/*  63:106 */     while (tailSetIt.hasNext())
/*  64:    */     {
/*  65:107 */       Range nextRange = (Range)tailSetIt.next();
/*  66:108 */       LOG.debug("nextRange " + nextRange + "   startIndex:" + startIndex + "  endIndex:" + endIndex);
/*  67:110 */       if (endIndex < nextRange.getStartIndex()) {
/*  68:    */         break;
/*  69:    */       }
/*  70:113 */       tailSetIt.remove();
/*  71:114 */       this.indicesCount -= nextRange.getLength();
/*  72:115 */       if (endIndex < nextRange.getEndIndex())
/*  73:    */       {
/*  74:117 */         endIndex = nextRange.getEndIndex();
/*  75:118 */         break;
/*  76:    */       }
/*  77:    */     }
/*  78:124 */     add(startIndex, endIndex);
/*  79:    */   }
/*  80:    */   
/*  81:    */   synchronized void remove(Range range)
/*  82:    */   {
/*  83:135 */     if (range.isEmpty()) {
/*  84:136 */       return;
/*  85:    */     }
/*  86:138 */     long startIndex = range.getStartIndex();
/*  87:139 */     long endIndex = range.getEndIndex();
/*  88:    */     
/*  89:141 */     SortedSet<Range> headSet = this.ranges.headSet(range);
/*  90:142 */     if (headSet.size() > 0)
/*  91:    */     {
/*  92:143 */       Range previousRange = (Range)headSet.last();
/*  93:144 */       LOG.debug("previousRange " + previousRange);
/*  94:145 */       if (startIndex < previousRange.getEndIndex())
/*  95:    */       {
/*  96:148 */         if (this.ranges.remove(previousRange))
/*  97:    */         {
/*  98:149 */           this.indicesCount -= previousRange.getLength();
/*  99:150 */           LOG.debug("removed previousRange " + previousRange);
/* 100:    */         }
/* 101:152 */         add(previousRange.getStartIndex(), startIndex);
/* 102:153 */         if (endIndex <= previousRange.getEndIndex()) {
/* 103:154 */           add(endIndex, previousRange.getEndIndex());
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:159 */     Iterator<Range> tailSetIt = this.ranges.tailSet(range).iterator();
/* 108:160 */     while (tailSetIt.hasNext())
/* 109:    */     {
/* 110:161 */       Range nextRange = (Range)tailSetIt.next();
/* 111:162 */       LOG.debug("nextRange " + nextRange + "   startIndex:" + startIndex + "  endIndex:" + endIndex);
/* 112:164 */       if (endIndex <= nextRange.getStartIndex()) {
/* 113:    */         break;
/* 114:    */       }
/* 115:167 */       tailSetIt.remove();
/* 116:168 */       this.indicesCount -= nextRange.getLength();
/* 117:169 */       if (endIndex < nextRange.getEndIndex())
/* 118:    */       {
/* 119:170 */         add(endIndex, nextRange.getEndIndex());
/* 120:171 */         break;
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void add(long start, long end)
/* 126:    */   {
/* 127:180 */     if (end > start)
/* 128:    */     {
/* 129:181 */       Range recRange = new Range(start, end - start);
/* 130:182 */       this.ranges.add(recRange);
/* 131:183 */       this.indicesCount += recRange.getLength();
/* 132:184 */       LOG.debug("added " + recRange);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public synchronized void readFields(DataInput in)
/* 137:    */     throws IOException
/* 138:    */   {
/* 139:189 */     this.indicesCount = in.readLong();
/* 140:190 */     this.ranges = new TreeSet();
/* 141:191 */     int size = in.readInt();
/* 142:192 */     for (int i = 0; i < size; i++)
/* 143:    */     {
/* 144:193 */       Range range = new Range();
/* 145:194 */       range.readFields(in);
/* 146:195 */       this.ranges.add(range);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public synchronized void write(DataOutput out)
/* 151:    */     throws IOException
/* 152:    */   {
/* 153:200 */     out.writeLong(this.indicesCount);
/* 154:201 */     out.writeInt(this.ranges.size());
/* 155:202 */     Iterator<Range> it = this.ranges.iterator();
/* 156:203 */     while (it.hasNext())
/* 157:    */     {
/* 158:204 */       Range range = (Range)it.next();
/* 159:205 */       range.write(out);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String toString()
/* 164:    */   {
/* 165:210 */     StringBuffer sb = new StringBuffer();
/* 166:211 */     Iterator<Range> it = this.ranges.iterator();
/* 167:212 */     while (it.hasNext())
/* 168:    */     {
/* 169:213 */       Range range = (Range)it.next();
/* 170:214 */       sb.append(range.toString() + "\n");
/* 171:    */     }
/* 172:216 */     return sb.toString();
/* 173:    */   }
/* 174:    */   
/* 175:    */   static class Range
/* 176:    */     implements Comparable<Range>, Writable
/* 177:    */   {
/* 178:    */     private long startIndex;
/* 179:    */     private long length;
/* 180:    */     
/* 181:    */     Range(long startIndex, long length)
/* 182:    */     {
/* 183:229 */       if (length < 0L) {
/* 184:230 */         throw new RuntimeException("length can't be negative");
/* 185:    */       }
/* 186:232 */       this.startIndex = startIndex;
/* 187:233 */       this.length = length;
/* 188:    */     }
/* 189:    */     
/* 190:    */     Range()
/* 191:    */     {
/* 192:237 */       this(0L, 0L);
/* 193:    */     }
/* 194:    */     
/* 195:    */     long getStartIndex()
/* 196:    */     {
/* 197:245 */       return this.startIndex;
/* 198:    */     }
/* 199:    */     
/* 200:    */     long getEndIndex()
/* 201:    */     {
/* 202:253 */       return this.startIndex + this.length;
/* 203:    */     }
/* 204:    */     
/* 205:    */     long getLength()
/* 206:    */     {
/* 207:261 */       return this.length;
/* 208:    */     }
/* 209:    */     
/* 210:    */     boolean isEmpty()
/* 211:    */     {
/* 212:270 */       return this.length == 0L;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public boolean equals(Object o)
/* 216:    */     {
/* 217:274 */       if ((o instanceof Range))
/* 218:    */       {
/* 219:275 */         Range range = (Range)o;
/* 220:276 */         return (this.startIndex == range.startIndex) && (this.length == range.length);
/* 221:    */       }
/* 222:279 */       return false;
/* 223:    */     }
/* 224:    */     
/* 225:    */     public int hashCode()
/* 226:    */     {
/* 227:283 */       return Long.valueOf(this.startIndex).hashCode() + Long.valueOf(this.length).hashCode();
/* 228:    */     }
/* 229:    */     
/* 230:    */     public int compareTo(Range o)
/* 231:    */     {
/* 232:289 */       return this.length > o.length ? 1 : this.length < o.length ? -1 : this.startIndex > o.startIndex ? 1 : this.startIndex < o.startIndex ? -1 : 0;
/* 233:    */     }
/* 234:    */     
/* 235:    */     public void readFields(DataInput in)
/* 236:    */       throws IOException
/* 237:    */     {
/* 238:296 */       this.startIndex = in.readLong();
/* 239:297 */       this.length = in.readLong();
/* 240:    */     }
/* 241:    */     
/* 242:    */     public void write(DataOutput out)
/* 243:    */       throws IOException
/* 244:    */     {
/* 245:301 */       out.writeLong(this.startIndex);
/* 246:302 */       out.writeLong(this.length);
/* 247:    */     }
/* 248:    */     
/* 249:    */     public String toString()
/* 250:    */     {
/* 251:306 */       return this.startIndex + ":" + this.length;
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   static class SkipRangeIterator
/* 256:    */     implements Iterator<Long>
/* 257:    */   {
/* 258:    */     Iterator<SortedRanges.Range> rangeIterator;
/* 259:315 */     SortedRanges.Range range = new SortedRanges.Range();
/* 260:316 */     long next = -1L;
/* 261:    */     
/* 262:    */     SkipRangeIterator(Iterator<SortedRanges.Range> rangeIterator)
/* 263:    */     {
/* 264:323 */       this.rangeIterator = rangeIterator;
/* 265:324 */       doNext();
/* 266:    */     }
/* 267:    */     
/* 268:    */     public synchronized boolean hasNext()
/* 269:    */     {
/* 270:333 */       return this.next < 9223372036854775807L;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public synchronized Long next()
/* 274:    */     {
/* 275:341 */       long ci = this.next;
/* 276:342 */       doNext();
/* 277:343 */       return Long.valueOf(ci);
/* 278:    */     }
/* 279:    */     
/* 280:    */     private void doNext()
/* 281:    */     {
/* 282:347 */       this.next += 1L;
/* 283:348 */       SortedRanges.LOG.debug("currentIndex " + this.next + "   " + this.range);
/* 284:349 */       skipIfInRange();
/* 285:350 */       while ((this.next >= this.range.getEndIndex()) && (this.rangeIterator.hasNext()))
/* 286:    */       {
/* 287:351 */         this.range = ((SortedRanges.Range)this.rangeIterator.next());
/* 288:352 */         skipIfInRange();
/* 289:    */       }
/* 290:    */     }
/* 291:    */     
/* 292:    */     private void skipIfInRange()
/* 293:    */     {
/* 294:357 */       if ((this.next >= this.range.getStartIndex()) && (this.next < this.range.getEndIndex()))
/* 295:    */       {
/* 296:360 */         SortedRanges.LOG.warn("Skipping index " + this.next + "-" + this.range.getEndIndex());
/* 297:361 */         this.next = this.range.getEndIndex();
/* 298:    */       }
/* 299:    */     }
/* 300:    */     
/* 301:    */     synchronized boolean skippedAllRanges()
/* 302:    */     {
/* 303:372 */       return (!this.rangeIterator.hasNext()) && (this.next > this.range.getEndIndex());
/* 304:    */     }
/* 305:    */     
/* 306:    */     public void remove()
/* 307:    */     {
/* 308:379 */       throw new UnsupportedOperationException("remove not supported.");
/* 309:    */     }
/* 310:    */   }
/* 311:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapred.SortedRanges
 * JD-Core Version:    0.7.0.1
 */