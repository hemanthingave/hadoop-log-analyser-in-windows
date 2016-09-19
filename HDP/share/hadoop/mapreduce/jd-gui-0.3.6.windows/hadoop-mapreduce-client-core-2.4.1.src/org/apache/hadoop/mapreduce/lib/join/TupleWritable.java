/*   1:    */ package org.apache.hadoop.mapreduce.lib.join;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.BitSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.NoSuchElementException;
/*   9:    */ import org.apache.hadoop.classification.InterfaceAudience.Public;
/*  10:    */ import org.apache.hadoop.classification.InterfaceStability.Stable;
/*  11:    */ import org.apache.hadoop.io.NullWritable;
/*  12:    */ import org.apache.hadoop.io.Text;
/*  13:    */ import org.apache.hadoop.io.Writable;
/*  14:    */ import org.apache.hadoop.io.WritableUtils;
/*  15:    */ 
/*  16:    */ @InterfaceAudience.Public
/*  17:    */ @InterfaceStability.Stable
/*  18:    */ public class TupleWritable
/*  19:    */   implements Writable, Iterable<Writable>
/*  20:    */ {
/*  21:    */   protected BitSet written;
/*  22:    */   private Writable[] values;
/*  23:    */   
/*  24:    */   public TupleWritable()
/*  25:    */   {
/*  26: 58 */     this.written = new BitSet(0);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public TupleWritable(Writable[] vals)
/*  30:    */   {
/*  31: 66 */     this.written = new BitSet(vals.length);
/*  32: 67 */     this.values = vals;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean has(int i)
/*  36:    */   {
/*  37: 74 */     return this.written.get(i);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Writable get(int i)
/*  41:    */   {
/*  42: 81 */     return this.values[i];
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int size()
/*  46:    */   {
/*  47: 88 */     return this.values.length;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean equals(Object other)
/*  51:    */   {
/*  52: 95 */     if ((other instanceof TupleWritable))
/*  53:    */     {
/*  54: 96 */       TupleWritable that = (TupleWritable)other;
/*  55: 97 */       if (!this.written.equals(that.written)) {
/*  56: 98 */         return false;
/*  57:    */       }
/*  58:100 */       for (int i = 0; i < this.values.length; i++) {
/*  59:101 */         if ((has(i)) && 
/*  60:102 */           (!this.values[i].equals(that.get(i)))) {
/*  61:103 */           return false;
/*  62:    */         }
/*  63:    */       }
/*  64:106 */       return true;
/*  65:    */     }
/*  66:108 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int hashCode()
/*  70:    */   {
/*  71:112 */     if (!$assertionsDisabled) {
/*  72:112 */       throw new AssertionError("hashCode not designed");
/*  73:    */     }
/*  74:113 */     return this.written.hashCode();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Iterator<Writable> iterator()
/*  78:    */   {
/*  79:122 */     final TupleWritable t = this;
/*  80:123 */     new Iterator()
/*  81:    */     {
/*  82:124 */       int bitIndex = TupleWritable.this.written.nextSetBit(0);
/*  83:    */       
/*  84:    */       public boolean hasNext()
/*  85:    */       {
/*  86:126 */         return this.bitIndex >= 0;
/*  87:    */       }
/*  88:    */       
/*  89:    */       public Writable next()
/*  90:    */       {
/*  91:129 */         int returnIndex = this.bitIndex;
/*  92:130 */         if (returnIndex < 0) {
/*  93:131 */           throw new NoSuchElementException();
/*  94:    */         }
/*  95:132 */         this.bitIndex = TupleWritable.this.written.nextSetBit(this.bitIndex + 1);
/*  96:133 */         return t.get(returnIndex);
/*  97:    */       }
/*  98:    */       
/*  99:    */       public void remove()
/* 100:    */       {
/* 101:136 */         if (!TupleWritable.this.written.get(this.bitIndex)) {
/* 102:137 */           throw new IllegalStateException("Attempt to remove non-existent val");
/* 103:    */         }
/* 104:140 */         TupleWritable.this.written.clear(this.bitIndex);
/* 105:    */       }
/* 106:    */     };
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String toString()
/* 110:    */   {
/* 111:150 */     StringBuffer buf = new StringBuffer("[");
/* 112:151 */     for (int i = 0; i < this.values.length; i++)
/* 113:    */     {
/* 114:152 */       buf.append(has(i) ? this.values[i].toString() : "");
/* 115:153 */       buf.append(",");
/* 116:    */     }
/* 117:155 */     if (this.values.length != 0) {
/* 118:156 */       buf.setCharAt(buf.length() - 1, ']');
/* 119:    */     } else {
/* 120:158 */       buf.append(']');
/* 121:    */     }
/* 122:159 */     return buf.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void write(DataOutput out)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:171 */     WritableUtils.writeVInt(out, this.values.length);
/* 129:172 */     writeBitSet(out, this.values.length, this.written);
/* 130:173 */     for (int i = 0; i < this.values.length; i++) {
/* 131:174 */       Text.writeString(out, this.values[i].getClass().getName());
/* 132:    */     }
/* 133:176 */     for (int i = 0; i < this.values.length; i++) {
/* 134:177 */       if (has(i)) {
/* 135:178 */         this.values[i].write(out);
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void readFields(DataInput in)
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:188 */     int card = WritableUtils.readVInt(in);
/* 144:189 */     this.values = new Writable[card];
/* 145:190 */     readBitSet(in, card, this.written);
/* 146:191 */     Class<? extends Writable>[] cls = new Class[card];
/* 147:    */     try
/* 148:    */     {
/* 149:193 */       for (int i = 0; i < card; i++) {
/* 150:194 */         cls[i] = Class.forName(Text.readString(in)).asSubclass(Writable.class);
/* 151:    */       }
/* 152:196 */       for (int i = 0; i < card; i++)
/* 153:    */       {
/* 154:197 */         if (cls[i].equals(NullWritable.class)) {
/* 155:198 */           this.values[i] = NullWritable.get();
/* 156:    */         } else {
/* 157:200 */           this.values[i] = ((Writable)cls[i].newInstance());
/* 158:    */         }
/* 159:202 */         if (has(i)) {
/* 160:203 */           this.values[i].readFields(in);
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:    */     catch (ClassNotFoundException e)
/* 165:    */     {
/* 166:207 */       throw new IOException("Failed tuple init", e);
/* 167:    */     }
/* 168:    */     catch (IllegalAccessException e)
/* 169:    */     {
/* 170:209 */       throw new IOException("Failed tuple init", e);
/* 171:    */     }
/* 172:    */     catch (InstantiationException e)
/* 173:    */     {
/* 174:211 */       throw new IOException("Failed tuple init", e);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   void setWritten(int i)
/* 179:    */   {
/* 180:219 */     this.written.set(i);
/* 181:    */   }
/* 182:    */   
/* 183:    */   void clearWritten(int i)
/* 184:    */   {
/* 185:227 */     this.written.clear(i);
/* 186:    */   }
/* 187:    */   
/* 188:    */   void clearWritten()
/* 189:    */   {
/* 190:235 */     this.written.clear();
/* 191:    */   }
/* 192:    */   
/* 193:    */   private static final void writeBitSet(DataOutput stream, int nbits, BitSet bitSet)
/* 194:    */     throws IOException
/* 195:    */   {
/* 196:246 */     long bits = 0L;
/* 197:248 */     for (int bitSetIndex = bitSet.nextSetBit(0); (bitSetIndex >= 0) && (bitSetIndex < 64); bitSetIndex = bitSet.nextSetBit(bitSetIndex + 1)) {
/* 198:251 */       bits |= 1L << bitSetIndex;
/* 199:    */     }
/* 200:253 */     WritableUtils.writeVLong(stream, bits);
/* 201:255 */     if (nbits > 64)
/* 202:    */     {
/* 203:256 */       bits = 0L;
/* 204:257 */       for (int lastWordWritten = 0; (bitSetIndex >= 0) && (bitSetIndex < nbits); bitSetIndex = bitSet.nextSetBit(bitSetIndex + 1))
/* 205:    */       {
/* 206:259 */         int bitsIndex = bitSetIndex % 8;
/* 207:260 */         int word = (bitSetIndex - 64) / 8;
/* 208:261 */         if (word > lastWordWritten)
/* 209:    */         {
/* 210:262 */           stream.writeByte((byte)(int)bits);
/* 211:263 */           bits = 0L;
/* 212:264 */           for (lastWordWritten++; lastWordWritten < word; lastWordWritten++) {
/* 213:265 */             stream.writeByte((byte)(int)bits);
/* 214:    */           }
/* 215:    */         }
/* 216:268 */         bits |= 1L << bitsIndex;
/* 217:    */       }
/* 218:270 */       stream.writeByte((byte)(int)bits);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   private static final void readBitSet(DataInput stream, int nbits, BitSet bitSet)
/* 223:    */     throws IOException
/* 224:    */   {
/* 225:280 */     bitSet.clear();
/* 226:281 */     long initialBits = WritableUtils.readVLong(stream);
/* 227:282 */     long last = 0L;
/* 228:283 */     while (0L != initialBits)
/* 229:    */     {
/* 230:284 */       last = Long.lowestOneBit(initialBits);
/* 231:285 */       initialBits ^= last;
/* 232:286 */       bitSet.set(Long.numberOfTrailingZeros(last));
/* 233:    */     }
/* 234:289 */     for (int offset = 64; offset < nbits; offset += 8)
/* 235:    */     {
/* 236:290 */       byte bits = stream.readByte();
/* 237:291 */       while (0 != bits)
/* 238:    */       {
/* 239:292 */         last = Long.lowestOneBit(bits);
/* 240:293 */         bits = (byte)(int)(bits ^ last);
/* 241:294 */         bitSet.set(Long.numberOfTrailingZeros(last) + offset);
/* 242:    */       }
/* 243:    */     }
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\HDP\share\hadoop\mapreduce\jd-gui-0.3.6.windows\hadoop-mapreduce-client-core-2.4.1.jar
 * Qualified Name:     org.apache.hadoop.mapreduce.lib.join.TupleWritable
 * JD-Core Version:    0.7.0.1
 */